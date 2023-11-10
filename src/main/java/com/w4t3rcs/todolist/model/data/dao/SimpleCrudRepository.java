package com.w4t3rcs.todolist.model.data.dao;

import java.util.List;
import java.util.Optional;

public interface SimpleCrudRepository<T, ID> {
    Optional<T> save(T t);

    Optional<T> find(T t);

    Optional<T> findById(ID id);

    List<T> findAll();

    Optional<T> update(T t);

    void delete(T t);
}
