package com.w4t3rcs.todolist.controller.user.todo;

import com.w4t3rcs.todolist.model.data.dao.TodoListRepository;
import com.w4t3rcs.todolist.model.entity.TodoList;
import com.w4t3rcs.todolist.model.service.state.FinishedTodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/todo/{id}")
@Controller
public class GetTodoController {
    private final TodoListRepository todoListRepository;
    private final FinishedTodoService finishedTodoService;

    @Autowired
    public GetTodoController(TodoListRepository todoListRepository, FinishedTodoService finishedTodoService) {
        this.todoListRepository = todoListRepository;
        this.finishedTodoService = finishedTodoService;
    }

    @ModelAttribute(name = "list")
    public TodoList todoList(@PathVariable Long id) {
        return todoListRepository.findById(id).orElse(null);
    }

    @GetMapping
    public String getCreateTodo(@PathVariable Long id) {
        boolean finished = finishedTodoService.isFinished(id);
        return finished ? "todo/get_finished" : "todo/get";
    }
}
