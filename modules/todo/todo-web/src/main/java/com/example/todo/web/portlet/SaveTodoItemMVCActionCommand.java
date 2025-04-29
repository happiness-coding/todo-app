package com.example.todo.web.portlet;

import com.example.todo.exception.TodoException;
import com.example.todo.model.TodoItem;
import com.example.todo.service.TodoItemService;
import com.example.todo.web.constants.TodoPortletKeys;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Date;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
    immediate = true,
    property = {
        "javax.portlet.name=" + TodoPortletKeys.TODO,
        "mvc.command.name=/todo/save"
    },
    service = MVCActionCommand.class
)
public class SaveTodoItemMVCActionCommand implements MVCActionCommand {

    private static final Log _log = LogFactoryUtil.getLog(SaveTodoItemMVCActionCommand.class);

    @Override
    public boolean processAction(ActionRequest actionRequest, ActionResponse actionResponse)
            throws PortletException {

        long todoItemId = ParamUtil.getLong(actionRequest, "todoItemId", 0);
        String title = ParamUtil.getString(actionRequest, "title");
        String description = ParamUtil.getString(actionRequest, "description");
        Date dueDate = ParamUtil.getDate(actionRequest, "dueDate", null);
        int priority = ParamUtil.getInteger(actionRequest, "priority", 3);
        long assigneeUserId = ParamUtil.getLong(actionRequest, "assigneeUserId", 0);
        boolean completed = ParamUtil.getBoolean(actionRequest, "completed", false);

        _log.info("Processing todo item save with title: " + title);

        try {
            ServiceContext serviceContext = ServiceContextFactory.getInstance(
                TodoItem.class.getName(), actionRequest);

            TodoItem todoItem;
            java.sql.Date sqlDueDate = dueDate != null ? new java.sql.Date(dueDate.getTime()) : null;

            if (todoItemId > 0) {
                // Update existing TodoItem
                _log.info("Updating existing todo item with ID: " + todoItemId);
                todoItem = todoItemService.updateTodoItem(
                    todoItemId, title, description, sqlDueDate, priority,
                    assigneeUserId, completed, serviceContext);

                SessionMessages.add(actionRequest, "todoItemUpdated");
            } else {
                // Create new TodoItem
                _log.info("Creating new todo item");
                todoItem = todoItemService.createTodoItem(
                    title, description, sqlDueDate, priority, assigneeUserId, serviceContext);

                SessionMessages.add(actionRequest, "todoItemAdded");
            }

            _log.info("Todo item saved successfully with ID: " + todoItem.getTodoItemId());

            actionResponse.setRenderParameter("todoItemId", String.valueOf(todoItem.getTodoItemId()));
            actionResponse.setRenderParameter("mvcRenderCommandName", "/todo/view");

            return true;

        } catch (Exception e) {
            _log.error("Error saving todo item: ", e);
            SessionErrors.add(actionRequest, e.getClass().getName());
            actionResponse.setRenderParameter("mvcRenderCommandName", "/todo/edit");
        }

        return false;
    }

    @Reference
    private TodoItemService todoItemService;
}