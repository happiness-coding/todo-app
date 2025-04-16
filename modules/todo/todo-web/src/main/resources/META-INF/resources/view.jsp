<%@ include file="/init.jsp" %>

<div class="container-fluid">
    <h2>Todo Application</h2>

    <div class="card">
        <div class="card-header">
            <div class="card-title">
                <h3>Actions</h3>
            </div>
        </div>
        <div class="card-body">
            <portlet:renderURL var="uploadFileURL">
                <portlet:param name="mvcPath" value="/upload.jsp" />
            </portlet:renderURL>

            <a href="${uploadFileURL}" class="btn btn-primary">
                <i class="icon-upload"></i> Upload Attachment
            </a>
        </div>
    </div>
</div>