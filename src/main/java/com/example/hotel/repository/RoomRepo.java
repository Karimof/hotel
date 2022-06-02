package com.example.hotel.repository;

import com.example.hotel.Entitiy.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepo extends JpaRepository<Room, Integer> {
    Optional<Room> findByFloorAndNumberAndHotel_Id(Integer floor, Integer number, Integer hotelId);

    List<Room> findAllByHotel_Id(Integer hotelId);
}
