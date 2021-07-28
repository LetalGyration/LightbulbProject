package com.project.room.services;

import com.project.room.models.Room;
import com.project.room.models.RoomDTO;
import com.project.room.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService implements IRoom {

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
    public Room addRoom(RoomDTO roomDTO) {
        return roomRepo.save(convertDTOToRoom(roomDTO));
    }

    @Override
    public void activate(int id) {
        roomRepo.activate(id);
    }

    @Override
    public void deactivate(int id) {
        roomRepo.deactivate(id);
    }


    private Room convertDTOToRoom(RoomDTO roomDTO){

        Room room = new Room();
        room.setName(roomDTO.getName());
        room.setCountryName(roomDTO.getCountryName());
        room.setStatus(roomDTO.isStatus());

        return room;
    }
}
