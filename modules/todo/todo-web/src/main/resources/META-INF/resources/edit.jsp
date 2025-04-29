<%-- edit.jsp --%>
<%@ include file="/META-INF/resources/init.jsp" %>

<%
TodoItem todoItem = (TodoItem)request.getAttribute("todoItem");
long todoItemId = todoItem != null ? todoItem.getTodoItemId() : 0;
String title = todoItem != null ? todoItem.getTitle() : "";
String description = todoItem != null ? todoItem.getDescription() : "";
Date dueDate = todoItem != null ? todoItem.getDueDate() : null;
int priority = todoItem != null ? todoItem.getPriority() : 3;
long assigneeUserId =  0;
boolean completed = todoItem != null ? todoItem.isCompleted() : false;
%>

<portlet:renderURL var="viewURL">
    <portlet:param name="mvcRenderCommandName" value="/todo/view" />
</portlet:renderURL>

<portlet:actionURL name="/todo/save" var="saveURL" />

<div class="container-fluid">
    <h2>
        <c:choose>
            <c:when test="<%= todoItemId > 0 %>">
                <liferay-ui:message key="edit-todo-item" />
            </c:when>
            <c:otherwise>
                <liferay-ui:message key="add-todo-item" />
            </c:otherwise>
        </c:choose>
    </h2>

    <liferay-ui:error key="com.example.todo.exception.TodoValidationException" message="invalid-todo-item-data" />

    <aui:form action="<%= saveURL %>" method="post" name="fm">
        <aui:input name="todoItemId" type="hidden" value="<%= todoItemId %>" />

        <aui:fieldset>
            <aui:input name="title" label="title" value="<%= title %>" required="true" />

            <aui:input name="description" label="description" value="<%= description %>" type="textarea" />

            <aui:input name="dueDate" label="due-date" value="<%= dueDate %>" type="date" />

            <aui:select name="priority" label="priority">
                <aui:option value="1" selected="<%= priority == 1 %>">1 - <liferay-ui:message key="lowest" /></aui:option>
                <aui:option value="2" selected="<%= priority == 2 %>">2 - <liferay-ui:message key="low" /></aui:option>
                <aui:option value="3" selected="<%= priority == 3 %>">3 - <liferay-ui:message key="medium" /></aui:option>
                <aui:option value="4" selected="<%= priority == 4 %>">4 - <liferay-ui:message key="high" /></aui:option>
                <aui:option value="5" selected="<%= priority == 5 %>">5 - <liferay-ui:message key="highest" /></aui:option>
            </aui:select>

            <aui:input name="assigneeUserId" label="assignee" value="<%= assigneeUserId %>">
                <aui:validator name="number" />
            </aui:input>

            <c:if test="<%= todoItemId > 0 %>">
                <aui:input name="completed" label="completed" type="checkbox" checked="<%= completed %>" />
            </c:if>
        </aui:fieldset>

        <aui:button-row>
            <aui:button type="submit" value="save" cssClass="btn-primary" />
            <aui:button type="cancel" value="cancel" href="<%= viewURL %>" />
        </aui:button-row>
    </aui:form>
</div>