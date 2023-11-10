package com.w4t3rcs.todolist.model.data.dao;

import com.w4t3rcs.todolist.model.entity.TodoList;

import java.sql.Timestamp;
import java.util.List;

public interface TodoListRepository extends SimpleCrudRepository<TodoList, Long> {
    List<TodoList> findAllByUsername(String username);

    void deleteByDeadline(Timestamp deadline);
}
