package com.afzalll.hotelbookingapp.service.impl;

import com.afzalll.hotelbookingapp.model.*;
import com.afzalll.hotelbookingapp.service.*;
//import edu.afzalll.hotelbookingapp.model.*;
import com.afzalll.hotelbookingapp.model.*;
import com.afzalll.hotelbookingapp.model.dto.AddressDTO;
import com.afzalll.hotelbookingapp.model.dto.BookingDTO;
import com.afzalll.hotelbookingapp.model.dto.BookingInitiationDTO;
import com.afzalll.hotelbookingapp.model.dto.RoomSelectionDTO;
import com.afzalll.hotelbookingapp.repository.BookingRepository;
//import edu.afzalll.hotelbookingapp.service.*;
import com.afzalll.hotelbookingapp.model.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final AvailabilityService availabilityService;
    private final PaymentService paymentService;
    private final CustomerService customerService;
    private final HotelService hotelService;


    @Override
    @Transactional
    public Booking saveBooking(BookingInitiationDTO bookingInitiationDTO, Long userId) {
        validateBookingDates(bookingInitiationDTO.getCheckinDate(), bookingInitiationDTO.getCheckoutDate());

        Customer customer = customerService.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with user ID: " + userId));

        Hotel hotel = hotelService.findHotelById(bookingInitiationDTO.getHotelId())
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found with ID: " + bookingInitiationDTO.getHotelId()));

        Booking booking = mapBookingInitDtoToBookingModel(bookingInitiationDTO, customer, hotel);

        return bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public BookingDTO confirmBooking(BookingInitiationDTO bookingInitiationDTO, Long customerId) {
        Booking savedBooking = this.saveBooking(bookingInitiationDTO, customerId);
        Payment savedPayment = paymentService.savePayment(bookingInitiationDTO, savedBooking);
        savedBooking.setPayment(savedPayment);
        bookingRepository.save(savedBooking);
        availabilityService.updateAvailabilities(bookingInitiationDTO.getHotelId(), bookingInitiationDTO.getCheckinDate(),
                bookingInitiationDTO.getCheckoutDate(), bookingInitiationDTO.getRoomSelections());
        return mapBookingModelToBookingDto(savedBooking);
    }

    @Override
    public List<BookingDTO> findAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(this::mapBookingModelToBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDTO findBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with ID: " + bookingId));
        return mapBookingModelToBookingDto(booking);
    }

    @Override
    public List<BookingDTO> findBookingsByCustomerId(Long customerId) {
        List<Booking> bookingDTOs = bookingRepository.findBookingsByCustomerId(customerId);
        return bookingDTOs.stream()
                .map(this::mapBookingModelToBookingDto)
                .sorted(Comparator.comparing(BookingDTO::getCheckinDate))
                .collect(Collectors.toList());
    }


    @Override
    public BookingDTO findBookingByIdAndCustomerId(Long bookingId, Long customerId) {
        Booking booking = bookingRepository.findBookingByIdAndCustomerId(bookingId, customerId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with ID: " + bookingId));
        return mapBookingModelToBookingDto(booking);
    }

    @Override
    public List<BookingDTO> findBookingsByManagerId(Long managerId) {
        List<Hotel> hotels = hotelService.findAllHotelsByManagerId(managerId);
        return hotels.stream()
                .flatMap(hotel -> bookingRepository.findBookingsByHotelId(hotel.getId()).stream())
                .map(this::mapBookingModelToBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDTO findBookingByIdAndManagerId(Long bookingId, Long managerId) {
        Booking booking = bookingRepository.findBookingByIdAndHotel_HotelManagerId(bookingId, managerId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with ID: " + bookingId + " and manager ID: " + managerId));
        return mapBookingModelToBookingDto(booking);
    }

    private void validateBookingDates(LocalDate checkinDate, LocalDate checkoutDate) {
        if (checkinDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Check-in date cannot be in the past");
        }
        if (checkoutDate.isBefore(checkinDate.plusDays(1))) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
    }

    @Override
    public BookingDTO mapBookingModelToBookingDto(Booking booking) {
        AddressDTO addressDto = AddressDTO.builder()
                .addressLine(booking.getHotel().getAddress().getAddressLine())
                .city(booking.getHotel().getAddress().getCity())
                .country(booking.getHotel().getAddress().getCountry())
                .build();

        List<RoomSelectionDTO> roomSelections = booking.getBookedRooms().stream()
                .map(room -> RoomSelectionDTO.builder()
                        .roomType(room.getRoomType())
                        .count(room.getCount())
                        .build())
                .collect(Collectors.toList());

        User customerUser = booking.getCustomer().getUser();

        Payment payment = booking.getPayment();


        return BookingDTO.builder()
                .id(booking.getId())
                .confirmationNumber(booking.getConfirmationNumber())
                .bookingDate(booking.getBookingDate())
                .customerId(booking.getCustomer().getId())
                .hotelId(booking.getHotel().getId())
                .checkinDate(booking.getCheckinDate())
                .checkoutDate(booking.getCheckoutDate())
                .roomSelections(roomSelections)
                .totalPrice(payment != null ? payment.getTotalPrice() : BigDecimal.ZERO)
                .hotelName(booking.getHotel().getName())
                .hotelAddress(addressDto)
                .customerName(customerUser.getName() + " " + customerUser.getLastName())
                .customerEmail(customerUser.getUsername())
                .paymentStatus(payment != null ? payment.getPaymentStatus() : null)
                .paymentMethod(payment != null ? payment.getPaymentMethod() : null)
                .build();
    }

    private Booking mapBookingInitDtoToBookingModel(BookingInitiationDTO bookingInitiationDTO, Customer customer, Hotel hotel) {
        Booking booking = Booking.builder()
                .customer(customer)
                .hotel(hotel)
                .checkinDate(bookingInitiationDTO.getCheckinDate())
                .checkoutDate(bookingInitiationDTO.getCheckoutDate())
                .build();

        for (RoomSelectionDTO roomSelection : bookingInitiationDTO.getRoomSelections()) {
            if (roomSelection.getCount() > 0) {
                BookedRoom bookedRoom = BookedRoom.builder()
                        .booking(booking)
                        .roomType(roomSelection.getRoomType())
                        .count(roomSelection.getCount())
                        .build();
                booking.getBookedRooms().add(bookedRoom);
            }
        }

        return booking;
    }

}
