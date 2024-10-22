package com.example.bus.Bus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

@Repository
@Service
public interface BusRepository extends JpaRepository<Bus, Integer> {


}
