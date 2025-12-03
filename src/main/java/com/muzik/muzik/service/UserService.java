package com.muzik.muzik.service;

import org.springframework.stereotype.Service;

import com.muzik.muzik.Entity.User;
import com.muzik.muzik.Repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setNom(userDetails.getNom());
                    existingUser.setPrenom(userDetails.getPrenom());
                    existingUser.setEmail(userDetails.getEmail());
                    existingUser.setPassword(userDetails.getPassword()); // Consider hashing password if updated
                    // Update other fields as necessary
                    return userRepository.save(existingUser);
                })
                .orElse(null); // Or throw a custom exception if user not found
    }

}
