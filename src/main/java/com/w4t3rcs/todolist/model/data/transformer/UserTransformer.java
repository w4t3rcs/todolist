package com.w4t3rcs.todolist.model.data.transformer;

import com.w4t3rcs.todolist.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserTransformer implements Transformer<User> {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserTransformer(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void transform(User user) {
        transformPassword(user);
        transformRole(user);
        transformAvatarLocation(user);
    }

    private void transformPassword(User user) {
        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
    }

    private void transformRole(User user) {
        user.setRole("USER");
    }

    private void transformAvatarLocation(User user) {
        user.setRole("default.png");
    }
}
