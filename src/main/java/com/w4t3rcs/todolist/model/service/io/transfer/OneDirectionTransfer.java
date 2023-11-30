package com.w4t3rcs.todolist.model.service.io.transfer;

import java.io.IOException;

public interface OneDirectionTransfer<T> {
    void transfer(T from) throws IOException;
}
