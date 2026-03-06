HotelManagement
HotelBookingApp is a modern web application designed to simplify the hotel reservation process for both customers and hotel managers. The application is built using Java and Spring Boot for the backend and Thymeleaf for the frontend, following the Model-View-Controller (MVC) architecture.

🚀 Features
User Registration & Management
Users can register and log in securely.
Profile management functionality is available.
Strong password validation is implemented to ensure security.
Hotel Management
Hotel managers can add and update hotel information.

Managers can specify details such as:

Hotel name
Address
Room availability
Room pricing
Hotel Search
Customers can search for hotels based on:

Location
Check-in date
Check-out date
Hotel Listing
The application displays a list of available hotels including:

Hotel name
Number of available rooms
Price per room
Hotel Details
Each hotel page provides detailed information such as:

Hotel name
Address
Room availability
Pricing information
Interactive map using Nominatim Geocoding API and Leaflet.js
Room Booking
Customers can select the required number of rooms and proceed to finalize the booking through the payment process.

Payment Processing
Secure credit card payment system

Includes validation such as:

Luhn algorithm for card validation
Expiry date validation
CVV validation
No third-party payment gateways are used.

Booking Management
Both customers and hotel managers can view and manage bookings through their dashboards.

Admin Panel
The admin panel allows administrators to manage:

Users
Hotels
Rooms
Bookings
Responsive Design
The application is optimized for multiple devices including:

Desktops
Tablets
Smartphones
🛠 Technology Stack
Backend
Java 17
Spring Boot 3.1.1
Spring Web MVC 6.0.10
Frontend
Thymeleaf 3.1.1
Bootstrap 5.3.0
Bootstrap Icons 1.10.5
jQuery 3.7.0
jQuery UI 1.13.2
Leaflet 1.9.4 (Interactive Maps)
Database
MySQL
Security
Spring Security 6.1.1 (Authentication & Authorization)
Tools & Libraries
Lombok (reduces boilerplate code)
Thymeleaf Layout Dialect
Thymeleaf Extras
📋 Prerequisites
Before running the project, make sure you have installed:

Java JDK 17
Maven
MySQL
👨‍💻 Author
Md Afzal Java Developer
```
HotelBookingApp
│
├── src
│   └── main
│       ├── java
│       │   └── com
│       │       └── afzalll
│       │           └── hotelbookingapp
│       │               ├── config
│       │               │   └── SecurityConfig.java
│       │               │
│       │               ├── controller
│       │               │   ├── AuthController.java
│       │               │   ├── HotelController.java
│       │               │   ├── BookingController.java
│       │               │   └── AdminController.java
│       │               │
│       │               ├── service
│       │               │   ├── UserService.java
│       │               │   ├── HotelService.java
│       │               │   ├── BookingService.java
│       │               │   └── impl
│       │               │       ├── UserServiceImpl.java
│       │               │       ├── HotelServiceImpl.java
│       │               │       └── BookingServiceImpl.java
│       │               │
│       │               ├── repository
│       │               │   ├── UserRepository.java
│       │               │   ├── HotelRepository.java
│       │               │   └── BookingRepository.java
│       │               │
│       │               ├── model
│       │               │   ├── User.java
│       │               │   ├── Hotel.java
│       │               │   ├── Room.java
│       │               │   └── Booking.java
│       │               │
│       │               └── HotelBookingAppApplication.java
│       │
│       └── resources
│           ├── static
│           │   ├── css
│           │   ├── js
│           │   └── images
│           │
│           ├── templates
│           │   ├── auth
│           │   ├── hotels
│           │   ├── bookings
│           │   ├── admin
│           │   └── fragments
│           │
│           └── application.properties
│
├── pom.xml
└── README.md

```
