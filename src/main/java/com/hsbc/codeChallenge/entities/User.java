package com.hsbc.codeChallenge.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    private String id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @JoinTable(name = "USER_FOLLWS", joinColumns = {
            @JoinColumn(name = "FOLLOWEB_BY", referencedColumnName = "ID", nullable =   false)}, inverseJoinColumns = {
            @JoinColumn(name = "FOLLOWED_USER", referencedColumnName = "ID", nullable = false)})
    @ManyToMany
    @JsonIgnore
    private Set<User> usersToFollow = new HashSet<>();

    private User() {
    }

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Set<User> getUsersToFollow() {
        return usersToFollow;
    }

    public void setUsersToFollow(Set<User> usersToFollow) {
        this.usersToFollow = usersToFollow;
    }
}
