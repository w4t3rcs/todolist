package com.w4t3rcs.todolist.controller.admin.rest;

import com.w4t3rcs.todolist.model.data.dao.TodoListRepository;
import com.w4t3rcs.todolist.model.entity.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/todo/api/todos" , produces = "application/json")
public class AdminTodoController {
    private final TodoListRepository todoListRepository;

    @Autowired
    public AdminTodoController(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    @GetMapping
    public ResponseEntity<List<TodoList>> getAllTodos() {
        List<TodoList> all = todoListRepository.findAll();
        if (all.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<TodoList>> getAllTodosByUsername(@PathVariable(name = "username") String username) {
        List<TodoList> all = todoListRepository.findAllByUsername(username);
        if (all.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
