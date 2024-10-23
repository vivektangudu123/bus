package com.example.bus.Booking;

import com.example.bus.Bus.Bus;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class BookingDTO {

    private int bookingId;
    private String seatNumber;
    private LocalDateTime bookingTime;
    private LocalDate travelDate;
    private BookingStatus status;
    private String  busName;
    // No-argument constructor
    public BookingDTO() {}

    // Parameterized constructor
    public BookingDTO(int bookingId, String seatNumber, LocalDateTime bookingTime, LocalDate travelDate, BookingStatus status,String Busname) {
        this.bookingId = bookingId;
        this.seatNumber = seatNumber;
        this.bookingTime = bookingTime;
        this.travelDate = travelDate;
        this.status = status;

    }

    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
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

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}
