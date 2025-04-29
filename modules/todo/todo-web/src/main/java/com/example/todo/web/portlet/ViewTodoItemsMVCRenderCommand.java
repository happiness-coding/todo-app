// ViewTodoItemsMVCRenderCommand.java
package com.example.todo.web.portlet;

import com.example.todo.model.TodoItem;
import com.example.todo.service.TodoItemService;
import com.example.todo.web.constants.TodoPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
    immediate = true,
    property = {
        "javax.portlet.name=" + TodoPortletKeys.TODO,
        "mvc.command.name=/", // Default view
        "mvc.command.name=/todo/view"
    },
    service = MVCRenderCommand.class
)
public class ViewTodoItemsMVCRenderCommand implements MVCRenderCommand {

    @Override
    public String render(RenderRequest renderRequest, RenderResponse renderResponse)
            throws PortletException {

        try {
            List<TodoItem> todoItems = todoItemService.getTodoItems();
            renderRequest.setAttribute("todoItems", todoItems);
        } catch (Exception e) {
            throw new PortletException("Error retrieving todo items", e);
        }

        return "/view.jsp";
    }

    @Reference
    private TodoItemService todoItemService;
}