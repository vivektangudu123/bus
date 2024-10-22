package com.example.bus.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Method to find a user by email
    User findByEmail(String email);

    // Method to find a user by phone number
    User findByPhoneNumber(String phoneNumber);

    // Method to check if a user exists by phone number
    boolean existsByPhoneNumber(String phoneNumber);

    // Method to check if a user exists by email
    boolean existsByEmail(String email);
}
