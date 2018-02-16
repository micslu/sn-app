package com.hsbc.codeChallenge.entities;

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
    @JoinColumn(name = "USER_ID")
    List<Post> posts = new ArrayList<>();

    private User(){}
    public User(String id){
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
}
