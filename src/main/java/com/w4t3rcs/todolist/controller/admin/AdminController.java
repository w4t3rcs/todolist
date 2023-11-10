package com.w4t3rcs.todolist.controller.admin;

import com.w4t3rcs.todolist.model.entity.TodoList;
import com.w4t3rcs.todolist.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@RequestMapping("todo/admin")
@Controller
public class AdminController {
    public final RestTemplate restTemplate;

    @Autowired
    public AdminController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String getAdmin() {
        return "admin";
    }
}
