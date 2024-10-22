package com.example.bus.Bus;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;

@Entity
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int busId;

    @Column(nullable = false)
    private String busName;

    @Column(nullable = false)
    private int totalSeats;

    @Column(nullable = false)
    private int currentOccupancy;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private Map<String, Boolean> seatPlan; // Seat plan with availability (true = available, false = booked)

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "bus_route", joinColumns = @JoinColumn(name = "bus_id"))
    @Column(name = "route")
    private List<Pair<Integer, Integer>> route; // List of (source, destination) pairs

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "available_days", joinColumns = @JoinColumn(name = "bus_id"))
    @Column(name = "day")
    private List<String> availableDays; // List of available days (e.g., ["Monday", "Wednesday"])

    @Column(nullable = false)
    private Pair<Integer, Integer> currentLocation; // Current location of the bus

    @Column(nullable = false)
    private boolean isLive; // Status of the bus

    public Bus() {
        this.seatPlan = generateDefaultSeatPlan();
        this.route = new ArrayList<>();
        this.availableDays = generateDefaultAvailableDays();
        this.currentLocation = route.isEmpty() ? null : route.get(0); // Set to first location in route
        this.isLive = false; // Initialize as not live
    }

    public Bus(String busName, int totalSeats, int currentOccupancy, List<Pair<Integer, Integer>> route) {
        this.busName = busName;
        this.totalSeats = totalSeats;
        this.currentOccupancy = currentOccupancy;
        this.route = route;
        this.availableDays = generateDefaultAvailableDays();
        this.currentLocation = route.isEmpty() ? null : route.get(0); // Set to first location in route
        this.isLive = false; // Initialize as not live
        this.seatPlan = generateDefaultSeatPlan();
    }

    // Generate seat plan with default seats (1A, 1B, 1C, 1D, etc.) and set them all as available initially
    private Map<String, Boolean> generateDefaultSeatPlan() {
        Map<String, Boolean> seatPlan = new HashMap<>();
        String[] rows = {"1", "2", "3", "4"};
        String[] columns = {"A", "B", "C", "D"};

        for (String row : rows) {
            for (String column : columns) {
                seatPlan.put(row + column, true); // All seats are available initially
            }
        }
        return seatPlan;
    }

    // Generate the list of all days of the week
    private List<String> generateDefaultAvailableDays() {
        List<String> days = new ArrayList<>();
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
        days.add("Sunday");
        return days;
    }

    // Getters and Setters

    public int getId() {
        return busId;
    }


    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getCurrentOccupancy() {
        return currentOccupancy;
    }

    public void setCurrentOccupancy(int currentOccupancy) {
        this.currentOccupancy = currentOccupancy;
    }

    public Map<String, Boolean> getSeatPlan() {
        return seatPlan;
    }

    public void setSeatPlan(Map<String, Boolean> seatPlan) {
        this.seatPlan = seatPlan;
    }

    public List<Pair<Integer, Integer>> getRoute() {
        return route;
    }

    public void setRoute(List<Pair<Integer, Integer>> route) {
        this.route = route;
        this.currentLocation = route.isEmpty() ? null : route.get(0); // Update current location
    }

    public List<String> getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(List<String> availableDays) {
        this.availableDays = availableDays;
    }

    public Pair<Integer, Integer> getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Pair<Integer, Integer> currentLocation) {
        this.currentLocation = currentLocation;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean isLive) {
        this.isLive = isLive;
    }

    // Method to book a seat
    public boolean bookSeat(String seatNumber) {
        if (seatPlan.get(seatNumber) != null && seatPlan.get(seatNumber)) {
            seatPlan.put(seatNumber, false); // Mark the seat as booked
            currentOccupancy++;
            return true;
        }
        return false; // Seat not available or does not exist
    }

    // Method to cancel a seat
    public boolean cancelSeatBooking(String seatNumber) {
        if (seatPlan.get(seatNumber) != null && !seatPlan.get(seatNumber)) {
            seatPlan.put(seatNumber, true); // Mark the seat as available
            currentOccupancy--;
            return true;
        }
        return false; // Seat not booked or does not exist
    }

    // Calculate occupancy percentage
    public double getOccupancyPercentage() {
        return (double) currentOccupancy / totalSeats * 100;
    }

    // Color coding for seat availability based on occupancy percentage
    public String getSeatAvailabilityColor() {
        double occupancy = getOccupancyPercentage();
        if (occupancy <= 60) {
            return "Green";
        } else if (occupancy > 60 && occupancy <= 90) {
            return "Yellow";
        } else {
            return "Red";
        }
    }
    public void printSeatPlan() {
        String resetColor = "\u001B[0m";
        String bookedColor = "\u001B[31m";
        String availableColor = "\u001B[37m";
        String[] rows = {"1", "2", "3", "4"};
        String[] columns = {"A", "B", "C", "D"};

        for (String row : rows) {
            for (String column : columns) {
                String seatNumber = row + column;
                if (seatPlan.get(seatNumber)) {
                    System.out.print(availableColor + seatNumber + " " + resetColor);
                } else {
                    System.out.print(bookedColor + seatNumber + " " + resetColor);
                }
            }
            System.out.println();
        }
    }
}
