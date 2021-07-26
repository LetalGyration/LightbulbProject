package com.project.room.controllers;

import com.project.room.models.Room;
import com.project.room.services.LocationValidatorService;
import com.project.room.services.RoomService;
import com.project.room.utils.RoomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api")
public class RoomController {

    LocationValidatorService locationService;
    RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService, LocationValidatorService locationService) {
        this.roomService = roomService;
        this.locationService = locationService;
    }

    @GetMapping(value = "/rooms")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping(value = "/rooms/{id}")
    public Room getRoomById(@PathVariable("id") @Min(1) int id, HttpServletRequest request) {

        Room room = roomService.findById(id).orElseThrow(() -> new RoomNotFoundException("Room with " + id + " Not Found!"));
        locationService.validateLocation(request.getRemoteAddr(), room.getCountryName());
        return room;
    }

    @PostMapping(value = "/rooms")
    public Room addRoom(@Valid @RequestBody Room room) {
        return roomService.addRoom(room);
    }

    @PutMapping(value = "/rooms/{id}/activate")
    public void activate(@PathVariable("id") @Min(1) int id) {
        roomService.activate(id);
    }

    @PutMapping(value = "/rooms/{id}/deactivate")
    public void deactivate(@PathVariable("id") @Min(1) int id) {
        roomService.deactivate(id);
    }
}
