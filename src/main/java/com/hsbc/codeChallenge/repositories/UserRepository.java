package com.hsbc.codeChallenge.repositories;

import com.hsbc.codeChallenge.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
