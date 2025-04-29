/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.todo.service;

import com.example.todo.exception.FileNameFormatException;
import com.example.todo.exception.FileSizeExceededException;
import com.example.todo.exception.InvalidFileNameException;
import com.example.todo.exception.InvalidProcessingPeriodException;
import com.example.todo.exception.ServiceUnavailableException;
import com.example.todo.model.TodoItem;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.io.File;
import java.io.IOException;

import java.sql.Date;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for TodoItem. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see TodoItemServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface TodoItemService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.example.todo.service.impl.TodoItemServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the todo item remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link TodoItemServiceUtil} if injection and service tracking are not available.
	 */
	public TodoItem createTodoItem(
			String title, String description, Date dueDate, int priority,
			long assigneeUserId, ServiceContext serviceContext)
		throws PortalException;

	public void deleteTodoItem(long todoItemId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public TodoItem getFirstTodoItemByTitleAndActive(
			String title, boolean isActive)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public TodoItem getTodoItem(long todoItemId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<TodoItem> getTodoItems();

	public TodoItem updateTodoItem(
			long todoItemId, String title, String description, Date dueDate,
			int priority, long assigneeUserId, boolean completed,
			ServiceContext serviceContext)
		throws PortalException;

	public String validateAndSaveFile(String fileName, File file)
		throws FileNameFormatException, FileSizeExceededException,
			   InvalidFileNameException, InvalidProcessingPeriodException,
			   IOException, ServiceUnavailableException;

}