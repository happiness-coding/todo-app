// DeleteTodoItemMVCActionCommand.java
package com.example.todo.web.portlet;

import com.example.todo.service.TodoItemService;
import com.example.todo.web.constants.TodoPortletKeys;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
    immediate = true,
    property = {
        "javax.portlet.name=" + TodoPortletKeys.TODO,
        "mvc.command.name=/todo/delete"
    },
    service = MVCActionCommand.class
)
public class DeleteTodoItemMVCActionCommand implements MVCActionCommand {

    @Override
    public boolean processAction(ActionRequest actionRequest, ActionResponse actionResponse)
            throws PortletException {

        long todoItemId = ParamUtil.getLong(actionRequest, "todoItemId");

        try {
            todoItemService.deleteTodoItem(todoItemId);

            SessionMessages.add(actionRequest, "todoItemDeleted");
            actionResponse.setRenderParameter("mvcRenderCommandName", "/todo/view");

            return true;

        } catch (PortalException e) {
            SessionErrors.add(actionRequest, e.getClass().getName());
            actionResponse.setRenderParameter("mvcRenderCommandName", "/todo/view");
            return false;
        }
    }

    @Reference
    private TodoItemService todoItemService;
}