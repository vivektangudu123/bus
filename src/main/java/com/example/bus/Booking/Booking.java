package com.example.bus.Booking;

import com.example.bus.Bus.Bus;
import com.example.bus.User.User;
import jakarta.persistence.*;
import javafx.util.Pair;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false) // Foreign key to the User entity
    private User user; // Reference to the User entity

    private String seatNumber; // The seat number booked

    @ManyToOne
    @JoinColumn(name = "busId", nullable = false) // Foreign key to the Bus entity
    private Bus bus;

    private LocalDateTime bookingTime; // Time when the booking was made

    @Column(nullable = false)
    private LocalDate travelDate; // Date of the journey

    private Pair<Integer, Integer> source; // Source location
    private Pair<Integer, Integer> destination; // Destination location

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status; // Status of the booking

    public Booking(User user, String seatNumber, Bus bus, LocalDateTime bookingTime, LocalDate travelDate,Pair<Integer, Integer> source,Pair<Integer, Integer> destination) {
        this.user = user;
        this.seatNumber = seatNumber;
        this.bus = bus;
        this.bookingTime = bookingTime;
        this.travelDate = travelDate;
        this.source = source;
        this.destination = destination;
        this.status = BookingStatus.PENDING; // Set default status to PENDING
    }
    public Booking() {
    }
    // Getters and Setters
    public int getId() {
        return bookingId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }

    public Pair<Integer, Integer> getSource() {
        return source;
    }

    public void setSource(Pair<Integer, Integer> source) {
        this.source = source;
    }

    public Pair<Integer, Integer> getDestination() {
        return destination;
    }

    public void setDestination(Pair<Integer, Integer> destination) {
        this.destination = destination;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}

