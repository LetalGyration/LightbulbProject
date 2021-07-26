package com.project.room;

import com.project.room.models.Room;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.shaded.javax.ws.rs.core.Application;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoomControllerIntegrationTests {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port + "/api";
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testGetAllRooms() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + "/rooms",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetRoomById() {
        Room room = testRestTemplate.getForObject(getRootUrl() + "/rooms/2", Room.class);
        System.out.println(room.getName());
        assertNotNull(room);
    }

    @Test
    public void testCreateRoom() {
        Room room = new Room();
        room.setName("adminRoom");
        room.setCountryName("Belarus");
        room.setStatus(false);
        ResponseEntity<Room> postResponse = testRestTemplate.postForEntity(getRootUrl() + "/rooms", room, Room.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    @Transactional
    public void testActivateRoom() {
        int id = 2;
        testRestTemplate.put(getRootUrl() + "/rooms/" + id + "/activate", null );
        Room room = testRestTemplate.getForObject(getRootUrl() + "/rooms/" + id, Room.class);
        assertNotNull(room);
    }

    @Test
    @Transactional
    public void testDeactivateRoom() {
        int id = 2;
        testRestTemplate.put(getRootUrl() + "/rooms/" + id + "/deactivate", null );
        Room room = testRestTemplate.getForObject(getRootUrl() + "/rooms/" + id, Room.class);
        assertNotNull(room);
    }

}
