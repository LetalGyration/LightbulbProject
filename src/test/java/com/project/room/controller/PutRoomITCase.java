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
public class PutRoomITCase extends CommonITCase {
    private Room room;

    @Test
    public void whenPutActivateSingleRoom_thenIsUpdated() {
        room = saveSingleRandomRoom();
        room.setStatus(true);

        ResponseEntity<String> response = this.restTemplate.exchange(
                baseUrl + "/rooms/" + room.getId() + "/activate",
                HttpMethod.PUT,
                new HttpEntity<>(room, new HttpHeaders()),
                String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(room.isStatus(), findRoomInDbById(room.getId()).get().isStatus());
    }

    @Test
    public void whenPutDeactivateSingleRoom_thenIsUpdated() {
        room = saveSingleRandomRoom();
        room.setStatus(false);

        ResponseEntity<String> response = this.restTemplate.exchange(
                baseUrl + "/rooms/" + room.getId() + "/deactivate",
                HttpMethod.PUT,
                new HttpEntity<>(room, new HttpHeaders()),
                String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(room.isStatus(), findRoomInDbById(room.getId()).get().isStatus());
    }

}
