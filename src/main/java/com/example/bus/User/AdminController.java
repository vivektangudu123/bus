package com.example.bus.User;

import java.util.Scanner;


public class AdminController {
    private final Scanner scanner;

    public AdminController(Scanner scanner) {
        this.scanner = scanner;
    }

    public boolean login(){
        System.out.println("Enter your phone number (10 digits): ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter OTP: ");
        String OTP = scanner.nextLine();
        if(OTP.equals("11111")){
            return true;
        }
        return false;
    }
}
