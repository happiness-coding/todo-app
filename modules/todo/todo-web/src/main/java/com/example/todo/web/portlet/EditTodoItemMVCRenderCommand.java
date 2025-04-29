// EditTodoItemMVCRenderCommand.java
package com.example.todo.web.portlet;

import com.example.todo.model.TodoItem;
import com.example.todo.service.TodoItemService;
import com.example.todo.web.constants.TodoPortletKeys;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
    immediate = true,
    property = {
        "javax.portlet.name=" + TodoPortletKeys.TODO,
        "mvc.command.name=/todo/edit"
    },
    service = MVCRenderCommand.class
)
public class EditTodoItemMVCRenderCommand implements MVCRenderCommand {

    @Override
    public String render(RenderRequest renderRequest, RenderResponse renderResponse)
            throws PortletException {

        long todoItemId = ParamUtil.getLong(renderRequest, "todoItemId", 0);

        try {
            if (todoItemId > 0) {
                TodoItem todoItem = todoItemService.getTodoItem(todoItemId);
                renderRequest.setAttribute("todoItem", todoItem);
            }
        } catch (PortalException e) {
            throw new PortletException("Error retrieving todo item", e);
        }

        return "/edit.jsp";
    }

    @Reference
    private TodoItemService todoItemService;
}