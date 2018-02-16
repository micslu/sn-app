package com.hsbc.codeChallenge.services;

import com.hsbc.codeChallenge.entities.Post;
import com.hsbc.codeChallenge.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private UserService userService;
    private PostRepository postRepository;

    @Autowired
    public PostService(UserService userService, PostRepository postRepository) {
        this.userService = userService;
        this.postRepository = postRepository;
    }

    public void createPost(String userId, String postText) {
        userService.createUserIfNotCreated(userId);
        postRepository.save(new Post(postText, userId));
    }
}
