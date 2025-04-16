package com.example.todo.web.portlet;

import com.example.todo.exception.FileNameFormatException;
import com.example.todo.exception.FileSizeExceededException;
import com.example.todo.exception.InvalidFileNameException;
import com.example.todo.exception.InvalidProcessingPeriodException;
import com.example.todo.service.TodoItemService;
import com.example.todo.web.constants.TodoPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.File;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
    immediate = true,
    property = {
        "javax.portlet.name=" + TodoPortletKeys.TODO,
        "mvc.command.name=uploadFile"
    },
    service = MVCActionCommand.class
)
public class UploadFileActionCommand implements MVCActionCommand {

    @Reference
    private TodoItemService todoItemService;

    @Override
    public boolean processAction(ActionRequest actionRequest, ActionResponse actionResponse)
            throws PortletException {
        try {
            // Clear any previous messages
            actionRequest.setAttribute("fileUploadSuccess", false);
            actionRequest.setAttribute("fileUploadError", null);

            UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);

            String fileName = uploadRequest.getFileName("fileToUpload");
            File file = uploadRequest.getFile("fileToUpload");

            // Delegate to service layer for validation and file saving
            String filePath = todoItemService.validateAndSaveFile(fileName, file);

            // Add success attributes
            actionRequest.setAttribute("fileUploadSuccess", true);
            actionRequest.setAttribute("fileName", fileName);
            actionRequest.setAttribute("filePath", filePath);
        } catch (FileSizeExceededException e) {
            actionRequest.setAttribute("fileUploadError", "The file must be less than 10MB");
        } catch (FileNameFormatException e) {
            actionRequest.setAttribute("fileUploadError", "Invalid file name format");
        } catch (InvalidFileNameException e) {
            actionRequest.setAttribute("fileUploadError", "Invalid file name");
        } catch (InvalidProcessingPeriodException e) {
            actionRequest.setAttribute("fileUploadError", "Invalid processing period");
        } catch (Exception e) {
            actionRequest.setAttribute("fileUploadError", e.getMessage());
        } finally {
            // Always redirect back to upload.jsp to show results
            actionResponse.setRenderParameter("jspPage", "/upload.jsp");
        }

        return true;
    }
}