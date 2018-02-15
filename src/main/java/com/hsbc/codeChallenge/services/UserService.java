package com.hsbc.codeChallenge.services;

import com.hsbc.codeChallenge.entities.Post;
import com.hsbc.codeChallenge.entities.User;
import com.hsbc.codeChallenge.exceptions.UserNotFoundException;
import com.hsbc.codeChallenge.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<Post> getPostsByUserId(String id) {
        User user = validateUser(id);
        return user.getPosts();
    }

    User validateUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    void createUser() {
        userRepository.save(new User());
    }
}
