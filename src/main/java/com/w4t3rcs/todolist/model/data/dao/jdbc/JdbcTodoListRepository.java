package com.w4t3rcs.todolist.model.data.dao.jdbc;

import com.w4t3rcs.todolist.model.data.dao.TodoListRepository;
import com.w4t3rcs.todolist.model.entity.TodoList;
import com.w4t3rcs.todolist.model.data.transformer.Transformer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTodoListRepository implements TodoListRepository {
    @PersistenceContext
    private final EntityManager entityManager;
    private final Transformer<TodoList> transformer;

    @Autowired
    public JdbcTodoListRepository(EntityManager entityManager, Transformer<TodoList> transformer) {
        this.entityManager = entityManager;
        this.transformer = transformer;
    }

    @Override
    @Transactional
    public Optional<TodoList> save(TodoList user) {
        transformer.transform(user);
        entityManager.persist(user);
        return Optional.of(user);
    }

    @Override
    public Optional<TodoList> find(TodoList user) {
        return findById(user.getId());
    }

    @Override
    public Optional<TodoList> findById(Long id) {
        return Optional.of(entityManager.find(TodoList.class, id));
    }

    @Override
    public List<TodoList> findAll() {
        return entityManager.createQuery("FROM TodoList", TodoList.class).getResultList();
    }

    @Override
    public List<TodoList> findAllByUsername(String username) {
        TypedQuery<TodoList> query = entityManager.createQuery("FROM TodoList WHERE username = :username", TodoList.class);
        query.setParameter("username", username);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Optional<TodoList> update(TodoList user) {
        TodoList merged = entityManager.merge(user);
        return Optional.of(merged);
    }

    @Override
    @Transactional
    public void delete(TodoList user) {
        TodoList stub = update(user).orElse(null);
        entityManager.remove(stub);
    }

    @Override
    @Transactional
    public void deleteByDeadline(Timestamp deadline) {
        TypedQuery<TodoList> query = entityManager.createQuery("FROM TodoList WHERE deadline = :deadline", TodoList.class);
        query.setParameter("deadline", deadline);
        TodoList singleResult = query.getSingleResult();
        delete(singleResult);
    }
}