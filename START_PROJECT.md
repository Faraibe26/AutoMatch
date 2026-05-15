# 🚗 AutoMatch AI - Complete Project Setup

## Prerequisites

Before starting the project, ensure you have:

- **Node.js** 14+ and npm (for React frontend)
- **Java JDK** 11+ (for Spring Boot backend)
- **PostgreSQL** 12+ (optional - H2 is configured for development)

---

## 🏃 Quick Start (Development Mode)

### 1. Start Backend (Spring Boot)

```bash
# Navigate to project root
cd /Users/faraibekhan/BankAccount/BankAccount

# Build and run the Spring Boot application
# Option 1: Using Maven (if installed)
mvn clean spring-boot:run

# Option 2: If Maven not available, compile and run with Java
javac -d build/classes -cp src/main/java src/main/java/com/automatch/**/*.java
java -cp build/classes:lib/* com.automatch.AutoMatchAiApplication
```

**Expected Output:**
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| ._ \_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.7.14)

AutoMatch AI - Car Recommendation Engine started successfully!
Server running on http://localhost:8080
API available at http://localhost:8080/api
Swagger UI available at http://localhost:8080/api/swagger-ui.html
```

### 2. Start Frontend (React)

**In a new terminal window:**

```bash
# Navigate to frontend directory
cd /Users/faraibekhan/BankAccount/BankAccount/frontend

# Install dependencies (first time only)
npm install

# Start development server
npm start
```

**Expected Output:**
```
Compiled successfully!

You can now view automatch-ai-frontend in the browser.

  Local:            http://localhost:3000
  On Your Network:  http://192.168.1.xxx:3000
```

---

## 🌐 Access the Application

1. **Frontend Application:** http://localhost:3000
   - Login page with demo credentials
   - Dashboard with car listings
   - AI recommendation engine
   - Favorites management

2. **Backend API:** http://localhost:8080/api
   - RESTful API endpoints
   - Swagger UI: http://localhost:8080/api/swagger-ui.html
   - H2 Console (dev): http://localhost:8080/api/h2-console

---

## 📝 Demo Credentials

### Test Account 1
- **Email:** demo@automatch.com
- **Password:** Demo@123456
- **First Name:** Demo
- **Last Name:** User

### Test Account 2
- **Email:** john@automatch.com
- **Password:** John@123456
- **First Name:** John
- **Last Name:** Doe

---

## 🔗 API Endpoints

### Authentication
```
POST   /api/auth/register     - Create new account
POST   /api/auth/login        - Login and get JWT token
GET    /api/auth/me           - Verify authentication 🔒
```

### Cars
```
GET    /api/cars              - Get all available cars
GET    /api/cars/{id}         - Get specific car details
GET    /api/cars/search       - Search cars by make/model
GET    /api/cars/filter/price - Filter by price range
GET    /api/cars/filter/efficiency - Filter by MPG
GET    /api/cars/filter/reliability - Filter by reliability
GET    /api/cars/filter/year  - Filter by year
GET    /api/cars/popular      - Get most favorited cars
```

### Recommendations (AI Engine) 🤖
```
POST   /api/recommendations/generate - Generate recommendations 🔒
GET    /api/recommendations/user     - Get user's recommendations 🔒
GET    /api/recommendations/top      - Get top recommendations 🔒
GET    /api/recommendations/{id}     - Get specific recommendation
```

### Favorites
```
GET    /api/favorites         - Get all favorite cars 🔒
POST   /api/favorites/{carId} - Add car to favorites 🔒
DELETE /api/favorites/{carId} - Remove from favorites 🔒
GET    /api/favorites/{carId}/is-favorited - Check if favorited 🔒
GET    /api/favorites/count   - Get total favorites count 🔒
```

---

## 🛠️ Troubleshooting

### Backend won't start

1. **Check if port 8080 is already in use:**
   ```bash
   lsof -i :8080
   kill -9 <PID>
   ```

2. **Verify Java installation:**
   ```bash
   java -version
   ```

3. **Check Maven/Spring Boot dependencies:**
   ```bash
   cd /Users/faraibekhan/BankAccount/BankAccount
   # Ensure pom.xml exists and is valid
   cat pom.xml | head -30
   ```

### Frontend won't start

1. **Check if port 3000 is in use:**
   ```bash
   lsof -i :3000
   ```

2. **Clear node_modules and reinstall:**
   ```bash
   cd frontend
   rm -rf node_modules package-lock.json
   npm install
   ```

3. **Verify Node.js version:**
   ```bash
   node -v  # Should be 14+
   npm -v   # Should be 6+
   ```

### API Connection Issues

1. **Verify backend is running:** http://localhost:8080/api/cars
2. **Check frontend .env file:**
   ```bash
   cat frontend/.env
   # Should show: REACT_APP_API_URL=http://localhost:8080/api
   ```
3. **Check CORS settings** in backend (application.properties)

---

## 📂 Project Structure

```
AutoMatch AI/
├── src/main/java/com/automatch/
│   ├── AutoMatchAiApplication.java      (Main Spring Boot app)
│   ├── controllers/                      (REST endpoints)
│   ├── services/                         (Business logic + AI engine)
│   ├── models/                           (JPA entities)
│   ├── repositories/                     (Data access layer)
│   ├── dto/                              (Data transfer objects)
│   ├── security/                         (JWT & authentication)
│   └── config/                           (Configuration classes)
│
├── frontend/
│   ├── public/
│   ├── src/
│   │   ├── pages/                        (Dashboard, Login, etc.)
│   │   ├── components/                   (CarCard, Navbar, etc.)
│   │   ├── context/                      (AuthContext for state)
│   │   ├── services/                     (API client)
│   │   ├── App.js                        (Main React component)
│   │   └── index.js                      (React entry point)
│   ├── package.json
│   ├── tailwind.config.js                (Tailwind CSS config)
│   └── .env                              (Environment variables)
│
├── pom.xml                               (Maven config)
├── src/main/resources/
│   └── application.properties             (Spring Boot config)
└── README.md
```

---

## 🎨 Frontend Features

### Dashboard
- Overview of available cars
- Statistics (total cars, average price, saved favorites)
- Real-time search and filtering
- Car cards with detailed specs

### AI Recommendations
- Set custom preferences (budget, MPG, reliability, etc.)
- Weight algorithm parameters
- See match scores (0-100) for each car
- View explanation for each recommendation

### Favorites
- Save favorite cars
- View saved collection
- Quick access to favorite cars
- Remove from collection

### Navigation
- Responsive navbar with mobile menu
- JWT-protected routes
- Automatic logout on token expiration

---

## 🚀 Deployment

### Production Build (Frontend)
```bash
cd frontend
npm run build
# Creates optimized build in frontend/build/
```

### Docker (Optional)
```bash
# Build Docker image
docker build -t automatch-ai .

# Run container
docker run -p 8080:8080 automatch-ai
```

### Environment Variables (Production)

**Backend (.env):**
```
SPRING_DATASOURCE_URL=jdbc:postgresql://prod-db:5432/automatch_ai
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=secure_password
JWT_SECRET=your_secure_jwt_secret_key
JWT_EXPIRATION=86400000
```

**Frontend (.env.production):**
```
REACT_APP_API_URL=https://api.automatch.com
REACT_APP_ENV=production
```

---

## 📚 Additional Resources

- **Backend API Documentation:** [API_DOCUMENTATION.md](API_DOCUMENTATION.md)
- **Architecture Guide:** [ARCHITECTURE.md](ARCHITECTURE.md)
- **README:** [AUTOMATCH_README.md](AUTOMATCH_README.md)
- **Integration Guide:** [INTEGRATION_GUIDE.md](INTEGRATION_GUIDE.md)

---

## 🤝 Support

For issues or questions:
1. Check the troubleshooting section above
2. Review logs in backend console
3. Check browser console for frontend errors (F12)
4. Verify all ports are available (8080 for backend, 3000 for frontend)

---

**Happy coding! 🚗✨**
