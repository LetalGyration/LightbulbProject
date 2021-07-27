package com.project.room.services;

import com.project.room.models.Room;
import com.project.room.models.RoomDTO;

import java.util.List;
import java.util.Optional;

public interface IRoom {

    List<Room> getAllRooms();

    Optional<Room> findById(int id);

    Room addRoom(RoomDTO roomDTO);

    void activate(int id);

    void deactivate(int id);
}
