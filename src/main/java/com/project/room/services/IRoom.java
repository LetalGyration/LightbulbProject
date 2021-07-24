package com.project.room.services;

import com.project.room.models.Room;

import java.util.List;
import java.util.Optional;

public interface IRoom {
    List<Room> getAllRooms();
    Optional<Room> findById(int id);
    Room addRoom(Room room);
    void updateStatus(int id, byte status);
}
