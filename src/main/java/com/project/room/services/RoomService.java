package com.project.room.services;

import com.project.room.models.Room;
import com.project.room.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService implements IRoom{
    RoomRepository roomRepo;
    @Autowired
    public RoomService(RoomRepository roomRepo) {
        this.roomRepo = roomRepo;
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }

    @Override
    public Optional<Room> findById(int id) {
        return roomRepo.findById(id);
    }

    @Override
    public Room addRoom(Room room) {
        return roomRepo.save(room);
    }

    @Override
    public void updateStatus(int id, byte status) {
        roomRepo.updateStatus(id, status);
    }
}
