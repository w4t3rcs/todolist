package com.w4t3rcs.todolist.model.data.dao.jdbc;

import com.w4t3rcs.todolist.model.data.dao.UserRepository;
import com.w4t3rcs.todolist.model.entity.User;
import com.w4t3rcs.todolist.model.data.transformer.Transformer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {
    @PersistenceContext
    private final EntityManager entityManager;
    private final Transformer<User> transformer;

    @Autowired
    public JdbcUserRepository(EntityManager entityManager, Transformer<User> transformer) {
        this.entityManager = entityManager;
        this.transformer = transformer;
    }

    @Override
    @Transactional
    public Optional<User> save(User user) {
        transformer.transform(user);
        entityManager.persist(user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> find(User user) {
        return findById(user.getId());
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.of(entityManager.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    @Transactional
    public Optional<User> update(User user) {
        User merged = entityManager.merge(user);
        return Optional.of(merged);
    }

    @Override
    @Transactional
    public void delete(User user) {
        User stub = update(user).orElse(null);
        entityManager.remove(stub);
    }
}