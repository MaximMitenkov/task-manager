package org.mitenkov.repository;

import org.mitenkov.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserById(int id);

    User findByUsername(String username);
}
