package com.w4t3rcs.todolist;

import com.w4t3rcs.todolist.model.data.dao.UserRepository;
import com.w4t3rcs.todolist.model.entity.User;
import com.w4t3rcs.todolist.model.security.transformer.Transformer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class UserRepositoryTest {
	private final UserRepository userRepository;
	private final Transformer<User> userTransformer;

	@Autowired
	public UserRepositoryTest(UserRepository userRepository, Transformer<User> userTransformer) {
		this.userRepository = userRepository;
		this.userTransformer = userTransformer;
	}

	@Test
	void findTest() {
		User user = userRepository.findById("test").orElse(null);
		System.out.println(user);
	}

	@Test
	void saveTest() {
		User user = new User("test", "test", "test@gmail.com");
		userTransformer.transform(user);
		userRepository.save(user);
	}

	@Test
	void updateTest() {
		User user = new User("test", "megatest", "test@gmail.com");
		userTransformer.transform(user);
		userRepository.update(user);
	}

	@Test
	void deleteTest() {
		Optional<User> user = userRepository.findById("test");
		userRepository.delete(user.orElse(null));
	}
}
