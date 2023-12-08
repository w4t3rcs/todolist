package com.w4t3rcs.todolist.model.service.state;

import com.w4t3rcs.todolist.model.constants.EmailConstants;
import com.w4t3rcs.todolist.model.properties.ExpirationEmailProperties;
import com.w4t3rcs.todolist.model.data.dao.TodoListRepository;
import com.w4t3rcs.todolist.model.data.dao.UserRepository;
import com.w4t3rcs.todolist.model.entity.TodoList;
import com.w4t3rcs.todolist.model.entity.User;
import com.w4t3rcs.todolist.model.service.message.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FinishedTodoService {
    private final TodoListRepository todoListRepository;
    private final UserRepository userRepository;
    private final EmailService<SimpleMailMessage> emailService;
    private final ExpirationEmailProperties messageProperties;

    @Autowired
    public FinishedTodoService(TodoListRepository todoListRepository, UserRepository userRepository, EmailService<SimpleMailMessage> emailService, ExpirationEmailProperties messageProperties) {
        this.todoListRepository = todoListRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.messageProperties = messageProperties;
    }

    public void clearByDeadline() {
        Timestamp now = getNow();
        List<TodoList> all = todoListRepository.findAll();
        all.stream()
                .filter(todoList -> todoList.getDeadline().before(now))
                .forEach(todoList -> {
                    todoListRepository.deleteByDeadline(todoList.getDeadline());
                    User user = userRepository.findById(todoList.getUsername()).orElseThrow(RuntimeException::new);
                    emailService.sendMessage(EmailConstants.ORGANIZATION_EMAIL, user.getEmail(), messageProperties.getSubject(), messageProperties.getExpirationMessage(todoList));
                });
    }

    private Timestamp getNow() {
        LocalDateTime now = LocalDateTime.now();
        return Timestamp.valueOf(now);
    }

    public boolean isFinished(Long id) {
        Optional<TodoList> optionalTodoList = todoListRepository.findById(id);
        TodoList todoList = optionalTodoList.orElseThrow(() -> new RuntimeException("No todo with this id!"));
        return todoList.isFinished();
    }
}
