package com.hsbc.codeChallenge.services;

import com.hsbc.codeChallenge.entities.Post;
import com.hsbc.codeChallenge.entities.User;
import com.hsbc.codeChallenge.exceptions.UserNotFoundException;
import com.hsbc.codeChallenge.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Post> getPostsByUserId(String id) {
        User user = validateUser(id);
        return user.getPosts();
    }

    public void followUser(String userId, String userToFollowId) {
        User user = validateUser(userId);
        User userToFollow = validateUser(userToFollowId);
        user.getUsersToFollow().add(userToFollow);
        userRepository.save(user);
    }

    User validateUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    void createUser(String id) {
        userRepository.save(new User(id));
    }

    public List<Post> getFollowsPostsByUserId(String id) {
        User user = validateUser(id);
        return user.getUsersToFollow().stream().map(User::getPosts)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Post::getCreationTimestamp).reversed())
                .collect(Collectors.toList());
    }
}
