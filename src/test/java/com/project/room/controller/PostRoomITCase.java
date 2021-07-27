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
public class PostRoomITCase extends CommonITCase {

    @Test
    public void whenPostSingleRoom_thenIsStoredInDb() {
        Room room = createSingleRoom();

        ResponseEntity<Room> response = this.restTemplate.exchange(
                baseUrl + "rooms/",
                HttpMethod.POST,
                new HttpEntity<>(convertRoomToDTO(room), new HttpHeaders()),
                Room.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(room.getName(), response.getBody().getName());
        assertEquals(room.getCountryName(), response.getBody().getCountryName());

        Room savedRoom = findRoomInDbById(response.getBody().getId()).get();
        assertEquals(room.getName(), savedRoom.getName());
        assertEquals(room.getCountryName(), savedRoom.getCountryName());
    }
}
