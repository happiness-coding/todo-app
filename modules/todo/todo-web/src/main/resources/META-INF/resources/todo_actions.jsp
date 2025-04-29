<%-- todo_actions.jsp --%>
    <%@ include file="/init.jsp" %>

    <%
    ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
    TodoItem todoItem = (TodoItem)row.getObject();
    %>

    <liferay-ui:icon-menu direction="left-side" icon="" markupView="lexicon" message="" showWhenSingleIcon="<%= true %>">
        <portlet:renderURL var="editURL">
            <portlet:param name="mvcRenderCommandName" value="/todo/edit" />
            <portlet:param name="todoItemId" value="<%= String.valueOf(todoItem.getTodoItemId()) %>" />
        </portlet:renderURL>

        <liferay-ui:icon
            message="edit"
            url="<%= editURL %>"
        />

        <portlet:actionURL name="/todo/delete" var="deleteURL">
            <portlet:param name="todoItemId" value="<%= String.valueOf(todoItem.getTodoItemId()) %>" />
        </portlet:actionURL>

        <liferay-ui:icon
            message="delete"
            url="<%= deleteURL %>"
            useDialog="<%= true %>"
        />
    </liferay-ui:icon-menu>