package com.hsbc.codeChallenge.services;

import com.hsbc.codeChallenge.Application;
import com.hsbc.codeChallenge.entities.Post;
import com.hsbc.codeChallenge.entities.User;
import com.hsbc.codeChallenge.exceptions.UserNotFoundException;
import com.hsbc.codeChallenge.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    @Transactional
    public void shouldReturnPostsWhenUserExists() {
        String userId = "userId";
        User user = new User(userId);
        user.setPosts(Arrays.asList(new Post("Post1", userId)));
        userRepository.save(user);
        userRepository.flush();
        List<Post> posts = userService.getPostsByUserId(userId);
        assertEquals(1, posts.size());
        assertEquals("Post1", posts.get(0).getText());
    }

    @Test
    public void validateUserShouldReturnUserWhenUserCreated() {
        String userId = "userId";
        userRepository.save(new User(userId));
        User user = userService.validateUser(userId);
        assertNotNull(user);
    }

    @Test(expected = UserNotFoundException.class)
    public void validateUserShouldThrowUserNotFoundExceptionWhenUserNotCreated() {
        String userId = "notExistingUserId";
        userService.validateUser(userId);
    }

}