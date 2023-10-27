package com.w4t3rcs.todolist.config;

import com.w4t3rcs.todolist.model.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetails(PasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(new User("admin", passwordEncoder.encode("admin"), "admin@gmail.com"));
        userDetailsManager.createUser(new User("default", "default", "default@gmail.com"));
        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(
                matcherRegistry -> matcherRegistry.requestMatchers("/todo").hasRole("USER")
                        .requestMatchers("/", "/register", "/**").permitAll())
                .formLogin(formLogin -> formLogin.loginPage("/login").defaultSuccessUrl("/todo"))
                .build();
    }
}
