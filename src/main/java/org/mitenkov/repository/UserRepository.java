package org.mitenkov.repository;

import org.mitenkov.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserById(int id);
}
