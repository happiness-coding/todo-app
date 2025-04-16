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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
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
            // Clear any previous messages
            actionRequest.setAttribute("fileUploadSuccess", false);
            actionRequest.setAttribute("fileUploadError", null);

            UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
            ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

            String fileName = uploadRequest.getFileName("fileToUpload");
            File file = uploadRequest.getFile("fileToUpload");
            String contentType = uploadRequest.getContentType("fileToUpload");
            long fileSize = uploadRequest.getSize("fileToUpload");

            // 1. Validate file size (10MB limit)
            if (fileSize > 10 * 1024 * 1024) {
                actionRequest.setAttribute("fileUploadError", "The file must be less than 10MB");
                actionResponse.setRenderParameter("jspPage", "/upload.jsp");
                return;
            }

            // 2. Validate file name format (fileName_ddMMyyyy.xlsx)
            if (!fileName.matches("^[a-zA-Z0-9]+_\\d{8}\\.(xlsx|cvx)$")) {
                actionRequest.setAttribute("fileUploadError", "Invalid file name");
                actionResponse.setRenderParameter("jspPage", "/upload.jsp");
                return;
            }

            // 3. Validate acceptable file names
            Set<String> acceptableFileNames = new HashSet<>(Arrays.asList("test.xlsx", "text.cvx"));
            String baseFileName = fileName.substring(0, fileName.indexOf("_"));
            String extension = fileName.substring(fileName.lastIndexOf("."));
            String baseNameWithExtension = baseFileName + extension;

            if (!acceptableFileNames.contains(baseNameWithExtension)) {
                actionRequest.setAttribute("fileUploadError", "Invalid file name");
                actionResponse.setRenderParameter("jspPage", "/upload.jsp");
                return;
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
                actionRequest.setAttribute("fileUploadError", "Invalid processing period");
                actionResponse.setRenderParameter("jspPage", "/upload.jsp");
                return;
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

            // Add a success message
            actionRequest.setAttribute("fileUploadSuccess", true);
            actionRequest.setAttribute("fileName", fileName);
            actionRequest.setAttribute("filePath", destFile.getAbsolutePath());
            actionResponse.setRenderParameter("jspPage", "/upload.jsp");

        } catch (Exception e) {
            actionRequest.setAttribute("fileUploadError", e.getMessage());
            actionRequest.setAttribute("fileUploadSuccess", false);
            actionResponse.setRenderParameter("jspPage", "/upload.jsp");
            throw new PortletException(e);
        }
    }


}