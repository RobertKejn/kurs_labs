package com.example.kurs.user;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT user from User user WHERE user.login=:login AND user.enabled=true")
    Optional<User> findByLogin(@Param("login") String login);
}
