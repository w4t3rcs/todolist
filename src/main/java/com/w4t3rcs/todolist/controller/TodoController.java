package com.w4t3rcs.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/todo")
@Controller
public class TodoController {
    @GetMapping
    public String getTodo() {
        return "todo";
    }
}
