package com.w4t3rcs.todolist.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "todo.email")
@Component
public class EmailProperties {
    private String username;
    private String password;
    private String host;
    private String mailbox;
    private long pollRate = 30000L;

    public String getImapUrl() {
        return "imaps://%s:%s@%s/%s".formatted(this.getUsername(), this.getPassword(), this.getHost(), this.getMailbox());
    }
}
