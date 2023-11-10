package com.w4t3rcs.todolist;

import com.w4t3rcs.todolist.model.data.dao.TodoListRepository;
import com.w4t3rcs.todolist.model.entity.TodoList;
import com.w4t3rcs.todolist.model.data.transformer.Transformer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class RepositoryTest {
	private final TodoListRepository repository;
	private final Transformer<TodoList> transformer;

	@Autowired
	public RepositoryTest(TodoListRepository repository, Transformer<TodoList> transformer) {
		this.repository = repository;
		this.transformer = transformer;
	}

	@Test
	void findTest() {
		List<TodoList> all = repository.findAll();
		System.out.println(all);
	}

	@Test
	void saveTest() {
//		User user = new User("test", "test", "test@gmail.com");
		TodoList todoList = new TodoList("todoListTestName", "todoListTestDescription", Timestamp.valueOf(LocalDateTime.of(2025, 11, 23, 23, 3, 1)));
		Optional<TodoList> save = repository.save(todoList);
		System.out.println(save);
	}

	@Test
	void updateTest() {
		TodoList todoList = new TodoList("todoListTestName", "todoListUpdTestDescription", Timestamp.valueOf(LocalDateTime.of(2134, 11, 23, 23, 3, 1)));
		Optional<TodoList> update = repository.update(todoList);
		System.out.println(update);
	}

	@Test
	void deleteTest() {
		Optional<TodoList> user = repository.findById(2L);
		repository.delete(user.orElse(null));
	}
}
