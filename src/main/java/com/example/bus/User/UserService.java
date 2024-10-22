package com.example.bus.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Save a user to the database
    public void saveUser(User user) {
        // Simulate interaction with the terminal for saving
        System.out.println("Saving user to the database...");
        userRepository.save(user);
        System.out.println("User saved: " + user.getName());
    }

    // Retrieve all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
