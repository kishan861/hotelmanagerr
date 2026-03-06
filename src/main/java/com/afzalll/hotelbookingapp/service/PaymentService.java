package com.afzalll.hotelbookingapp.service;

import com.afzalll.hotelbookingapp.model.Booking;
import com.afzalll.hotelbookingapp.model.Payment;
import com.afzalll.hotelbookingapp.model.dto.BookingInitiationDTO;

public interface PaymentService {

    Payment savePayment(BookingInitiationDTO bookingInitiationDTO, Booking booking);
}
