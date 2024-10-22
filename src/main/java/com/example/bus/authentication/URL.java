package com.example.bus.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class URL {
    @Autowired
    private AuthenticationController authenticationController;
    @CrossOrigin
    @PostMapping("/auth/send_otp")
    public String send_otp(@RequestBody Map<String, Object> payload) {
        String mobileNumber = (String) payload.get("mobileNumber");
        int role = (int) payload.get("role");
        return authenticationController.send_otp(mobileNumber, role);
    }
    @CrossOrigin
    @PostMapping("/auth/jwt")
    public String verifyJwt(@RequestParam String jwt) {
        return authenticationController.verify_jwt(jwt);
    }
    @CrossOrigin
    @PostMapping("/auth/verify_otp")
    public boolean verifyOtp(@RequestBody Map<String, Object> payload) {
        String mobileNumber = (String) payload.get("mobileNumber");
        String otp = (String) payload.get("otp");
        return authenticationController.verify_otp(mobileNumber, otp);
    }
}
