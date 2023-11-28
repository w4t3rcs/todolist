package com.w4t3rcs.todolist.config;

import com.w4t3rcs.todolist.model.properties.EmailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class IntegrationConfig {
    @Bean
    public JavaMailSender javaMailSender(EmailProperties emailProperties) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(emailProperties.getHost());
        javaMailSender.setPort(emailProperties.getPort());
        javaMailSender.setUsername(emailProperties.getUsername());
        javaMailSender.setPassword(emailProperties.getPassword());

        Properties properties = javaMailSender.getJavaMailProperties();
        properties.setProperty("mail.imap.ssl.enable", "false");
        properties.setProperty("mail.imap.starttls.enable", "true");

        return javaMailSender;
    }
}
