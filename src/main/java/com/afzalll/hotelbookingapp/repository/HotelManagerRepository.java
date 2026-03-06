package com.afzalll.hotelbookingapp.repository;

import com.afzalll.hotelbookingapp.model.HotelManager;
import com.afzalll.hotelbookingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelManagerRepository extends JpaRepository<HotelManager, Long> {

    Optional<HotelManager> findByUser(User user);
}
