package com.example.bus.User;

import java.util.Scanner;
import org.springframework.stereotype.Service;

@Service
public class AdminController {

    public boolean login(){
        System.out.println("Enter your phone number (10 digits): ");
        Scanner scanner=new Scanner(System.in);
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter OTP: ");
        String OTP = scanner.nextLine();
        if(OTP.equals("111111")){
            return true;
        }
        return false;
    }

}
