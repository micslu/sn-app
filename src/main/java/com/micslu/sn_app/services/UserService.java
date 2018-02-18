package com.micslu.sn_app.services;

import com.micslu.sn_app.entities.Post;
import com.micslu.sn_app.entities.User;
import com.micslu.sn_app.exceptions.UserNotFoundException;
import com.micslu.sn_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Post> getPostsByUserId(String id) {
        User user = validateUser(id);
        return user.getPosts().stream().sorted(Comparator.comparing(Post::getCreationTimestamp).reversed()).collect(Collectors.toList());
    }

    public void addUserToFollow(String userId, String userToFollowId) {
        User user = validateUser(userId);
        User userToFollow = validateUser(userToFollowId);
        user.getUsersToFollow().add(userToFollow);
        userRepository.save(user);
    }

    public List<Post> getFollowedUsersPostsByUserId(String id) {
        User user = validateUser(id);
        return user.getUsersToFollow().stream().map(User::getPosts)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Post::getCreationTimestamp).reversed())
                .collect(Collectors.toList());
    }

    public void createPost(String userId, String postText) {
        User user = createUserIfNotCreated(userId);
        user.getPosts().add(new Post(postText, userId));

        userRepository.save(user);
    }

    @Transactional
    User createUserIfNotCreated(String userId) {
        User user = null;
        try {
            user = validateUser(userId);
        } catch (UserNotFoundException ex) {
            user = createUser(userId);
        }
        return user;
    }

    User validateUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    private User createUser(String id) {
        return userRepository.save(new User(id));
    }
}
