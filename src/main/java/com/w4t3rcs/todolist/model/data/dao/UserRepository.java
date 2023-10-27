package com.w4t3rcs.todolist.model.data.dao;

import com.w4t3rcs.todolist.model.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
