package com.project.room.controller;

import com.project.room.models.Room;
import com.project.room.models.RoomDTO;
import com.project.room.repositories.RoomRepository;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Optional;

public class CommonITCase {
    @Autowired
    private RoomRepository roomRepository;

    protected String baseUrl;

    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/";
    }

    protected Room createSingleRoom(){
        Room room = new Room();
        int random = (int)(Math.random() * 100 + 1);
        room.setName("Test Room " + random);
        room.setCountryName("Belarus");
        room.setStatus(false);
        return room;
    }

    protected RoomDTO convertRoomToDTO(Room room) {
        return new RoomDTO().builder()
                .name(room.getName())
                .countryName(room.getCountryName())
                .status(room.isStatus())
                .build();
    }

    protected Room saveSingleRandomRoom() {
        return roomRepository.save(createSingleRoom());
    }

    protected Optional<Room> findRoomInDbById(int id) {
        return roomRepository.findById(id);
    }
}
