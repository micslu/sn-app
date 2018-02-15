package com.hsbc.codeChallenge.services;

import com.hsbc.codeChallenge.entities.Post;
import com.hsbc.codeChallenge.exceptions.UserNotFoundException;
import com.hsbc.codeChallenge.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private UserService userService;
    @Autowired
    private PostRepository postRepository;

    public void createPost(String userId, String postText) {
        try {
            userService.validateUser(userId);
        } catch (UserNotFoundException ex) {
            userService.createUser();
        }
        postRepository.save(new Post(postText, userId));
    }
}
