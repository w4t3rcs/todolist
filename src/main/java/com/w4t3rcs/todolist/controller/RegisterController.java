package com.w4t3rcs.todolist.controller;

import com.w4t3rcs.todolist.model.data.dao.UserRepository;
import com.w4t3rcs.todolist.model.entity.User;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/register")
@Controller
public class RegisterController {
    private final UserRepository userRepository;

    @Autowired
    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ModelAttribute("user")
    public User user() {
        return new User();
    }

    @GetMapping
    public String getRegistry() {
        return "register";
    }

    @PostMapping
    public String postRegistry(@ModelAttribute @Valid User user, BindingResult result) {
        if (result.hasErrors()) return "register";
        log.info("Executed POST method on the user: {}!", user);
        userRepository.save(user);
        return "redirect:/";
    }
}
