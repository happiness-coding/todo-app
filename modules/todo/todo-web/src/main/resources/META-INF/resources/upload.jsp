<%@ include file="/init.jsp" %>

<portlet:actionURL name="uploadFile" var="uploadFileURL" />

<div class="container-fluid">
    <div class="card">
        <div class="card-header">
            <h3><liferay-ui:message key="upload-attachment" /></h3>
        </div>
        <div class="card-body">
            <c:if test="${fileUploadSuccess}">
                <div class="alert alert-success">
                    <liferay-ui:message key="file-uploaded-successfully" />
                </div>
            </c:if>

            <c:if test="${not empty fileUploadError}">
                <div class="alert alert-danger">
                    <liferay-ui:message key="error-uploading-file" />: ${fileUploadError}
                </div>
            </c:if>

            <aui:form action="<%= uploadFileURL %>" enctype="multipart/form-data" method="post">
                <aui:fieldset>
                    <aui:input type="file" name="fileToUpload" label="select-file" required="true" />
                </aui:fieldset>

                <aui:button-row>
                    <aui:button type="submit" value="upload" cssClass="btn-primary" />
                    <portlet:renderURL var="cancelURL">
                        <portlet:param name="mvcPath" value="/view.jsp" />
                    </portlet:renderURL>
                    <aui:button type="cancel" onClick="<%= cancelURL %>" />
                </aui:button-row>
            </aui:form>
        </div>
    </div>
</div>