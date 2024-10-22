package com.example.bus.Bus;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javafx.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    public Bus addBus(Bus bus) {
        return busRepository.save(bus);
    }

    // Update an existing bus
    public Bus updateBus(int id,Bus updatedBus) {
        Optional<Bus> optionalBus = busRepository.findById(id);
        if (optionalBus.isPresent()) {
            Bus bus = optionalBus.get();
            bus.setBusName(updatedBus.getBusName());
            bus.setTotalSeats(updatedBus.getTotalSeats());
            bus.setCurrentOccupancy(updatedBus.getCurrentOccupancy());
            bus.setRoute(updatedBus.getRoute());
            bus.setAvailableDays(updatedBus.getAvailableDays());
            bus.setLive(updatedBus.isLive());
            return busRepository.save(bus);
        }
        return null;
    }

    // Delete a bus
    public void deleteBus(int id) {
        busRepository.deleteById(id);
    }


    public List<Bus> getAvailableBuses(Pair<Integer, Integer> source, Pair<Integer, Integer> destination) {
        List<Bus> buses = busRepository.findAll(); // Fetch all buses
        List<Bus> matchingBuses = new ArrayList<>();

        for (Bus bus : buses) {
            List<Pair<Integer, Integer>> route = bus.getRoute();
            int sourceIndex = route.indexOf(source);
            int destinationIndex = route.indexOf(destination);

            // Ensure that source exists, destination exists, and destination comes after source
            if (sourceIndex != -1 && destinationIndex != -1 && destinationIndex > sourceIndex) {
                matchingBuses.add(bus);
            }
        }
        return matchingBuses;
    }

    // Book a seat on a specific bus
    public String bookSeat(int id,String seatNumber) {
        Optional<Bus> optionalBus = busRepository.findById(id);
        if (optionalBus.isPresent()) {
            Bus bus = optionalBus.get();
            boolean success = bus.bookSeat(seatNumber);
            if (success) {
                busRepository.save(bus); // Update the bus after booking
                return "Seat " + seatNumber + " booked successfully!";
            } else {
                return "Seat " + seatNumber + " is already booked or does not exist!";
            }
        }
        return "Bus not found";
    }

    // Cancel a seat booking
    public String cancelSeat(int id,String seatNumber) {
        Optional<Bus> optionalBus = busRepository.findById(id);
        if (optionalBus.isPresent()) {
            Bus bus = optionalBus.get();
            boolean success = bus.cancelSeatBooking(seatNumber);
            if (success) {
                busRepository.save(bus);
                return "Seat " + seatNumber + " canceled successfully!";
            } else {
                return "Seat " + seatNumber + " was not booked!";
            }
        }
        return "Bus not found";
    }

    public Bus findBybusId(int a) {
        return busRepository.findBybusId(a);
    }
    public void addBus() {
        Scanner scanner = new Scanner(System.in);

        // Get bus name
        System.out.println("Enter Bus Name:");
        String busName = scanner.nextLine();

        // Get total seats
        System.out.println("Enter Total Seats: 16 (default value)");
//        int totalSeats = scanner.nextInt();

        // Get current occupancy
        System.out.println("Enter Current Occupancy (number of booked seats): 0");
//        int currentOccupancy = scanner.nextInt();

        // Get route (list of pairs of source and destination)
        System.out.println("Enter number of stops in the route:");
        int numStops = scanner.nextInt();
        List<Pair<Integer, Integer>> route = new ArrayList<>();

        for (int i = 0; i < numStops; i++) {
            System.out.println("Enter longitude for stop " + (i + 1) + ":");
            int c1 = scanner.nextInt();
            System.out.println("Enter latitude for stop " + (i + 1) + ":");
            int c2 = scanner.nextInt();
            route.add(new Pair<>(c1, c2));
        }

        Bus bus = new Bus(busName, 16, 0, route);

        busRepository.save(bus);

        System.out.println("Bus added successfully!");
    }
}
