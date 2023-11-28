package com.w4t3rcs.todolist.controller.user.todo;

import com.w4t3rcs.todolist.model.data.dao.TodoListRepository;
import com.w4t3rcs.todolist.model.entity.TodoList;
import com.w4t3rcs.todolist.model.service.state.FinishedTodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/todo/edit/{id}")
@Controller
public class UpdateTodoController {
    private final TodoListRepository todoListRepository;
    private final FinishedTodoService finishedTodoService;

    @Autowired
    public UpdateTodoController(TodoListRepository todoListRepository, FinishedTodoService finishedTodoService) {
        this.todoListRepository = todoListRepository;
        this.finishedTodoService = finishedTodoService;
    }

    @ModelAttribute(name = "list")
    public TodoList todoList(@PathVariable Long id) {
        return todoListRepository.findById(id).orElse(null);
    }

    @GetMapping
    public String getUpdateTodo(@PathVariable Long id) {
        boolean finished = finishedTodoService.isFinished(id);
        return finished ? "redirect:/todo/{id}" : "todo/update";
    }

    @PutMapping
    public String putUpdateTodo(@PathVariable Long id, @ModelAttribute(name = "list") @Valid TodoList todoList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "todo/update";
        todoListRepository.update(todoList);
        return "redirect:/todo";
    }
}
