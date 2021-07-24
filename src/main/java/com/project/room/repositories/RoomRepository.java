package com.project.room.repositories;

import com.project.room.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Modifying
    @Query("update Room r set r.status = :status where r.id = :id")
    void updateStatus(@Param(value = "id") int id, @Param(value = "status") byte status);
}
