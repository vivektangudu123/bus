package com.example.bus.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
public class URL {
    @Autowired
    private AuthenticationController authenticationController;
    private final JwtUtils jwtUtils;

    public URL(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @CrossOrigin
    @PostMapping("/auth/send_otp")
    public String send_otp(@RequestBody Map<String, Object> payload) {
        String mobileNumber = (String) payload.get("mobileNumber");
        int role = (int) payload.get("role");
        return authenticationController.send_otp(mobileNumber, role);
    }
    @CrossOrigin
    @PostMapping("/auth/jwt")
    public String verifyJwt(@RequestBody Map<String, Object> payload) {
        String jwt=(String) payload.get("jwt");
        return authenticationController.verify_jwt(jwt);
    }
    @CrossOrigin
    @PostMapping("/auth/verify_otp")
    public String verifyOtp(@RequestBody Map<String, Object> payload) {
        String mobileNumber = (String) payload.get("mobileNumber");
        String otp = (String) payload.get("otp");
        if(authenticationController.verify_otp(mobileNumber, otp)){
            return jwtUtils.generateToken(mobileNumber);
        }
        return "-1";
    }
}
