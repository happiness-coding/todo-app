/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.todo.service.impl;

import com.example.todo.exception.NoSuchItemException;
import com.example.todo.model.TodoItem;
import com.example.todo.service.base.TodoItemLocalServiceBaseImpl;

import com.liferay.portal.aop.AopService;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import java.sql.Date;
import java.util.List;
import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.example.todo.model.TodoItem",
	service = AopService.class
)
public class TodoItemLocalServiceImpl extends TodoItemLocalServiceBaseImpl {


	private static final Log _log = LogFactoryUtil.getLog(TodoItemLocalServiceImpl.class);

	// TodoItemLocalServiceImpl.java

	@Override
	public List<TodoItem> getTodoItems() {
	    return todoItemPersistence.findAll();
	}

	@Override
	public TodoItem addTodoItem(long userId, String title, String description,
	                           Date dueDate, int priority, long assigneeUserId,
	                           ServiceContext serviceContext) throws PortalException {

		_log.info("Adding TodoItem: " + title);
	    // Get the user
	    User user = userLocalService.getUser(userId);

	    // Generate primary key for new TodoItem
	    long todoItemId = counterLocalService.increment(TodoItem.class.getName());

	    // Create a new TodoItem
	    TodoItem todoItem = todoItemPersistence.create(todoItemId);

	    // Set audit fields
	    todoItem.setCompanyId(user.getCompanyId());
	    todoItem.setUserId(user.getUserId());
	    todoItem.setUserName(user.getFullName());
	    todoItem.setCreateDate(serviceContext.getCreateDate());
	    todoItem.setModifiedDate(serviceContext.getModifiedDate());

	    // Set TodoItem fields
	    todoItem.setTitle(title);
	    todoItem.setDescription(description);
	    todoItem.setDueDate(dueDate);
	    todoItem.setPriority(priority);
//	    todoItem.setAssigneeUserId(assigneeUserId);
	    todoItem.setCompleted(false); // New items are not completed

	    // Set resource permissions
	    todoItem.setGroupId(serviceContext.getScopeGroupId());

	    resourceLocalService.addResources(
	        user.getCompanyId(),
	        serviceContext.getScopeGroupId(),
	        userId,
	        TodoItem.class.getName(),
	        todoItemId,
	        false,
	        true,
	        true
	    );

	    // Persist and return
	    return todoItemPersistence.update(todoItem);
	}

	@Override
	public TodoItem updateTodoItem(long userId, long todoItemId, String title,
	                              String description, Date dueDate, int priority,
	                              long assigneeUserId, boolean completed,
	                              ServiceContext serviceContext) throws PortalException {

	    // Get the existing TodoItem
	    TodoItem todoItem = todoItemPersistence.findByPrimaryKey(todoItemId);

	    // Update audit fields
	    User user = userLocalService.getUser(userId);
	    todoItem.setUserId(user.getUserId());
	    todoItem.setUserName(user.getFullName());
	    todoItem.setModifiedDate(serviceContext.getModifiedDate());

	    // Update TodoItem fields
	    todoItem.setTitle(title);
	    todoItem.setDescription(description);
	    todoItem.setDueDate(dueDate);
	    todoItem.setPriority(priority);
//	    todoItem.setAssigneeUserId(assigneeUserId);
	    todoItem.setCompleted(completed);

	    // Update resource permissions
	    resourceLocalService.updateResources(
	        serviceContext.getCompanyId(),
	        serviceContext.getScopeGroupId(),
	        TodoItem.class.getName(),
	        todoItemId,
	        serviceContext.getModelPermissions()
	    );

	    // Persist and return
	    return todoItemPersistence.update(todoItem);
	}

	public TodoItem getFirstTodoItemByTitleAndActive(String title, boolean isActive)
            throws NoSuchItemException {

		return todoItemPersistence.findByTitleActive_First(
				title, isActive, null);
	}
}