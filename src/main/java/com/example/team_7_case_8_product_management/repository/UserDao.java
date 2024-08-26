package com.example.team_7_case_8_product_management.repository;

import com.example.team_7_case_8_product_management.model.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    @Query("select u from User u where u.login = ?1")
    Optional<User> findByLogin(String login);

    Collection<User> findAll();
    Optional<User> findByUserIdAndRole(Long id, String role);
}
