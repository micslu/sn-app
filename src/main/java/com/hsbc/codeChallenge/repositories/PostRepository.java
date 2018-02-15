package com.hsbc.codeChallenge.repositories;

import com.hsbc.codeChallenge.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
}
