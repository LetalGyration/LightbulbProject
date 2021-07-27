package com.project.room.controller;

import com.project.room.models.Room;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetByIdRoomITCase extends CommonITCase {

    @Test
    public void whenGetSingleRoomById_thenReceiveSingleRoom() {
        Room room = saveSingleRandomRoom();

        ResponseEntity<Room> response = this.restTemplate.exchange(
                baseUrl + "rooms/" + room.getId(),
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()), Room.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(room.getId(), response.getBody().getId());
        assertEquals(room.getName(), response.getBody().getName());
        assertEquals(room.getCountryName(), response.getBody().getCountryName());
    }

    @Test
    public void whenGetSingleRoomByIdWithInvalidId_thenReturnNotFound() {
        ResponseEntity<Room> response = this.restTemplate.exchange(
                baseUrl + "rooms/-1", HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()), Room.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
