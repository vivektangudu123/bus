package com.example.bus.User;

import com.example.bus.Booking.Booking;
import com.example.bus.Booking.BookingDTO;
import com.example.bus.Booking.BookingService;
import com.example.bus.authentication.AuthenticationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final AuthenticationController authenticationController;
    @Autowired
    private final BookingService bookingService;
    public UserController(UserService userService, UserRepository userRepository, AuthenticationController authenticationController, BookingService bookingService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationController = authenticationController;
        this.bookingService = bookingService;
    }

    @CrossOrigin
    @PostMapping("/users/create")
    public String register(@RequestBody Map<String, Object> payload) {
        String name = (String) payload.get("Name");
        String gender = (String) payload.get("gender");
        String email = (String) payload.get("email");
        String phoneNumber = (String) payload.get("phoneNumber");

        // Check if the user with the same phone number or email exists
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            return "Phone number already exists!";
        }

        if (userRepository.existsByEmail(email)) {
            return "Email already exists!";
        }

        // Create a new user and save it using the service
        User newUser = new User(name, email, gender, phoneNumber);
        userService.saveUser(newUser);

        return "Success";
    }

    @CrossOrigin
    @PostMapping("/users/bookings")
    public List <BookingDTO> boookings(@RequestBody Map<String, Object> payload) {
        String jwt=(String) payload.get("jwt");
        String mobileNUmber=authenticationController.verify_jwt(jwt);
        User user=userRepository.findByPhoneNumber(mobileNUmber);
        System.out.println(user.getUserId());
        List <Booking> ans=bookingService.getBookingsByUserId(user.getUserId());
        List<Booking> bookings = bookingService.getBookingsByUserId(user.getUserId());

        // Convert Booking entities to BookingDTOs
        List<BookingDTO> bookingDTOs = bookings.stream()
                .map(booking -> new BookingDTO(
                        booking.getId(),
                        booking.getSeatNumber(),
                        booking.getBookingTime(),
                        booking.getTravelDate(),
                        booking.getStatus()
                ))
                .collect(Collectors.toList());

        return bookingDTOs;

    }

    @CrossOrigin
    @PostMapping("/users/cancel-booking")
    public boolean cancelBooking(@RequestBody Map<String, Object> payload) throws Exception {
        String jwt=(String) payload.get("jwt");
        int bookingId=(int) payload.get("BookingID");
//        String mobileNUmber=authenticationCont1roller.verify_jwt(jwt);
//        User user=userRepository.findByPhoneNumber(mobileNUmber);
        userService.cancel_seat(bookingId);
        return true;
    }
}

