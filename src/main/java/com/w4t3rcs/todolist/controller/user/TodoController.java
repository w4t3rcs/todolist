package com.w4t3rcs.todolist.controller.user;

import com.w4t3rcs.todolist.model.data.dao.TodoListRepository;
import com.w4t3rcs.todolist.model.entity.TodoList;
import com.w4t3rcs.todolist.model.service.state.FinishedTodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/todo")
@Controller
public class TodoController {
    private final TodoListRepository todoListRepository;
    private final FinishedTodoService finishedTodoService;

    @Autowired
    public TodoController(TodoListRepository todoListRepository, FinishedTodoService finishedTodoService) {
        this.todoListRepository = todoListRepository;
        this.finishedTodoService = finishedTodoService;
    }

    @ModelAttribute(name = "lists")
    public List<TodoList> lists() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        finishedTodoService.clearByDeadline();
        return todoListRepository.findAllByUsername(username);
    }

    @GetMapping
    public String getTodo() {
        return "todo";
    }
}
