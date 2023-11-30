package com.w4t3rcs.todolist.controller.user.account;

import com.w4t3rcs.todolist.model.data.dao.UserRepository;
import com.w4t3rcs.todolist.model.entity.User;
import com.w4t3rcs.todolist.model.exception.InvalidTransferException;
import com.w4t3rcs.todolist.model.service.io.transfer.UserAvatarToPathTransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RequestMapping("/todo/account/edit")
@Controller
public class EditAccountController {
    private final UserRepository userRepository;
    private final UserAvatarToPathTransferService avatarToPathTransfer;

    @Autowired
    public EditAccountController(UserRepository userRepository, UserAvatarToPathTransferService avatarToPathTransfer) {
        this.userRepository = userRepository;
        this.avatarToPathTransfer = avatarToPathTransfer;
    }

    @ModelAttribute
    public User user() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return userRepository.findById(name).orElseThrow(RuntimeException::new);
    }

    @GetMapping
    public String getProfile() {
        return "account/edit";
    }

    @PutMapping(consumes = {"multipart/form-data"})
    public String putProfile(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "account/edit";
        try {
            avatarToPathTransfer.transfer(user);
            userRepository.update(user);
        } catch (IOException e) {
            log.warn(e.getMessage() + "! Avatar Transfer Exception");
            throw new InvalidTransferException();
        }
        return "redirect:/todo/account";
    }
}
