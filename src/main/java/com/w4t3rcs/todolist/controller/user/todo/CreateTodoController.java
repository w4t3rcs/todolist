package com.w4t3rcs.todolist.controller.user.todo;

import com.w4t3rcs.todolist.model.data.dao.TodoListRepository;
import com.w4t3rcs.todolist.model.entity.TodoList;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/todo/create")
@Controller
public class CreateTodoController {
    private final TodoListRepository todoListRepository;

    @Autowired
    public CreateTodoController(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    @ModelAttribute(name = "list")
    public TodoList todoList() {
        return new TodoList();
    }

    @GetMapping
    public String getCreateTodo() {
        return "todo/create";
    }

    @PostMapping
    public String postCreateTodo(@ModelAttribute(name = "list") @Valid TodoList todoList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "todo/create";
        todoListRepository.save(todoList);
        return "redirect:/todo";
    }
}
