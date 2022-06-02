package com.example.hotel.controller;

import com.example.hotel.Entitiy.Hotel;
import com.example.hotel.Entitiy.Room;
import com.example.hotel.model.RoomDto;
import com.example.hotel.repository.HotelRepo;
import com.example.hotel.repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    RoomRepo repoRoom;

    @Autowired
    HotelRepo repoHotel;

    @GetMapping
    public List<Room> hotelList() {
        return repoRoom.findAll();
    }

    @GetMapping("/{id}")
    public Room getOne(@PathVariable Integer id) {
        return repoRoom.findById(id).get();
    }

    @GetMapping("/roomsByHotelId/{id}")
    public List<Room> findAllByHotel_Id(@PathVariable Integer id) {
        return repoRoom.findAllByHotel_Id(id);
    }

    @PostMapping
    public String add(@RequestBody RoomDto roomDto) {
        int floor = roomDto.getFloor();
        int number = roomDto.getNumber();
        int size = roomDto.getSize();
        Optional<Hotel> optionalHotel = repoHotel.findById(roomDto.getHotelId());
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();

            Optional<Room> optionalRoom = repoRoom.findByFloorAndNumberAndHotel_Id(floor, number, hotel.getId());
            if (optionalRoom.isEmpty()) {
                Room room = new Room(number, floor, size, hotel);
                Room saved = repoRoom.save(room);
                return "Room saqlandi id: " + saved.getId();
            } else return "This room already exist in the hotel";
        } else return "Bunday id lik hotel topilmadi";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        repoRoom.deleteById(id);
        return "deleted";
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id, @RequestBody RoomDto roomDto) {
        int floor = roomDto.getFloor();
        int number = roomDto.getNumber();
        int size = roomDto.getSize();
        Optional<Hotel> optionalHotel = repoHotel.findById(roomDto.getHotelId());
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            Optional<Room> optionalRoom = repoRoom.findById(id);
            if (optionalRoom.isPresent()) {
                Room room = optionalRoom.get();
                room.setFloor(floor);
                room.setNumber(number);
                room.setSize(size);
                room.setHotel(hotel);
                Room savedRoom = repoRoom.save(room);
                return "Room saved Id's: " + savedRoom.getId();
            } else return "Bunday Id lik room topilmadi";

        } else return "Bunday id lik hotel topilmadi";
    }
}
