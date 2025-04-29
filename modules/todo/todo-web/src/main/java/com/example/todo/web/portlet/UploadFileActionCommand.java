package com.example.todo.web.portlet;

import com.example.todo.exception.*;
import com.example.todo.service.TodoItemService;
import com.example.todo.web.constants.TodoPortletKeys;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.File;
import java.util.Locale;
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
    public boolean processAction(ActionRequest actionRequest, ActionResponse actionResponse) {
        try {
            // Clear any previous messages
            actionRequest.setAttribute("fileUploadSuccess", false);
            actionRequest.setAttribute("fileUploadError", null);
            actionRequest.setAttribute("fileUploadErrorCode", null);

            UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);

            String fileName = uploadRequest.getFileName("fileToUpload");
            File file = uploadRequest.getFile("fileToUpload");

            // Delegate to service layer for validation and file saving
            String filePath = todoItemService.validateAndSaveFile(fileName, file);

            // Add success attributes
            actionRequest.setAttribute("fileUploadSuccess", true);
            actionRequest.setAttribute("fileName", fileName);
            actionRequest.setAttribute("filePath", filePath);
        } catch (TodoException e) {
            // Get locale-specific error message from Language.properties
            Locale locale = actionRequest.getLocale();
            String localizedMessage = LanguageUtil.get(locale, e.getLanguageKey());

            actionRequest.setAttribute("fileUploadErrorCode", e.getErrorCode());
            actionRequest.setAttribute("fileUploadError", localizedMessage);
        } catch (Exception e) {
            // Handle unexpected errors
            actionRequest.setAttribute("fileUploadErrorCode", TodoErrorCodes.GENERAL_ERROR);
            actionRequest.setAttribute("fileUploadError", e.getMessage());
        } finally {
            // Always redirect back to upload.jsp to show results
            actionResponse.setRenderParameter("jspPage", "/upload.jsp");
        }

        return true;
    }
}