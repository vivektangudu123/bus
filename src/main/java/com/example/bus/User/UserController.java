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
    public ResponseEntity<List<BookingDTO>> boookings(@RequestBody Map<String, Object> payload) {
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
                        booking.getStatus(),
                        booking.getBus().getBusName()
                ))
                .collect(Collectors.toList());


        return ResponseEntity.ok(bookingDTOs);
    }

    @CrossOrigin
    @PostMapping("/users/cancel-booking")
    public boolean cancelBooking(@RequestBody Map<String, Object> payload) throws Exception {
        String jwt=(String) payload.get("jwt");
        String booking_str=(String) payload.get("BookingID");
        System.out.println(booking_str);
        int bookingId;
        try {
            bookingId = Integer.parseInt(booking_str);
        } catch (NumberFormatException e) {
            // Handle the case where the string cannot be parsed as an integer
            throw new Exception("Invalid BookingID format", e);
        }
        userService.cancel_seat(bookingId);
        return true;
    }
    @CrossOrigin
    @PostMapping("/users/search")
    public boolean search(@RequestBody Map<String, Object> payload) throws Exception {
        try {
            // Extract the parameters and convert them to integers
            int s1 = Integer.parseInt((String) payload.get("s1"));
            int s2 = Integer.parseInt((String) payload.get("s2"));
            int d1 = Integer.parseInt((String) payload.get("d1"));
            int d2 = Integer.parseInt((String) payload.get("d2"));

            // Proceed with your business logic using the integer values
            // For example:
            System.out.println("Source: " + s1 + ", " + s2);
            System.out.println("Destination: " + d1 + ", " + d2);

            // Example of using the user service to search for buses
             userService.get_view_buses(s1,s2,d1,d2);
            return true; // Return a response based on your logic
        } catch (NumberFormatException e) {
            // Handle the case where the string cannot be parsed as an integer
            throw new Exception("Invalid input format", e);
        }
    }

