package com.w4t3rcs.todolist.model.properties;

import com.w4t3rcs.todolist.model.entity.TodoList;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "todo.email.message")
@Component
public class ExpirationEmailProperties {
    private String subject;
    private String body;

    public String getExpirationMessage(TodoList todoList) {
        return String.format(body, todoList.getId(), todoList.getName(), todoList.getDescription(), todoList.getCreatedAt().toString(), todoList.getDeadline().toString());
    }
}