package com.w4t3rcs.todolist.controller.user.account;

import com.w4t3rcs.todolist.model.data.dao.UserRepository;
import com.w4t3rcs.todolist.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/todo/account")
@Controller
public class GetAccountController {
    private final UserRepository userRepository;

    @Autowired
    public GetAccountController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ModelAttribute
    public User user() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return userRepository.findById(name).orElseThrow(RuntimeException::new);
    }

    @GetMapping
    public String getProfile() {
        return "account/account";
    }
}
