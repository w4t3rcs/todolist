package com.w4t3rcs.todolist.config;

import com.w4t3rcs.todolist.model.data.dao.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetails(UserRepository userRepository) {
        final String exceptionMessage = "Invalid username or password";
        return username -> userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(exceptionMessage));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(
                matcherRegistry -> matcherRegistry.requestMatchers("/todo", "todo/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/todo/admin", "todo/api/**").hasRole("ADMIN")
                        .requestMatchers("/", "/register", "/**").permitAll())
                .formLogin(formLogin -> formLogin.loginPage("/login").defaultSuccessUrl("/todo").permitAll())
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/").permitAll())
                .build();
    }
}
