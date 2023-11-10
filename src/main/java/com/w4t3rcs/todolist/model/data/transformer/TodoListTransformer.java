package com.w4t3rcs.todolist.model.data.transformer;

import com.w4t3rcs.todolist.model.entity.TodoList;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class TodoListTransformer implements Transformer<TodoList> {
    @Override
    public void transform(TodoList todoList) {
        transformUsername(todoList);
        transformCreatedAt(todoList);
        todoList.setFinished(false);
    }

    private void transformUsername(TodoList todoList) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        todoList.setUsername(username);
    }

    private void transformCreatedAt(TodoList todoList) {
        LocalDateTime now = LocalDateTime.now();
        todoList.setCreatedAt(Timestamp.valueOf(now));
    }
}
