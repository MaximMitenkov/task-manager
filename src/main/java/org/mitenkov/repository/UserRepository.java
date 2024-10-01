package org.mitenkov.repository;

import org.mitenkov.entity.User;
import org.mitenkov.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserById(int id);

    User findByUsername(String username);

    List<User> findByRole(UserRole role);
}
