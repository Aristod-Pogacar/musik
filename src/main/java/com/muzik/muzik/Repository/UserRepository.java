package com.muzik.muzik.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muzik.muzik.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
