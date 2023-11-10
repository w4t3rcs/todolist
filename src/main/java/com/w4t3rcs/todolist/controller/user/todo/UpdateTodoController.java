package com.w4t3rcs.todolist.controller.user.todo;

import com.w4t3rcs.todolist.model.data.dao.TodoListRepository;
import com.w4t3rcs.todolist.model.entity.TodoList;
import com.w4t3rcs.todolist.model.service.TodoListService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/todo/edit/{id}")
@Controller
public class UpdateTodoController {
    private final TodoListRepository todoListRepository;
    private final TodoListService todoListService;

    @Autowired
    public UpdateTodoController(TodoListRepository todoListRepository, TodoListService todoListService) {
        this.todoListRepository = todoListRepository;
        this.todoListService = todoListService;
    }

    @ModelAttribute(name = "list")
    public TodoList todoList(@PathVariable Long id) {
        return todoListRepository.findById(id).orElse(null);
    }

    @GetMapping
    public String getUpdateTodo(@PathVariable Long id) {
        boolean finished = todoListService.checkFinished(id);
        return finished ? "redirect:/todo/{id}" : "todo/update";
    }

    @PutMapping
    public String putUpdateTodo(@PathVariable Long id, @ModelAttribute(name = "list") @Valid TodoList todoList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "todo/update";
        todoListRepository.update(todoList);
        return "redirect:/todo";
    }
}
