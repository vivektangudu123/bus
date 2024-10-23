//package com.example.bus.Booking;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/bookings")
//public class BookingController {
//
//    @Autowired
//    private BookingRepository bookingRepository;
//
//    // Create a new booking
//    @PostMapping
//    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest bookingRequest) {
//        Booking newBooking = new Booking(
//                bookingRequest.getUser(),
//                bookingRequest.getSeatNumber(),
//                bookingRequest.getBus(),
//                bookingRequest.getBookingTime(),
//                bookingRequest.getTravelDate(),
//                bookingRequest.getSource(),
//                bookingRequest.getDestination()
//        );
//        newBooking.setStatus(BookingStatus.PENDING); // Set initial status
//        Booking savedBooking = bookingRepository.save(newBooking);
//        return ResponseEntity.ok(savedBooking);
//    }
//
//    // Fetch bookings based on User ID
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable Long userId) {
//        List<Booking> bookings = bookingRepository.findByUserId(userId);
//        return ResponseEntity.ok(bookings);
//    }
//
//    // Fetch bookings based on Bus ID
//    @GetMapping("/bus/{busId}")
//    public ResponseEntity<List<Booking>> getBookingsByBusId(@PathVariable Long busId) {
//        List<Booking> bookings = bookingRepository.findByBusId(busId);
//        return ResponseEntity.ok(bookings);
//    }
//
//    // Change the status of a booking
//    @PutMapping("/{bookingId}/status")
//    public ResponseEntity<Booking> changeBookingStatus(@PathVariable Long bookingId, @RequestBody BookingStatus newStatus) {
//        return bookingRepository.findById(bookingId).map(booking -> {
//            booking.setStatus(newStatus);
//            Booking updatedBooking = bookingRepository.save(booking);
//            return ResponseEntity.ok(updatedBooking);
//        }).orElse(ResponseEntity.notFound().build());
//    }
//}
package com.example.bus.Booking;

import com.example.bus.Bus.Bus;
import com.example.bus.Bus.BusRepository;
import com.example.bus.User.User;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.bus.Booking.BookingStatus.SUCCESSFUL;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BusRepository busRepository;
    // Add a new booking
    public boolean addBooking(User user, String seatNumber, Bus bus, LocalDateTime bookingTime, LocalDate travelDate, Pair<Integer, Integer> source, Pair<Integer, Integer> destination) {

        if(bus.bookSeat(seatNumber)){
            Booking booking = new Booking(user, seatNumber, bus, bookingTime, travelDate, source, destination);
            booking.setStatus(SUCCESSFUL);
            bus.bookSeat(seatNumber);
            busRepository.save(bus);
            bookingRepository.save(booking);
            return true;
        }
        return false;
    }

    // Change booking status
    public Booking changeBookingStatus(int bookingId, BookingStatus newStatus) throws Exception {
        Optional<Booking> bookingOptional = bookingRepository.findById((long) bookingId);

        if (!bookingOptional.isPresent()) {
            throw new Exception("Booking with id " + bookingId + " not found.");
        }

        Booking booking = bookingOptional.get();
        booking.setStatus(newStatus);
        return bookingRepository.save(booking);
    }

    // Get all bookings based on userId
    public List<Booking> getBookingsByUserId(int userId) {
        return bookingRepository.findByUser_userId(userId);
    }

    // Get all bookings based on busId
    public List<Booking> getBookingsByBusId(int busId) {
        return bookingRepository.findByBus_busId(busId);
    }

    // Get booking by bookingId
    public Optional<Booking> getBookingById(int bookingId) {
        return bookingRepository.findById((long) bookingId);
    }
}
