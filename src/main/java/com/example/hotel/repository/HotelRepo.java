package com.example.hotel.repository;

import com.example.hotel.Entitiy.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelRepo extends JpaRepository<Hotel, Integer> {
    Optional<Hotel> findByName(String name);
}
