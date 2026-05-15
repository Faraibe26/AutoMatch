# 🚗 AutoMatch AI - AI Car Recommendation Engine

A modern, full-stack AI-powered car recommendation system built with **Spring Boot**, **PostgreSQL**, **React.js**, **TailwindCSS**, and **JWT Authentication**.

**Refactored from a professional Banking Management System** to demonstrate enterprise-level architecture, design patterns, and software engineering practices.

---

## 🎯 Project Overview

AutoMatch AI intelligently matches users with their ideal vehicles using weighted AI algorithms that consider:

- **Budget Match** (20%) - Optimal price range fitting
- **Fuel Economy** (15%) - MPG preferences
- **Reliability** (25%) - Build quality and track record
- **Maintenance Cost** (20%) - Long-term affordability
- **Mileage** (20%) - Vehicle condition/usage

Each recommendation includes a **0-100 match score** and **human-readable explanation** for why a vehicle was recommended.

---

## ✨ Key Features

### 🔐 **Authentication & Security**
- JWT (JSON Web Token) authentication
- BCrypt password encryption
- Role-based access control (User/Admin)
- Secure token validation

### 🚗 **Car Catalog**
- Browse 10+ realistic vehicles
- Advanced filtering (price, year, MPG, reliability, fuel type, drive type)
- Search by make/model
- Popular/top-rated cars
- Favorite cars management

### 🤖 **AI Recommendation Engine**
- Intelligent matching algorithm
- Customizable preference weights
- Detailed scoring breakdown
- Natural language explanations
- Ranked recommendations

### 👤 **User Management**
- User registration and login
- Profile management
- Search history
- Favorite cars tracking
- Personalized recommendations

### 📊 **Dashboard Analytics**
- Recommendation statistics
- Favorite tracking
- Search preference history
- User engagement metrics

---

## 🏗️ Architecture & Design

### **Tech Stack**

| Component | Technology | Version |
|-----------|-----------|---------|
| **Backend** | Spring Boot | 2.7.14 |
| **Database** | PostgreSQL / H2 | Latest |
| **Authentication** | JWT (jjwt) | 0.11.5 |
| **ORM** | Spring Data JPA | - |
| **Security** | Spring Security | 2.7.14 |
| **API Docs** | Swagger/OpenAPI | 1.6.14 |
| **Frontend** | React.js | 18+ |
| **UI Framework** | TailwindCSS | 3+ |
| **State Management** | Redux / Context API | - |
| **HTTP Client** | Axios | - |

### **Architecture Layers**

```
┌─────────────────────────────────────────┐
│         Frontend (React + Tailwind)     │
│   Dashboard | Cars | Recommendations   │
└──────────────────┬──────────────────────┘
                   │ REST API
┌──────────────────▼──────────────────────┐
│      Controllers (REST Endpoints)       │
│  Auth | Cars | Recommendations | Users  │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│    Services (Business Logic)            │
│ Recommendation | Car | Auth | Search    │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│  Repositories (Data Access Layer)       │
│   JPA Repositories | Custom Queries     │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│    Database (PostgreSQL / H2)           │
│  Users | Cars | Recommendations | etc   │
└─────────────────────────────────────────┘
```

### **OOP Principles Implemented**

✅ **Encapsulation** - Private fields with controlled access  
✅ **Inheritance** - Entity hierarchies and service inheritance  
✅ **Polymorphism** - Different recommendation algorithms  
✅ **Abstraction** - Abstract services and repositories  
✅ **Single Responsibility** - Each class has one purpose  
✅ **Dependency Injection** - Spring manages all dependencies  

### **Design Patterns**

- **Service Layer Pattern** - Business logic separation
- **Repository Pattern** - Data access abstraction
- **DTO Pattern** - Clean API contracts
- **Factory Pattern** - Recommendation creation
- **Builder Pattern** - Object construction
- **Strategy Pattern** - Different scoring strategies

---

## 📁 Project Structure

```
automatch-ai/
├── src/
│   ├── main/
│   │   ├── java/com/automatch/
│   │   │   ├── models/
│   │   │   │   ├── User.java
│   │   │   │   ├── Car.java
│   │   │   │   ├── SearchPreference.java
│   │   │   │   ├── Recommendation.java
│   │   │   │   └── FavoriteCar.java
│   │   │   ├── repositories/
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── CarRepository.java
│   │   │   │   ├── RecommendationRepository.java
│   │   │   │   ├── SearchPreferenceRepository.java
│   │   │   │   └── FavoriteCarRepository.java
│   │   │   ├── services/
│   │   │   │   ├── RecommendationService.java (Core AI Engine)
│   │   │   │   ├── CarService.java
│   │   │   │   ├── AuthService.java
│   │   │   │   └── UserService.java
│   │   │   ├── controllers/
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── CarController.java
│   │   │   │   ├── RecommendationController.java
│   │   │   │   ├── FavoriteCarController.java
│   │   │   │   └── UserController.java
│   │   │   ├── dto/
│   │   │   │   ├── LoginRequest.java
│   │   │   │   ├── RegisterRequest.java
│   │   │   │   ├── AuthResponse.java
│   │   │   │   ├── CarDTO.java
│   │   │   │   ├── RecommendationDTO.java
│   │   │   │   └── SearchPreferenceRequest.java
│   │   │   ├── security/
│   │   │   │   ├── JwtTokenProvider.java
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── config/
│   │   │   │   └── DataInitializer.java
│   │   │   └── AutoMatchAiApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/java/com/automatch/
│       └── (Unit tests)
├── pom.xml
├── README.md
├── API_DOCUMENTATION.md
└── ARCHITECTURE.md
```

---

## 🚀 Getting Started

### **Prerequisites**

- Java 11+
- Maven 3.6+
- PostgreSQL 12+ (or use H2 for development)
- Node.js 14+ (for React frontend)
- npm or yarn

### **Backend Setup**

#### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/automatch-ai.git
cd automatch-ai/backend
```

#### 2. Configure Database (application.properties)
```properties
# Development (H2 in-memory)
spring.datasource.url=jdbc:h2:mem:testdb

# Production (PostgreSQL)
spring.datasource.url=jdbc:postgresql://localhost:5432/automatch_ai
spring.datasource.username=postgres
spring.datasource.password=your_password
```

#### 3. Build and Run
```bash
# Build
mvn clean install

# Run application
mvn spring-boot:run

# Or run directly
java -jar target/automatch-ai-1.0.0.jar
```

#### 4. Verify API is Running
```bash
# Test API
curl http://localhost:8080/api/cars

# Access Swagger UI
http://localhost:8080/api/swagger-ui.html

# Access H2 Console (development)
http://localhost:8080/api/h2-console
```

---

## 📚 API Endpoints

### **Authentication**

```bash
# Register
POST /api/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "securepass123",
  "firstName": "John",
  "lastName": "Doe"
}

# Login
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "securepass123"
}

# Response
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "type": "Bearer",
  "userId": 1,
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe"
}
```

### **Cars**

```bash
# Get all cars
GET /api/cars

# Get car by ID
GET /api/cars/{id}

# Search cars
GET /api/cars/search?make=Honda&model=CR-V

# Filter by price
GET /api/cars/filter/price?minPrice=20000&maxPrice=50000

# Get fuel-efficient cars
GET /api/cars/filter/efficiency?minMpg=25

# Get reliable cars
GET /api/cars/filter/reliability?minScore=80

# Get popular cars
GET /api/cars/popular
```

### **Recommendations** (Requires Authentication)

```bash
# Generate recommendations
POST /api/recommendations/generate
Authorization: Bearer {token}
Content-Type: application/json

{
  "minBudget": 25000,
  "maxBudget": 50000,
  "minMpg": 25,
  "maxMileage": 100000,
  "minReliabilityScore": 80,
  "preferredFuelType": "Hybrid",
  "budgetWeight": 20,
  "mpgWeight": 15,
  "reliabilityWeight": 25,
  "maintenanceWeight": 20,
  "mileageWeight": 20
}

# Get user recommendations
GET /api/recommendations/user
Authorization: Bearer {token}

# Get top recommendations
GET /api/recommendations/top?limit=5
Authorization: Bearer {token}

# Get specific recommendation
GET /api/recommendations/{id}
```

### **Favorites** (Requires Authentication)

```bash
# Get favorite cars
GET /api/favorites
Authorization: Bearer {token}

# Add to favorites
POST /api/favorites/{carId}
Authorization: Bearer {token}

# Remove from favorites
DELETE /api/favorites/{carId}
Authorization: Bearer {token}

# Check if favorited
GET /api/favorites/{carId}/is-favorited
Authorization: Bearer {token}

# Get favorite count
GET /api/favorites/count
Authorization: Bearer {token}
```

---

## 🤖 AI Recommendation Algorithm

### **Scoring Mechanism**

Each car is scored 0-100 against user preferences:

```
Overall Score = Weighted Sum of Component Scores

Component Scores:
├── Budget Score (0-100)
│   └── 100 = price at midpoint of budget range
│   └── Lower for prices outside range
│
├── MPG Score (0-100)
│   └── 100 = exceeds minimum requirement
│   └── Decreases below minimum
│
├── Reliability Score (0-100)
│   └── Based on car's reliability rating vs. user minimum
│
├── Maintenance Score (0-100)
│   └── 100 = lowest maintenance costs
│   └── Decreases for higher costs
│
└── Mileage Score (0-100)
    └── 100 = lowest mileage
    └── Decreases for higher mileage

Weights Applied:
├── Budget: 20%
├── MPG: 15%
├── Reliability: 25%
├── Maintenance: 20%
└── Mileage: 20%
```

### **Example Calculation**

```
User Preferences:
- Budget: $30,000 - $45,000
- Min MPG: 25
- Min Reliability: 80
- Max Mileage: 100,000
- Max Annual Maintenance: $500

Car: 2023 Honda CR-V
- Price: $32,500 (budgetScore: 92)
- MPG: 28.5 (mpgScore: 100)
- Reliability: 92 (reliabilityScore: 88)
- Maintenance: $450 (maintenanceScore: 95)
- Mileage: 15,000 (mileageScore: 90)

Overall Score = (92×0.20) + (100×0.15) + (88×0.25) + (95×0.20) + (90×0.20)
              = 18.4 + 15 + 22 + 19 + 18
              = 92.4 → 92/100 "Excellent Match"
```

---

## 📋 Sample Data

The application seeds 10 realistic vehicles on startup:

1. **Honda CR-V** - $32,500 | 28.5 MPG | 92 Reliability
2. **Toyota RAV4** - $35,200 | 29 MPG | 95 Reliability
3. **BMW X5** - $68,900 | 23.5 MPG | 78 Reliability
4. **Lexus RX** - $55,000 | 25 MPG | 94 Reliability
5. **Acura MDX** - $48,500 | 26.5 MPG | 88 Reliability
6. **Tesla Model 3** - $42,900 | 120 MPGe | 82 Reliability
7. **Toyota Camry** - $28,900 | 32 MPG | 93 Reliability
8. **Honda Civic** - $25,900 | 33 MPG | 90 Reliability
9. **Ford Mustang** - $45,000 | 21 MPG | 80 Reliability
10. **Chevrolet Silverado** - $52,000 | 20.5 MPG | 82 Reliability

---

## 🔒 Security Features

- ✅ JWT token-based authentication
- ✅ BCrypt password hashing
- ✅ CORS configuration
- ✅ Role-based access control
- ✅ Input validation
- ✅ SQL injection prevention (JPA)
- ✅ XSS protection headers

---

## 📦 Deployment

### **Docker**
```bash
# Build Docker image
docker build -t automatch-ai:latest .

# Run container
docker run -p 8080:8080 automatch-ai:latest
```

### **AWS / Cloud**
- Deploy on AWS EC2 or ECS
- Use RDS for PostgreSQL
- Use S3 for image storage
- CloudFront for CDN

### **Production Checklist**
- [ ] Use PostgreSQL (not H2)
- [ ] Set strong JWT secret
- [ ] Enable HTTPS/TLS
- [ ] Configure production domain CORS
- [ ] Set up CI/CD pipeline
- [ ] Enable monitoring/logging
- [ ] Implement rate limiting
- [ ] Set up database backups

---

## 🧪 Testing

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=RecommendationServiceTest

# Generate test coverage
mvn clean test jacoco:report
```

---

## 📈 Performance Optimizations

- Lazy loading for related entities
- Database query optimization
- Caching frequently accessed data
- Pagination for large datasets
- Index optimization for search queries

---

## 🚀 Future Enhancements

- [ ] **Machine Learning** - Train models on user behavior
- [ ] **Advanced Filters** - Color, interior features, warranty
- [ ] **Price Prediction** - Estimate future market values
- [ ] **Trade-in Valuation** - Calculate trade-in values
- [ ] **Financing Calculator** - Monthly payment estimation
- [ ] **Insurance Integration** - Estimate insurance costs
- [ ] **Real-time Inventory** - Connect to dealership systems
- [ ] **Mobile App** - React Native frontend
- [ ] **Push Notifications** - Alert on matching cars
- [ ] **Social Sharing** - Share recommendations
- [ ] **Comparison Tool** - Side-by-side car comparison
- [ ] **Video Tours** - 360° car views

---

## 📞 Support

For issues, questions, or suggestions:
- Open an issue on GitHub
- Email: support@automatch-ai.com
- Check API documentation: `/api/swagger-ui.html`

---

## 📄 License

MIT License - See LICENSE file for details

---

## 👨‍💻 Author

**Your Name**  
Full-Stack Software Engineer | Spring Boot | React | Cloud Architecture

---

**Last Updated:** May 13, 2026  
**Version:** 1.0.0  
**Status:** Production Ready ✅
