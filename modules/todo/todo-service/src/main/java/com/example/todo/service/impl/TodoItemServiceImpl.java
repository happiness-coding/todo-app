/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.todo.service.impl;

import com.example.todo.exception.FileNameFormatException;
import com.example.todo.exception.FileSizeExceededException;
import com.example.todo.exception.InvalidFileNameException;
import com.example.todo.exception.InvalidProcessingPeriodException;
import com.example.todo.service.base.TodoItemServiceBaseImpl;

import com.liferay.portal.aop.AopService;

import com.liferay.portal.kernel.util.FileUtil;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=todo",
		"json.web.service.context.path=TodoItem"
	},
	service = AopService.class
)
public class TodoItemServiceImpl extends TodoItemServiceBaseImpl {

	@Override
	public String validateAndSaveFile(String fileName, File file)
			throws FileSizeExceededException, FileNameFormatException,
			InvalidFileNameException, InvalidProcessingPeriodException, IOException {

		long fileSize = file.length();

		// 1. Validate file size (10MB limit)
		if (fileSize > 10 * 1024 * 1024) {
			throw new FileSizeExceededException("The file must be less than 10MB");
		}

		// 2. Validate file name format (fileName_ddMMyyyy.xlsx)
		if (!fileName.matches("^[a-zA-Z0-9]+_\\d{8}\\.(xlsx|cvx)$")) {
			throw new FileNameFormatException("Invalid file name format. Expected: name_ddMMyyyy.xlsx");
		}

		// 3. Validate acceptable file names
		Set<String> acceptableFileNames = new HashSet<>(Arrays.asList("test.xlsx", "text.cvx"));
		String baseFileName = fileName.substring(0, fileName.indexOf("_"));
		String extension = fileName.substring(fileName.lastIndexOf("."));
		String baseNameWithExtension = baseFileName + extension;

		if (!acceptableFileNames.contains(baseNameWithExtension)) {
			throw new InvalidFileNameException("File name not in the list of acceptable file names");
		}

		// 4. Extract date components from file name
		String datePart = fileName.substring(fileName.indexOf("_") + 1, fileName.lastIndexOf("."));
		String day = datePart.substring(0, 2);
		String month = datePart.substring(2, 4);
		String year = datePart.substring(4, 8);

		// 5. Validate yyyyMM
		String yearMonth = year + month;
		Set<String> acceptablePeriods = new HashSet<>(Arrays.asList("202501", "202502"));

		if (!acceptablePeriods.contains(yearMonth)) {
			throw new InvalidProcessingPeriodException("Invalid processing period");
		}

		// 6. Create directory if it doesn't exist
		String dirPath = "/apps/todo/" + yearMonth;
		File directory = new File(dirPath);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		// 7. Save the file
		File destFile = new File(directory, fileName);
		FileUtil.copyFile(file, destFile);

		return destFile.getAbsolutePath();
	}
}