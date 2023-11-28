package com.w4t3rcs.todolist.model.service;

import com.w4t3rcs.todolist.model.ExpirationEmailProperties;
import com.w4t3rcs.todolist.model.data.dao.TodoListRepository;
import com.w4t3rcs.todolist.model.data.dao.UserRepository;
import com.w4t3rcs.todolist.model.entity.TodoList;
import com.w4t3rcs.todolist.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TodoListService {
    private final TodoListRepository todoListRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ExpirationEmailProperties messageProperties;

    @Autowired
    public TodoListService(TodoListRepository todoListRepository, UserRepository userRepository, EmailService emailService, ExpirationEmailProperties messageProperties) {
        this.todoListRepository = todoListRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.messageProperties = messageProperties;
    }

    public boolean checkFinished(Long id) {
        Optional<TodoList> optionalTodoList = todoListRepository.findById(id);
        TodoList todoList = optionalTodoList.orElseThrow(() -> new RuntimeException("No todo with this id!"));
        return todoList.isFinished();
    }

    public void clearByDeadline() {
        Timestamp now = getNow();
        List<TodoList> all = todoListRepository.findAll();
        all.stream()
                .filter(todoList -> todoList.getDeadline().before(now))
                .forEach(todoList -> {
                    todoListRepository.deleteByDeadline(todoList.getDeadline());
                    User user = userRepository.findById(todoList.getUsername()).orElseThrow(RuntimeException::new);
                    emailService.sendEmail(user.getEmail(), messageProperties.getSubject(), messageProperties.getExpirationMessage(todoList));
                });
    }

    private Timestamp getNow() {
        LocalDateTime now = LocalDateTime.now();
        return Timestamp.valueOf(now);
    }
}
