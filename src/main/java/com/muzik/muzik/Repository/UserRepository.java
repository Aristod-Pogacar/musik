package com.muzik.muzik.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muzik.muzik.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
