package com.micslu.sn_app.services;

import com.micslu.sn_app.Application;
import com.micslu.sn_app.entities.Post;
import com.micslu.sn_app.entities.User;
import com.micslu.sn_app.exceptions.UserNotFoundException;
import com.micslu.sn_app.repositories.UserRepository;
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
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    public void shouldReturnPostsInReversedChronologicalOrderWhenUserExists() {
        String userId = "userId";
        User user = new User(userId);
        Post olderPost = new Post("Older Post", userId);
        Post newerPost = new Post("Newer Post", userId);
        newerPost.setCreationTimestamp(olderPost.getCreationTimestamp() + 2000);
        user.setPosts(Arrays.asList(olderPost, newerPost));
        userRepository.save(user);
        List<Post> posts = userService.getPostsByUserId(userId);
        assertEquals(2, posts.size());
        assertEquals("Newer Post", posts.get(0).getText());
        assertEquals("Older Post", posts.get(1).getText());
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

    @Test
    public void shouldAddUserToFollow() {
        String userId = "userId";
        String usertoFollowId = "userToFollowId";
        User user = new User(userId);
        User userToFollow = new User(usertoFollowId);
        userRepository.save(user);
        userRepository.save(userToFollow);
        userService.addUserToFollow(userId, usertoFollowId);

        assertEquals(1, userRepository.findOne(userId).getUsersToFollow().size());
        assertEquals("userToFollowId", userRepository.findOne(userId).getUsersToFollow().iterator().next().getId());
    }

    @Test
    public void shouldReturnEmptyListWhenNoFollowedUsers() {
        String userId = "userId";
        User user = new User(userId);
        userRepository.save(user);

        List<Post> followedUsersPostsd = userService.getFollowedUsersPostsByUserId(userId);
        assertTrue(followedUsersPostsd.isEmpty());
    }

    @Test
    public void shouldReturnFollowedUsersPosts() {
        String userId = "userId";
        String usertoFollowId = "userToFollowId";
        User user = new User(userId);
        User userToFollow = new User(usertoFollowId);
        userToFollow.setPosts(Arrays.asList(new Post("Post 1", usertoFollowId)));
        user.getUsersToFollow().add(userToFollow);
        userRepository.save(userToFollow);
        userRepository.save(user);

        List<Post> followedUsersPosts = userService.getFollowedUsersPostsByUserId(userId);

        assertEquals(1, followedUsersPosts.size());
        assertEquals("Post 1", followedUsersPosts.get(0).getText());
    }

    @Test
    public void shouldCreatePostForExistingUser() throws InterruptedException {
        String userId = "userId";
        userService.createUserIfNotCreated(userId);

        userService.createPost(userId, "Post");
        List<Post> posts = userService.getPostsByUserId(userId);
        assertEquals(1, posts.size());
        assertEquals("Post", posts.get(0).getText());
    }

    @Test
    public void shouldCreateUserAndPost() {
        String userId = "userId";
        UserNotFoundException exception = null;
        //Make sure userId doesn't exist
        try {
            userService.validateUser(userId);
        } catch (UserNotFoundException ex) {
            exception = ex;
        }
        assertNotNull(exception);
        assertEquals("Could not find user '" + userId + "'.", exception.getMessage());
        userService.createPost(userId, "Post");
        List<Post> posts = userService.getPostsByUserId(userId);
        assertEquals(1, posts.size());
        assertEquals("Post", posts.get(0).getText());
    }
}