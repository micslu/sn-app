package com.hsbc.codeChallenge.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "POSTS")
public class Post {

    @Id
    String id;
    @Size(max = 140)
    String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
