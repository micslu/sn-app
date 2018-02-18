package com.micslu.sn_app.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Instant;

@Entity
@Table(name = "POSTS")
public class Post {

    @Id
    @GeneratedValue
    private Long id;
    @Size(max = 140)
    private String text;
    @Column(name = "CREATED_DATE")
    private Long creationTimestamp;
    @Column(name = "USER_ID")
    private String userId;

    private Post() {
    }

    public Post(String text, String userId) {
        super();
        this.text = text;
        this.creationTimestamp = Instant.now().toEpochMilli();
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
