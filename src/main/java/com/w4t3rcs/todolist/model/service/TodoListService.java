package com.w4t3rcs.todolist.model.service;

import com.w4t3rcs.todolist.model.data.dao.TodoListRepository;
import com.w4t3rcs.todolist.model.entity.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TodoListService {
    private final TodoListRepository todoListRepository;

    @Autowired
    public TodoListService(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
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
                .map(TodoList::getDeadline)
                .filter(deadline -> deadline.before(now))
                .forEach(todoListRepository::deleteByDeadline);
    }

    private Timestamp getNow() {
        LocalDateTime now = LocalDateTime.now();
        return Timestamp.valueOf(now);
    }
}
