package com.example.todo.web.portlet;

import com.example.todo.exception.FileNameFormatException;
import com.example.todo.exception.FileSizeExceededException;
import com.example.todo.exception.InvalidFileNameException;
import com.example.todo.exception.InvalidProcessingPeriodException;
import com.example.todo.service.TodoItemService;
import com.example.todo.web.constants.TodoPortletKeys;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.File;
import java.io.IOException;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
    property = {
        "com.liferay.portlet.display-category=category.sample",
        "com.liferay.portlet.header-portlet-css=/css/main.css",
        "com.liferay.portlet.instanceable=true",
        "javax.portlet.display-name=Todo",
        "javax.portlet.init-param.template-path=/",
        "javax.portlet.init-param.view-template=/view.jsp",
        "javax.portlet.name=" + TodoPortletKeys.TODO,
        "javax.portlet.resource-bundle=content.Language",
        "javax.portlet.security-role-ref=power-user,user"
    },
    service = Portlet.class
)
public class TodoPortlet extends MVCPortlet {

    @Reference
    private DLAppLocalService dlAppLocalService;

    @Reference
    private TodoItemService todoItemService;

    public void uploadFile(ActionRequest actionRequest, ActionResponse actionResponse)
            throws IOException, PortletException {

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
    }
}