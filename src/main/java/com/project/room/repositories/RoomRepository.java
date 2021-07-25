package com.project.room.repositories;

import com.project.room.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Modifying
    @Transactional
    @Query("update Room r set r.status = true where r.id = :id")
    void activate(@Param(value = "id") int id);

    @Modifying
    @Transactional
    @Query("update Room r set r.status = false where r.id = :id")
    void deactivate(@Param(value = "id") int id);
}
