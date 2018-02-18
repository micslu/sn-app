package com.micslu.codeChallenge.controllers;

import com.micslu.codeChallenge.entities.Post;
import com.micslu.codeChallenge.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users/{id}/posts", method = RequestMethod.GET)
    public List<Post> getUserPosts(@PathVariable String id) {
        return userService.getPostsByUserId(id);
    }

    @RequestMapping(value = "/users/{id}/posts",
            params = {"text"},
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void createUserPost(@PathVariable String id, @RequestParam String text) {
        userService.createPost(id, text);
    }

    @RequestMapping(value = "/users/{id}/follow", method = RequestMethod.GET)
    public List<Post> getUserFollowsPosts(@PathVariable String id) {
        return userService.getFollowedUsersPostsByUserId(id);
    }

    @RequestMapping(value = "/users/{id}/follow",
            params = {"userToFollowId"},
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void followUser(@PathVariable String id, @RequestParam String userToFollowId) {
        userService.addUserToFollow(id, userToFollowId);
    }
}
