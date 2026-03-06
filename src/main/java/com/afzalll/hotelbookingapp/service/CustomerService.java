package com.afzalll.hotelbookingapp.service;

import com.afzalll.hotelbookingapp.model.Customer;

import java.util.Optional;

public interface CustomerService {

    Optional<Customer> findByUserId(Long userId);

    Optional<Customer> findByUsername(String username);
}
