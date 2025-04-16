package com.example.todo.web.portlet;

import com.example.todo.web.constants.TodoPortletKeys;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

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

    public void uploadFile(ActionRequest actionRequest, ActionResponse actionResponse)
            throws IOException, PortletException {

        try {
            UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
            ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

            String fileName = uploadRequest.getFileName("fileToUpload");
            File file = uploadRequest.getFile("fileToUpload");
            String contentType = uploadRequest.getContentType("fileToUpload");

            long repositoryId = themeDisplay.getScopeGroupId();
            long folderId = 0; // Root folder
            String description = "Todo attachment";

            // Create a ServiceContext object
            ServiceContext serviceContext = new ServiceContext();
            serviceContext.setScopeGroupId(themeDisplay.getScopeGroupId());
            serviceContext.setUserId(themeDisplay.getUserId());

            // Add the file to Document Library
            FileEntry fileEntry = dlAppLocalService.addFileEntry(
                    String.valueOf(themeDisplay.getUserId()),
                    repositoryId,
                    folderId,
                    fileName,
                    contentType,
                    fileName,
                    description,
                    null,
                    FileUtil.getBytes(file),
                    null,
                    serviceContext);
            // Add a success message
            actionRequest.setAttribute("fileUploadSuccess", true);
            actionRequest.setAttribute("fileEntryId", fileEntry.getFileEntryId());

        } catch (Exception e) {
            actionRequest.setAttribute("fileUploadError", e.getMessage());
            throw new PortletException(e);
        }
    }

}