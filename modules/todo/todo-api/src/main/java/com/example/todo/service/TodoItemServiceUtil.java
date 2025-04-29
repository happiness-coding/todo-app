/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.todo.service;

import com.example.todo.model.TodoItem;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.List;

/**
 * Provides the remote service utility for TodoItem. This utility wraps
 * <code>com.example.todo.service.impl.TodoItemServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see TodoItemService
 * @generated
 */
public class TodoItemServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.example.todo.service.impl.TodoItemServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static TodoItem createTodoItem(
			String title, String description, java.sql.Date dueDate,
			int priority, long assigneeUserId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().createTodoItem(
			title, description, dueDate, priority, assigneeUserId,
			serviceContext);
	}

	public static void deleteTodoItem(long todoItemId) throws PortalException {
		getService().deleteTodoItem(todoItemId);
	}

	public static TodoItem getFirstTodoItemByTitleAndActive(
			String title, boolean isActive)
		throws PortalException {

		return getService().getFirstTodoItemByTitleAndActive(title, isActive);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static TodoItem getTodoItem(long todoItemId) throws PortalException {
		return getService().getTodoItem(todoItemId);
	}

	public static List<TodoItem> getTodoItems() {
		return getService().getTodoItems();
	}

	public static TodoItem updateTodoItem(
			long todoItemId, String title, String description,
			java.sql.Date dueDate, int priority, long assigneeUserId,
			boolean completed,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateTodoItem(
			todoItemId, title, description, dueDate, priority, assigneeUserId,
			completed, serviceContext);
	}

	public static String validateAndSaveFile(String fileName, java.io.File file)
		throws com.example.todo.exception.FileNameFormatException,
			   com.example.todo.exception.FileSizeExceededException,
			   com.example.todo.exception.InvalidFileNameException,
			   com.example.todo.exception.InvalidProcessingPeriodException,
			   com.example.todo.exception.ServiceUnavailableException,
			   java.io.IOException {

		return getService().validateAndSaveFile(fileName, file);
	}

	public static TodoItemService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<TodoItemService> _serviceSnapshot =
		new Snapshot<>(TodoItemServiceUtil.class, TodoItemService.class);

}