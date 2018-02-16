package com.hsbc.codeChallenge.services;

import com.hsbc.codeChallenge.entities.Post;
import com.hsbc.codeChallenge.entities.User;
import com.hsbc.codeChallenge.exceptions.UserNotFoundException;
import com.hsbc.codeChallenge.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @Before
    public void setUp(){
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    public void shouldReturnPostsWhenUserCreated() {
        String userId = "userId";
        User user = new User();
        user.setId(userId);
        user.setPosts(Arrays.asList(new Post("Post1", userId)));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        List<Post> posts = userService.getPostsByUserId(userId);
        assertEquals(1, posts.size());
        assertEquals("Post1", posts.get(0).getText());
    }

    @Test
    public void shouldReturnUserWhenUserCreated() {
        String userId = "userId";
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        User user = userService.validateUser(userId);
        assertNotNull(user);
    }

    @Test(expected = UserNotFoundException.class)
    public void shoulThrowUserNotFoundExceptionWhenUserNotCreated() {
        String userId = "userId";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        userService.validateUser(userId);
    }

}