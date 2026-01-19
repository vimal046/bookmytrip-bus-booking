# BookMyTrip - Bus Ticket Booking System

A full-stack web application for online bus ticket booking built with **Spring Boot** and **MySQL**.

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)

## 📋 Overview

BookMyTrip is a comprehensive bus ticket booking platform that allows users to search buses, book tickets, and manage bookings online. Administrators can manage routes, buses, schedules, and view all bookings through a dedicated admin panel.

**Live Demo:** [Add your deployed link here if available]

---

## ✨ Key Features

### For Users

- 🔐 **Secure Authentication** - Register and login with encrypted passwords
- 🔍 **Smart Search** - Search buses by source, destination, and date
- 🎫 **Easy Booking** - Book tickets with real-time seat availability
- 📊 **Booking Management** - View booking history and cancel tickets
- 🚌 **Browse Options** - View all available routes, buses, and schedules

### For Admins

- 🛣️ **Route Management** - Add, edit, and delete bus routes
- 🚍 **Bus Management** - Manage bus fleet and assignments
- 📅 **Schedule Management** - Create and manage bus schedules
- 📈 **Dashboard** - View statistics and all bookings
- 👥 **User Overview** - Monitor all user activities

### Technical Highlights

- ✅ Role-based access control (USER/ADMIN)
- ✅ Real-time seat availability tracking
- ✅ Transaction management for booking consistency
- ✅ Input validation and error handling
- ✅ Responsive UI with Bootstrap 5
- ✅ RESTful API design patterns

---

## 🛠️ Technology Stack

| Layer          | Technology                          |
| -------------- | ----------------------------------- |
| **Backend**    | Java 17, Spring Boot 3.2            |
| **Security**   | Spring Security with BCrypt         |
| **Database**   | MySQL 8.0                           |
| **ORM**        | Spring Data JPA, Hibernate          |
| **Frontend**   | Thymeleaf, Bootstrap 5, HTML/CSS/JS |
| **Build Tool** | Maven                               |
| **Utilities**  | Lombok                              |

---

## 🏗️ Architecture

```
┌─────────────────────────────────────┐
│     Presentation Layer              │
│   (Controllers + Thymeleaf)         │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│      Business Logic Layer           │
│         (Services)                  │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│      Data Access Layer              │
│       (Repositories)                │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│         MySQL Database              │
└─────────────────────────────────────┘
```

**Design Patterns Used:**

- MVC (Model-View-Controller)
- DTO (Data Transfer Object)
- Repository Pattern
- Service Layer Pattern
- Dependency Injection

---

## 🗄️ Database Schema

**Entities:**

- **Users** - User information and credentials
- **Routes** - Bus routes with source/destination
- **Buses** - Bus details and assignments
- **Schedules** - Trip schedules with timing and fare
- **Bookings** - User ticket bookings

**Relationships:**

- One User → Many Bookings
- One Route → Many Buses
- One Bus → Many Schedules
- One Schedule → Many Bookings

---

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA / Eclipse / VS Code)

### Installation Steps

1. **Clone the repository**

```bash
git clone https://github.com/YOUR-USERNAME/bookmytrip-bus-booking.git
cd bookmytrip-bus-booking
```

2. **Create MySQL database**

```sql
CREATE DATABASE bookmytrip_db;
```

3. **Configure database**

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookmytrip_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

4. **Build the project**

```bash
mvn clean install
```

5. **Run the application**

```bash
mvn spring-boot:run
```

6. **Access the application**

```
http://localhost:8080
```

## 📸 Screenshots

### Home Page

![Home Page](screenshots/home.png)
_Landing page with available routes and schedules_

### User Dashboard

![User Dashboard](screenshots/dashboard.png)
_User dashboard with search and booking options_

### Booking Process

![Booking](screenshots/booking.png)
_Ticket booking interface with seat selection_

### Admin Panel

![Admin Dashboard](screenshots/admin.png)
_Admin dashboard for managing system_

> **Note:** Add screenshots to `/screenshots` folder in your repository

---

## 📁 Project Structure

```
bookmytrip/
├── src/
│   ├── main/
│   │   ├── java/com/bookmytrip/
│   │   │   ├── config/           # Security & app configuration
│   │   │   ├── controller/       # REST controllers
│   │   │   ├── dto/              # Data transfer objects
│   │   │   ├── exception/        # Custom exceptions
│   │   │   ├── model/            # JPA entities
│   │   │   ├── repository/       # Data repositories
│   │   │   └── service/          # Business logic
│   │   └── resources/
│   │       ├── templates/        # Thymeleaf templates
│   │       ├── static/           # CSS, JS, images
│   │       └── application.properties
│   └── test/                     # Unit tests
├── pom.xml                       # Maven dependencies
└── README.md
```

---

## 🧪 Testing

```bash
# Run all tests
mvn test

# Run with coverage
mvn clean test jacoco:report
```

---

## 🔒 Security Features

- ✅ BCrypt password encryption
- ✅ Role-based authorization (USER/ADMIN)
- ✅ CSRF protection
- ✅ Session management
- ✅ SQL injection prevention (JPA/Hibernate)
- ✅ Input validation and sanitization

---

## 🚧 Future Enhancements

- [ ] Online payment integration (Razorpay/Stripe)
- [ ] Interactive seat selection UI
- [ ] PDF ticket generation with QR code
- [ ] Email/SMS notifications
- [ ] Bus ratings and reviews
- [ ] Multi-language support
- [ ] Mobile application (Android/iOS)
- [ ] Advanced analytics dashboard

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/NewFeature`)
3. Commit changes (`git commit -m 'Add NewFeature'`)
4. Push to branch (`git push origin feature/NewFeature`)
5. Open a Pull Request

---

## 👨‍💻 Author

**Your Name**

- GitHub: [https://github.com/vimal046](https://github.com/vimal046)
- LinkedIn: [www.linkedin.com/in/vimal-s-7572b6310](www.linkedin.com/in/vimal-s-7572b6310)

---

## 🙏 Acknowledgments

- Spring Boot Documentation
- Baeldung Tutorials
- Stack Overflow Community
- Bootstrap Team

---

<div align="center">

**⭐ Star this repository if you find it helpful!**

Made with ❤️ using Spring Boot

</div>
