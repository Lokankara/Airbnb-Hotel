package com.manager.hotel.dao;

import com.manager.hotel.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomDao extends JpaRepository<Room, Long> {
}
