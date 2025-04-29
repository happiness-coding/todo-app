<%@ include file="/init.jsp" %>

<div class="container-fluid">
    <h2>Todo Application</h2>

    <div class="row mb-3">
        <div class="col-md-6">
            <portlet:renderURL var="addTodoItemURL">
                <portlet:param name="mvcRenderCommandName" value="/todo/edit" />
            </portlet:renderURL>

            <aui:button href="<%= addTodoItemURL %>" value="add-todo-item" cssClass="btn-primary" />

            <portlet:renderURL var="uploadFileURL">
                <portlet:param name="mvcPath" value="/upload.jsp" />
            </portlet:renderURL>

            <aui:button href="<%= uploadFileURL %>" value="upload-attachment" cssClass="btn-info" icon="upload" />
        </div>
    </div>

    <liferay-ui:success key="todoItemAdded" message="todo-item-added-successfully" />
    <liferay-ui:success key="todoItemUpdated" message="todo-item-updated-successfully" />
    <liferay-ui:success key="todoItemDeleted" message="todo-item-deleted-successfully" />
    <liferay-ui:success key="fileUploaded" message="file-uploaded-successfully" />

    <div class="card">
        <div class="card-header">
            <div class="card-title">
                <h3>Todo Items</h3>
            </div>
        </div>
        <div class="card-body">
            <liferay-ui:search-container total="<%= todoItems.size() %>" emptyResultsMessage="no-todo-items">
                <liferay-ui:search-container-results results="<%= todoItems %>" />

                <liferay-ui:search-container-row className="com.example.todo.model.TodoItem" modelVar="todoItem">
                    <liferay-ui:search-container-column-text name="title" property="title" />

                    <liferay-ui:search-container-column-date name="due-date" property="dueDate" />

                    <liferay-ui:search-container-column-text name="priority">
                        <span class="badge badge-info"><%= todoItem.getPriority() %></span>
                    </liferay-ui:search-container-column-text>

                    <liferay-ui:search-container-column-text name="status">
                        <c:choose>
                            <c:when test="<%= todoItem.isCompleted() %>">
                                <span class="badge badge-success"><liferay-ui:message key="completed" /></span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge badge-warning"><liferay-ui:message key="pending" /></span>
                            </c:otherwise>
                        </c:choose>
                    </liferay-ui:search-container-column-text>

                    <liferay-ui:search-container-column-jsp path="/todo_actions.jsp" />
                </liferay-ui:search-container-row>

                <liferay-ui:search-iterator />
            </liferay-ui:search-container>
        </div>
    </div>

    <div class="card mt-4">
        <div class="card-header">
            <div class="card-title">
                <h3>Uploaded Files</h3>
            </div>
        </div>
        <div class="card-body">
            <c:choose>
                <c:when test="${empty uploadedFiles}">
                    <div class="alert alert-info">
                        <liferay-ui:message key="no-files-uploaded" />
                    </div>
                </c:when>
                <c:otherwise>
                    <ul class="list-group">
                        <c:forEach items="${uploadedFiles}" var="file">
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                <div>
                                    <i class="icon-file-text"></i> ${file.fileName}
                                    <span class="text-muted ml-2"><fmt:formatDate value="${file.uploadDate}" pattern="dd/MM/yyyy HH:mm" /></span>
                                </div>
                                <div>
                                    <portlet:resourceURL id="/todo/downloadFile" var="downloadURL">
                                        <portlet:param name="fileId" value="${file.fileId}" />
                                    </portlet:resourceURL>
                                    <a href="${downloadURL}" class="btn btn-sm btn-outline-primary">
                                        <i class="icon-download"></i> <liferay-ui:message key="download" />
                                    </a>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>