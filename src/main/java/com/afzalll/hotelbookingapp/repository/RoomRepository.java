package com.afzalll.hotelbookingapp.repository;

import com.afzalll.hotelbookingapp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
