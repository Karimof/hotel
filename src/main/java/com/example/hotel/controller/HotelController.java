package com.example.hotel.controller;

import com.example.hotel.Entitiy.Hotel;
import com.example.hotel.repository.HotelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    HotelRepo repoHotel;

    @GetMapping
    public List<Hotel> hotelList() {
        return repoHotel.findAll();
    }

    @GetMapping("/{id}")
    public Hotel getOne(@PathVariable Integer id) {
        return repoHotel.findById(id).get();
    }

    @PostMapping
    public String add(@RequestBody Hotel newHotel) {
        if (repoHotel.findByName(newHotel.getName()).isPresent()) {
            return "This Hotel name already exist!";
        }
        Hotel hotel = new Hotel(newHotel.getName());
        Hotel savedHotel = repoHotel.save(hotel);
        return "Saqlandi. Hotel id: " + savedHotel.getId();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        repoHotel.deleteById(id);
        return "deleted";
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id, @RequestBody Hotel newHotel) {
        Optional<Hotel> optionalHotel = repoHotel.findById(id);
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            hotel.setName(newHotel.getName());
            Hotel savedH = repoHotel.save(hotel);
            return "O'zgartirildi id: " + savedH.getId();
        } else {
            return "Hotel not found!!";
        }
    }

}
