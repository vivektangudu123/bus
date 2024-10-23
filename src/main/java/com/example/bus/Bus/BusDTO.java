package com.example.bus.Bus;
import javafx.util.Pair;
import java.util.List;
import java.util.Map;

public class BusDTO {

    private int busId;
    private String busName;
    private int totalSeats;
    private int currentOccupancy;
    private Map<String, Boolean> seatPlan;
    private List<Pair<Integer, Integer>> route;
    private List<String> availableDays;
    private Pair<Integer, Integer> currentLocation;
    private boolean isLive;
    private String currentOccupancycolor;
    // Constructor
    public BusDTO(int busId, String busName, int totalSeats, int currentOccupancy,
                  String currentOccupancycolor,Map<String, Boolean> seatPlan,
                  Pair<Integer, Integer> currentLocation,
                  boolean isLive) {
        this.busId = busId;
        this.busName = busName;
        this.totalSeats = totalSeats;
        this.currentOccupancy = currentOccupancy;
        this.currentOccupancycolor=currentOccupancycolor;
        this.seatPlan = seatPlan;
//        this.route = route;
//        this.availableDays = availableDays;
        this.currentLocation = currentLocation;
        this.isLive = isLive;
    }

    // Getters and Setters
    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
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

    public String getColor() {
        return currentOccupancycolor;
    }

    public void setColor(String color) {
        this.currentOccupancycolor = color;
    }
}
