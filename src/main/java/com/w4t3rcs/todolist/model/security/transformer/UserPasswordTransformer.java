package com.w4t3rcs.todolist.model.security.transformer;

import com.w4t3rcs.todolist.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserPasswordTransformer implements Transformer<User> {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserPasswordTransformer(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void transform(User user) {
        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
    }
}
