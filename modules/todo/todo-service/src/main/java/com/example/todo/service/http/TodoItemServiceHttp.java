/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.todo.service.http;

import com.example.todo.service.TodoItemServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>TodoItemServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TodoItemServiceHttp {

	public static String validateAndSaveFile(
			HttpPrincipal httpPrincipal, String fileName, java.io.File file)
		throws com.example.todo.exception.FileNameFormatException,
			   com.example.todo.exception.FileSizeExceededException,
			   com.example.todo.exception.InvalidFileNameException,
			   com.example.todo.exception.InvalidProcessingPeriodException,
			   com.example.todo.exception.ServiceUnavailableException,
			   java.io.IOException {

		try {
			MethodKey methodKey = new MethodKey(
				TodoItemServiceUtil.class, "validateAndSaveFile",
				_validateAndSaveFileParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, fileName, file);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.example.todo.exception.FileSizeExceededException) {

					throw (com.example.todo.exception.FileSizeExceededException)
						exception;
				}

				if (exception instanceof
						com.example.todo.exception.FileNameFormatException) {

					throw (com.example.todo.exception.FileNameFormatException)
						exception;
				}

				if (exception instanceof
						com.example.todo.exception.InvalidFileNameException) {

					throw (com.example.todo.exception.InvalidFileNameException)
						exception;
				}

				if (exception instanceof
						com.example.todo.exception.
							InvalidProcessingPeriodException) {

					throw (com.example.todo.exception.
						InvalidProcessingPeriodException)exception;
				}

				if (exception instanceof java.io.IOException) {
					throw (java.io.IOException)exception;
				}

				if (exception instanceof
						com.example.todo.exception.
							ServiceUnavailableException) {

					throw (com.example.todo.exception.
						ServiceUnavailableException)exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.example.todo.model.TodoItem> getTodoItems(
		HttpPrincipal httpPrincipal) {

		try {
			MethodKey methodKey = new MethodKey(
				TodoItemServiceUtil.class, "getTodoItems",
				_getTodoItemsParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.example.todo.model.TodoItem>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.example.todo.model.TodoItem createTodoItem(
			HttpPrincipal httpPrincipal, String title, String description,
			java.sql.Date dueDate, int priority, long assigneeUserId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				TodoItemServiceUtil.class, "createTodoItem",
				_createTodoItemParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, title, description, dueDate, priority,
				assigneeUserId, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.example.todo.model.TodoItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.example.todo.model.TodoItem updateTodoItem(
			HttpPrincipal httpPrincipal, long todoItemId, String title,
			String description, java.sql.Date dueDate, int priority,
			long assigneeUserId, boolean completed,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				TodoItemServiceUtil.class, "updateTodoItem",
				_updateTodoItemParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, todoItemId, title, description, dueDate, priority,
				assigneeUserId, completed, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.example.todo.model.TodoItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.example.todo.model.TodoItem getTodoItem(
			HttpPrincipal httpPrincipal, long todoItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				TodoItemServiceUtil.class, "getTodoItem",
				_getTodoItemParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, todoItemId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.example.todo.model.TodoItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteTodoItem(
			HttpPrincipal httpPrincipal, long todoItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				TodoItemServiceUtil.class, "deleteTodoItem",
				_deleteTodoItemParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, todoItemId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.example.todo.model.TodoItem
			getFirstTodoItemByTitleAndActive(
				HttpPrincipal httpPrincipal, String title, boolean isActive)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				TodoItemServiceUtil.class, "getFirstTodoItemByTitleAndActive",
				_getFirstTodoItemByTitleAndActiveParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, title, isActive);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.example.todo.model.TodoItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(TodoItemServiceHttp.class);

	private static final Class<?>[] _validateAndSaveFileParameterTypes0 =
		new Class[] {String.class, java.io.File.class};
	private static final Class<?>[] _getTodoItemsParameterTypes12 =
		new Class[] {};
	private static final Class<?>[] _createTodoItemParameterTypes13 =
		new Class[] {
			String.class, String.class, java.sql.Date.class, int.class,
			long.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateTodoItemParameterTypes14 =
		new Class[] {
			long.class, String.class, String.class, java.sql.Date.class,
			int.class, long.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _getTodoItemParameterTypes15 = new Class[] {
		long.class
	};
	private static final Class<?>[] _deleteTodoItemParameterTypes16 =
		new Class[] {long.class};
	private static final Class<?>[]
		_getFirstTodoItemByTitleAndActiveParameterTypes17 = new Class[] {
			String.class, boolean.class
		};

}