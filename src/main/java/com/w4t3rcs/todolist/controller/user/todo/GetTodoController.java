package com.w4t3rcs.todolist.controller.user.todo;

import com.w4t3rcs.todolist.model.data.dao.TodoListRepository;
import com.w4t3rcs.todolist.model.entity.TodoList;
import com.w4t3rcs.todolist.model.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/todo/{id}")
@Controller
public class GetTodoController {
    private final TodoListRepository todoListRepository;
    private final TodoListService todoListService;

    @Autowired
    public GetTodoController(TodoListRepository todoListRepository, TodoListService todoListService) {
        this.todoListRepository = todoListRepository;
        this.todoListService = todoListService;
    }

    @ModelAttribute(name = "list")
    public TodoList todoList(@PathVariable Long id) {
        return todoListRepository.findById(id).orElse(null);
    }

    @GetMapping
    public String getCreateTodo(@PathVariable Long id) {
        boolean finished = todoListService.checkFinished(id);
        return finished ? "todo/get_finished" : "todo/get";
    }
}
