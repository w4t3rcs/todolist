package com.w4t3rcs.todolist.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/logout")
@Controller
public class LogoutController {
    @GetMapping
    public String getLogout() {
        return "logout";
    }

    @PostMapping
    public String postLogout() {
        return "redirect:/";
    }
}
