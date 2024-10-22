package com.example.bus.Booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Repository


public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser_userId(int userId); // Access user_id through the user object
    List<Booking> findByBus_busId(int busId);       // This method can remain as is if your Bus entity has an 'id' field
}
