package com.w4t3rcs.todolist.model.service.io.transfer;

import com.w4t3rcs.todolist.model.entity.User;
import com.w4t3rcs.todolist.model.exception.InvalidTransferException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class UserAvatarToPathTransferService implements OneDirectionTransfer<User> {
    @Override
    public void transfer(User from) throws IOException {
        if (from == null) throw new InvalidTransferException();
        if (from.getAvatar().isEmpty()) return;
        setNewAvatarFilename(from);
        createIfNotExists(from);
        transferToNewPath(from);
    }

    private void createIfNotExists(User user) throws IOException {
        Path avatarLocation = getAvatarLocation(user);
        if (Files.notExists(avatarLocation)) {
            Files.createFile(getAvatarLocation(user));
        }
    }

    private void transferToNewPath(User user) throws IOException {
        byte[] avatarBytes = user.getAvatar().getBytes();
        try (OutputStream transferToNewPathStream = Files.newOutputStream(getAvatarLocation(user))) {
            transferToNewPathStream.write(avatarBytes);
        }
    }

    private Path getAvatarLocation(User user) {
        String staticLocation = "src/main/resources/static/";
        return Path.of(staticLocation + user.getAvatarFullLocation());
    }

    private void setNewAvatarFilename(User user) {
        String type = user.getAvatar().getOriginalFilename().split("\\.")[1];
        user.setAvatarFilename(user.getUsername() + "." + type);
    }
}
