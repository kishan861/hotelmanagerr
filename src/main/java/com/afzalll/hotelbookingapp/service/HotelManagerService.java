package com.afzalll.hotelbookingapp.service;

import com.afzalll.hotelbookingapp.model.HotelManager;
import com.afzalll.hotelbookingapp.model.User;

public interface HotelManagerService {

    HotelManager findByUser(User user);

}
