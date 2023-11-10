package com.w4t3rcs.todolist.controller.user.todo;

import com.w4t3rcs.todolist.model.data.dao.TodoListRepository;
import com.w4t3rcs.todolist.model.entity.TodoList;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/todo/delete/{id}")
@Controller
public class DeleteTodoController {
    private final TodoListRepository todoListRepository;

    @Autowired
    public DeleteTodoController(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    @ModelAttribute(name = "list")
    public TodoList todoList(@PathVariable Long id) {
        return todoListRepository.findById(id).orElse(null);
    }

    @GetMapping
    public String getDeleteTodo(@PathVariable Long id) {
        return "todo/delete";
    }

    @DeleteMapping
    public String deleteDeleteTodo(@PathVariable Long id, @ModelAttribute(name = "list") @Valid TodoList todoList) {
        todoListRepository.delete(todoList);
        return "redirect:/todo";
    }
}
