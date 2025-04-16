package com.example.todo.service.impl;

import com.example.todo.exception.FileNameFormatException;
import com.example.todo.exception.FileSizeExceededException;
import com.example.todo.exception.InvalidFileNameException;
import com.example.todo.exception.InvalidProcessingPeriodException;
import com.example.todo.exception.ServiceUnavailableException;
import com.example.todo.exception.TodoErrorCodes;
import com.example.todo.model.FileMetadata;
import com.example.todo.service.base.TodoItemServiceBaseImpl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Implementation of TodoItemService
 */
@Component(
    property = {
        "json.web.service.context.name=todo",
        "json.web.service.context.path=TodoItem"
    },
    service = AopService.class
)
public class TodoItemServiceImpl extends TodoItemServiceBaseImpl {

    private static final Log _log = LogFactoryUtil.getLog(TodoItemServiceImpl.class);

    // Constants
    private static final long MAX_FILE_SIZE_BYTES = 10L * 1024 * 1024; // 10MB
    private static final String FILE_NAME_PATTERN = "^([a-zA-Z0-9]+)_(\\d{8})\\.(xlsx|cvx)$";
    private static final Set<String> ACCEPTABLE_BASE_FILENAMES = new HashSet<>(
        Arrays.asList("test.xlsx", "text.cvx")
    );
    private static final Set<String> ACCEPTABLE_PERIODS = new HashSet<>(
        Arrays.asList("202501", "202502")
    );
    private static final String BASE_DIRECTORY = "/apps/todo/";

    // Backend service configuration
    private static final String BACKEND_SERVICE_URL = "http://backend-service.example.com/upload";
    private static final int CONNECTION_TIMEOUT = 10; // seconds

    @Override
    public String validateAndSaveFile(String fileName, File file)
            throws FileSizeExceededException, FileNameFormatException,
            InvalidFileNameException, InvalidProcessingPeriodException,
            IOException, ServiceUnavailableException {

        // Validate all aspects of the file
        validateFileSize(file);

        // Extract file components and validate format
        FileComponents components = validateAndExtractFileComponents(fileName);

        // Validate file name is in acceptable list
        validateAcceptableFileName(components);

        // Validate processing period
        validateProcessingPeriod(components);

        // Save the file after all validations have passed
        String filePath = saveFile(fileName, file, components.yearMonth);

        // Send file to behind service
        sendFileToBehindService(new File(filePath), components);

        return filePath;
    }

	/**
	 * Converts file to binary and sends to behind service through the /upload API
	 */
	private void sendFileToBehindService(File file, FileComponents components)
			throws IOException, ServiceUnavailableException {

		byte[] binaryData = null;
		try {
			// Convert file to binary data
			binaryData = Files.readAllBytes(file.toPath());

			// Prepare and send the request
			HttpResponse<String> response = sendRequestToBackendService(file, binaryData, components);

			// Process the response
			processBackendServiceResponse(response, file.getName());

		} catch (IOException e) {
			_log.error("IO error while sending file to backend service", e);
			throw e;
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			_log.error("Request to backend service was interrupted", e);
			throw new ServiceUnavailableException("Communication with backend service was interrupted");
		} catch (Exception e) {
			_log.error("Unexpected error while sending file to backend service", e);
			throw new ServiceUnavailableException("Error communicating with backend service: " + e.getMessage());
		}
	}

	/**
	 * Prepares and sends the HTTP request to the backend service
	 */
	private HttpResponse<String> sendRequestToBackendService(File file, byte[] binaryData, FileComponents components)
			throws IOException, InterruptedException {

		// Convert binary data to Base64
		String base64Data = Base64.getEncoder().encodeToString(binaryData);

		// Create JSON payload with file metadata and content
		JSONObject payload = createRequestPayload(file, base64Data, components);

		// Create and configure HTTP client
		HttpClient client = HttpClient.newBuilder()
				.connectTimeout(java.time.Duration.ofSeconds(CONNECTION_TIMEOUT))
				.build();

		// Build the request
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(BACKEND_SERVICE_URL))
				.header("Content-Type", "application/json")
				.header("X-Request-ID", generateRequestId())
				.POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
				.build();

		// Send request and return response
		return client.send(request, HttpResponse.BodyHandlers.ofString());
	}

	/**
	 * Creates the JSON payload for the request
	 */
	private JSONObject createRequestPayload(File file, String base64Data, FileComponents components) {
		// Create file metadata
		String contentType = determineContentType(components.extension);
		String originalDate = components.day + "/" + components.month + "/" + components.year;

		// Build JSON payload
		JSONObject payload = JSONFactoryUtil.createJSONObject();
		payload.put("fileName", file.getName());
		payload.put("fileSize", file.length());
		payload.put("contentType", contentType);
		payload.put("processingPeriod", components.yearMonth);
		payload.put("originalDate", originalDate);
		payload.put("fileData", base64Data);

		return payload;
	}

	/**
	 * Processes the response from the backend service
	 */
	private void processBackendServiceResponse(HttpResponse<String> response, String fileName)
			throws ServiceUnavailableException {

		int statusCode = response.statusCode();
		if (statusCode < 200 || statusCode >= 300) {
			String errorMessage = "Backend service returned error. Status: " + statusCode;
			if (response.body() != null && !response.body().isEmpty()) {
				errorMessage += ", Response: " + response.body();
			}

			_log.error(errorMessage);
			throw new ServiceUnavailableException("Backend service returned error: " + statusCode);
		}

		_log.info("File successfully sent to backend service: " + fileName);
	}

    /**
     * Validates file size against maximum allowed size
     */
    private void validateFileSize(File file) throws FileSizeExceededException {
        if (file.length() > MAX_FILE_SIZE_BYTES) {
            throw new FileSizeExceededException("The file must be less than 10MB");
        }
    }

    /**
     * Validates file name format and extracts components
     */
    private FileComponents validateAndExtractFileComponents(String fileName)
            throws FileNameFormatException {

        Pattern pattern = Pattern.compile(FILE_NAME_PATTERN);
        Matcher matcher = pattern.matcher(fileName);

        if (!matcher.matches()) {
            throw new FileNameFormatException(
                "Invalid file name format. Expected: name_ddMMyyyy.xlsx or name_ddMMyyyy.cvx");
        }

        FileComponents components = new FileComponents();
        components.baseName = matcher.group(1);

        String datePart = matcher.group(2);
        components.day = datePart.substring(0, 2);
        components.month = datePart.substring(2, 4);
        components.year = datePart.substring(4);
        components.yearMonth = components.year + components.month;

        components.extension = matcher.group(3);
        components.baseNameWithExtension = components.baseName + "." + components.extension;

        return components;
    }

    /**
     * Validates the file name is in the list of acceptable names
     */
    private void validateAcceptableFileName(FileComponents components)
            throws InvalidFileNameException {

        if (!ACCEPTABLE_BASE_FILENAMES.contains(components.baseNameWithExtension)) {
            throw new InvalidFileNameException("File name not in the list of acceptable file names");
        }
    }

    /**
     * Validates the processing period is acceptable
     */
    private void validateProcessingPeriod(FileComponents components)
            throws InvalidProcessingPeriodException {

        if (!ACCEPTABLE_PERIODS.contains(components.yearMonth)) {
            throw new InvalidProcessingPeriodException("Invalid processing period");
        }
    }

    /**
     * Saves the file to the appropriate directory
     */
    private String saveFile(String fileName, File file, String yearMonth) throws IOException {
        String dirPath = BASE_DIRECTORY + yearMonth;
        File directory = new File(dirPath);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        File destFile = new File(directory, fileName);
        FileUtil.copyFile(file, destFile);

        return destFile.getAbsolutePath();
    }


    /**
     * Generates a unique request ID for tracking
     */
    private String generateRequestId() {
        return "req_" + System.currentTimeMillis();
    }

    /**
     * Determines content type based on file extension
     */
    private String determineContentType(String extension) {
        switch (extension.toLowerCase()) {
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "cvx":
                return "application/octet-stream"; // Custom type for cvx files
            default:
                return "application/octet-stream";
        }
    }

    /**
     * Helper class to store file name components
     */
    private static class FileComponents {
        String baseName;
        String day;
        String month;
        String year;
        String yearMonth;
        String extension;
        String baseNameWithExtension;
    }
}