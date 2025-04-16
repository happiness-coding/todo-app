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

	private static Log _log = LogFactoryUtil.getLog(TodoItemServiceHttp.class);

	private static final Class<?>[] _validateAndSaveFileParameterTypes0 =
		new Class[] {String.class, java.io.File.class};

}