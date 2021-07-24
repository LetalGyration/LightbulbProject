package com.project.room.controllers;

import com.project.room.models.Room;
import com.project.room.services.RoomService;
import com.project.room.utils.RoomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api")
public class RoomController {
    RoomService service;
    @Autowired
    public RoomController(RoomService service) {
        this.service = service;
    }

    @GetMapping(value = "/rooms")
    public List<Room> getAllRooms() {
        System.out.println("Working");
        return service.getAllRooms();
    }

    @GetMapping(value = "/rooms/{id}")
    public Room getRoomById(@PathVariable("id") @Min(1) int id) {
        Room room = service.findById(id).orElseThrow(() -> new RoomNotFoundException("Room with " + id + " Not Found!"));
        return room;
    }

    @PostMapping(value = "/rooms")
    public Room addRoom(@Valid @RequestBody Room room) {
        return service.addRoom(room);
    }

    @PutMapping(value = "/rooms/{id}")
    public void updateStatus(@PathVariable("id") @Min(1) int id, @RequestParam byte status) {
        service.updateStatus(id, status);
    }
}
