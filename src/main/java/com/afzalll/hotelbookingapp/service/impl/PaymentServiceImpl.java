package com.afzalll.hotelbookingapp.service.impl;

import com.afzalll.hotelbookingapp.model.Booking;
import com.afzalll.hotelbookingapp.model.Payment;
import com.afzalll.hotelbookingapp.model.dto.BookingInitiationDTO;
import com.afzalll.hotelbookingapp.model.enums.Currency;
import com.afzalll.hotelbookingapp.model.enums.PaymentMethod;
import com.afzalll.hotelbookingapp.model.enums.PaymentStatus;
import com.afzalll.hotelbookingapp.repository.PaymentRepository;
import com.afzalll.hotelbookingapp.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override

    public Payment savePayment(BookingInitiationDTO dto, Booking booking) {

        Payment payment = Payment.builder()
                .booking(booking)
                .totalPrice(dto.getTotalPrice())
                .paymentStatus(PaymentStatus.COMPLETED)
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .currency(Currency.USD)
                .build();

        // 🔥 VERY IMPORTANT
        booking.setPayment(payment);

        return paymentRepository.save(payment);
    }
}
