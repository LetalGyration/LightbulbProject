package com.project.room.controllers;

import com.project.room.models.Room;
import com.project.room.models.RoomDTO;
import com.project.room.services.ILocationValidator;
import com.project.room.services.IRoom;
import com.project.room.services.LocationValidatorService;
import com.project.room.services.RoomService;
import com.project.room.utils.RoomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api")
public class RoomController {

    ILocationValidator locationService;
    IRoom roomService;
    SimpMessagingTemplate template;

    @Autowired
    public RoomController(RoomService roomService, LocationValidatorService locationService, SimpMessagingTemplate template) {

        this.template = template;
        this.roomService = roomService;
        this.locationService = locationService;
    }

    @GetMapping(value = "/rooms")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping(value = "/rooms/{id}")
    public Room getRoomById(@PathVariable("id") @Min(1) int id, HttpServletRequest request) {

        Room room = roomService.findById(id).orElseThrow(
                () -> new RoomNotFoundException("Room with " + id + " Not Found!"));
        locationService.validateLocation(request.getRemoteAddr(), room.getCountryName());

        return room;
    }

    @PostMapping(value = "/rooms")
    public Room addRoom(@Valid @RequestBody RoomDTO roomDTO) {
        return roomService.addRoom(roomDTO);
    }

    @PutMapping(value = "/rooms/{id}/activate")
    public void activate(@PathVariable("id") @Min(1) int id) {
        roomService.findById(id).orElseThrow(
                () -> new RoomNotFoundException("Room with " + id + " Not Found!"));
        roomService.activate(id);
        this.template.convertAndSend("/topic/notification", id);
    }

    @PutMapping(value = "/rooms/{id}/deactivate")
    public void deactivate(@PathVariable("id") @Min(1) int id) {
        roomService.findById(id).orElseThrow(
                () -> new RoomNotFoundException("Room with " + id + " Not Found!"));
        roomService.deactivate(id);
        this.template.convertAndSend("/topic/notification", id);
    }
}
