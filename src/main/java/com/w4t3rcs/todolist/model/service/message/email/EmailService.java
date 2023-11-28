package com.w4t3rcs.todolist.model.service.message.email;

public interface EmailService<T> {
    void sendMessage(String to, String subject, String body);

    T createMessage(String to, String subject, String body);
}
