package com.hsbc.codeChallenge.controllers;

import com.hsbc.codeChallenge.entities.Post;
import com.hsbc.codeChallenge.services.PostService;
import com.hsbc.codeChallenge.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @RequestMapping(value = "/users/{id}/posts", method = RequestMethod.GET)
    public List<Post> getUserPosts(@PathVariable String id) {
        return userService.getPostsByUserId(id);
    }

    @RequestMapping(value = "/users/{id}/posts",
            params = {"text"},
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void createUserPost(@PathVariable String id, @RequestParam String text) {
        postService.createPost(id, text);
    }

    @RequestMapping(value = "/users/{id}/follow", method = RequestMethod.GET)
    public List<Post> getUserFollowsPosts(@PathVariable String id) {
        return userService.getFollowsPostsByUserId(id);
    }

    @RequestMapping(value = "/users/{id}/follow",
            params = {"userToFollowId"},
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void followUser(@PathVariable String id, @RequestParam String userToFollowId) {
        userService.followUser(id, userToFollowId);
    }
}
