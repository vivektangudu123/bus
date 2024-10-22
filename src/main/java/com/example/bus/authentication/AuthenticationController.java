package com.example.bus.authentication;


import com.example.bus.User.User;
import com.example.bus.User.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;


@Service
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;

    public AuthenticationController(AuthenticationService authenticationService, JwtUtils jwtUtils) {
        this.authenticationService = authenticationService;
        this.jwtUtils = jwtUtils;
    }

    public String send_otp(String mobile_number,int role) {
            if(userRepository.existsByPhoneNumber(mobile_number))
            {
//                String au=authenticationService.send_otp(send_otp_rec.mobile_number);
//                if(au.equals("pending"))return "pending";
//                else{
//                    return "Try again, Error occured";
//                }
                return "pending";
            }
            else{
                return "User Not Found";
            }
    }

    public String verify_jwt(String JWT) {
        JWT = JWT.substring(1, JWT.length() - 1);
        System.out.println(JWT);
        String username=get_username_using_jwt(JWT);
        System.out.println(username);
        return username;
    }


    public boolean verify_otp(String mobile_number,String otp) {
        String status = "approved"; // Assuming status is approved for simplicity

        if (status.equals("approved")) {
            if (userRepository.existsByPhoneNumber(mobile_number)) {
                User user = userRepository.findByPhoneNumber(mobile_number);
//                String jwtToken = jwtUtils.generateToken(String.valueOf(user.getUserId()));

                Map<String, Object> response = new HashMap<>();
                String statuss=status;

                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    public String get_username_using_jwt(String token) {
        try {
            return jwtUtils.extractUsername(token);
        } catch (ExpiredJwtException ex) {
            return "1";
        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {

            return"2";
        }
    }

}