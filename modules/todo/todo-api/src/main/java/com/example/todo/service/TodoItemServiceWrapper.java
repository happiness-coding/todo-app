/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.todo.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link TodoItemService}.
 *
 * @author Brian Wing Shun Chan
 * @see TodoItemService
 * @generated
 */
public class TodoItemServiceWrapper
	implements ServiceWrapper<TodoItemService>, TodoItemService {

	public TodoItemServiceWrapper() {
		this(null);
	}

	public TodoItemServiceWrapper(TodoItemService todoItemService) {
		_todoItemService = todoItemService;
	}

	@Override
	public com.example.todo.model.TodoItem createTodoItem(
			String title, String description, java.sql.Date dueDate,
			int priority, long assigneeUserId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _todoItemService.createTodoItem(
			title, description, dueDate, priority, assigneeUserId,
			serviceContext);
	}

	@Override
	public void deleteTodoItem(long todoItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_todoItemService.deleteTodoItem(todoItemId);
	}

	@Override
	public com.example.todo.model.TodoItem getFirstTodoItemByTitleAndActive(
			String title, boolean isActive)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _todoItemService.getFirstTodoItemByTitleAndActive(
			title, isActive);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _todoItemService.getOSGiServiceIdentifier();
	}

	@Override
	public com.example.todo.model.TodoItem getTodoItem(long todoItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _todoItemService.getTodoItem(todoItemId);
	}

	@Override
	public java.util.List<com.example.todo.model.TodoItem> getTodoItems() {
		return _todoItemService.getTodoItems();
	}

	@Override
	public com.example.todo.model.TodoItem updateTodoItem(
			long todoItemId, String title, String description,
			java.sql.Date dueDate, int priority, long assigneeUserId,
			boolean completed,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _todoItemService.updateTodoItem(
			todoItemId, title, description, dueDate, priority, assigneeUserId,
			completed, serviceContext);
	}

	@Override
	public String validateAndSaveFile(String fileName, java.io.File file)
		throws com.example.todo.exception.FileNameFormatException,
			   com.example.todo.exception.FileSizeExceededException,
			   com.example.todo.exception.InvalidFileNameException,
			   com.example.todo.exception.InvalidProcessingPeriodException,
			   com.example.todo.exception.ServiceUnavailableException,
			   java.io.IOException {

		return _todoItemService.validateAndSaveFile(fileName, file);
	}

	@Override
	public TodoItemService getWrappedService() {
		return _todoItemService;
	}

	@Override
	public void setWrappedService(TodoItemService todoItemService) {
		_todoItemService = todoItemService;
	}

	private TodoItemService _todoItemService;

}