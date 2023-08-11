package org.xinhua.example.spring.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xinhua.example.spring.mvc.model.po.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByUsername(String username);
}
