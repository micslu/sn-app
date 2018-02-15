package com.hsbc.codeChallenge.controllers;

import com.hsbc.codeChallenge.entities.User;
import com.hsbc.codeChallenge.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/users")
    public List<User> getUsers(){
        return userService.getAll();
    }
}
