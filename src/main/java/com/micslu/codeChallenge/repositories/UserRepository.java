package com.micslu.codeChallenge.repositories;

import com.micslu.codeChallenge.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<User> findAll();

    Optional<User> findById(String id);
}
