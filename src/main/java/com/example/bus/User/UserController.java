package com.example.bus.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.bus.authentication.AuthenticationController;
import java.util.Scanner;
@Service
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final AuthenticationController authenticationController;
    @Autowired
    private final UserRepository userRepository;
    private Scanner scanner;
    // Constructor
    public UserController(UserService userService, AuthenticationController authenticationController, UserRepository userRepository) {
        this.userService = userService;
        this.authenticationController = authenticationController;
        this.userRepository = userRepository;
        scanner = new Scanner(System.in);
    }

    public boolean do_auth(String phoneNumber){
        String sendOtp = authenticationController.send_otp(phoneNumber, 1);
        System.out.print("Enter OTP: ");
        String OTP = scanner.nextLine();
        return authenticationController.verify_otp(phoneNumber, OTP);

    }
    // Method to register a new user via terminal input
    public int registerUser() {

        System.out.println("Enter your phone number (10 digits): ");
        String phoneNumber = scanner.nextLine();
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            System.out.println("Number already exists!! Try again");
            return -1;
        }
        if(!do_auth(phoneNumber)){
            System.out.println("Authentication Failed!!");
            return -1;
        }else{
            System.out.println("Authentication Success!!");
        }

        System.out.println("Enter your name: ");
        String name = scanner.nextLine();

        System.out.println("Enter your email: ");
        String email = scanner.nextLine();
        if(userRepository.existsByEmail(email)){
            System.out.println("Email already exists!! Try again");
            return -1;
        }
        System.out.println("Enter your gender (M/F): ");
        String gender = scanner.nextLine();

        // Create a new User object with the input details
        User newUser = new User(name, email, gender, phoneNumber);

        // Call the service class to save the user
        userService.saveUser(newUser);

        System.out.println("User registered successfully!");
        return userRepository.findByPhoneNumber(phoneNumber).getUserId();
    }
    public int loginUser(){
        System.out.println("Enter your phone number (10 digits): ");
        String phoneNumber = scanner.nextLine();
        if(!userRepository.existsByPhoneNumber(phoneNumber)){
            System.out.println("Number does not exists!! Try again");
            return -1;
        }
        if(!do_auth(phoneNumber)){
            System.out.println("Authentication Failed!!");
            return -1;
        }else {
            System.out.println("Authentication Success!!");
            return userRepository.findByPhoneNumber(phoneNumber).getUserId();
        }

    }
}
