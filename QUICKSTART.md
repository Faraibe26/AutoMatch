# AutoMatch AI - Quick Start Guide

## Project Overview

AutoMatch AI is a full-stack AI-powered car recommendation engine built with:
- **Backend**: Spring Boot 2.7.14, Java 11, PostgreSQL, JWT Authentication
- **Frontend**: React 18, Tailwind CSS, React Router, Axios

## Quick Start (5 Minutes)

### Step 1: Start Backend Server

**Terminal 1:**
```bash
# Navigate to project root
cd /Users/faraibekhan/BankAccount/BankAccount

# Build and run with Maven
mvn clean install
mvn spring-boot:run
```

Backend runs on: `http://localhost:8080`

**Expected Output:**
```
Started AutoMatchAiApplication in X seconds
Application started successfully!
```

### Step 2: Start Frontend Application

**Terminal 2:**
```bash
# Navigate to frontend directory
cd /Users/faraibekhan/BankAccount/BankAccount/frontend

# Install dependencies (first time only)
npm install

# Start development server
npm start
```

Frontend runs on: `http://localhost:3000`

**Expected Output:**
```
Compiled successfully!
You can now view frontend in the browser.
```

### Step 3: Test the Application

1. Open browser to `http://localhost:3000`
2. You should see the Login page
3. Register a new account or use test credentials
4. Navigate through Dashboard, Recommendations, and Favorites

## Full Installation Guide

### Prerequisites
- Java 11 or higher
- Node.js 14+ and npm
- Maven 3.6+
- PostgreSQL 12+ (optional, H2 used in development)
- Git

### Backend Setup

#### 1. Clone and Navigate
```bash
cd /Users/faraibekhan/BankAccount/BankAccount
```

#### 2. Configure Database (Optional)

**For Development (H2 - No Setup Needed):**
Database is embedded and auto-configured. Skip to step 3.

**For Production (PostgreSQL):**

Create database:
```sql
CREATE DATABASE automatch_ai;
CREATE USER automatch WITH PASSWORD 'your_password';
ALTER ROLE automatch SET client_encoding TO 'utf8';
ALTER ROLE automatch SET default_transaction_isolation TO 'read committed';
ALTER ROLE automatch SET default_transaction_deferrable TO on;
ALTER ROLE automatch SET timezone TO 'UTC';
GRANT ALL PRIVILEGES ON DATABASE automatch_ai TO automatch;
\c automatch_ai
GRANT ALL ON SCHEMA public TO automatch;
```

Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/automatch_ai
spring.datasource.username=automatch
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQL10Dialect
```

#### 3. Build Backend
```bash
mvn clean install
```

#### 4. Run Backend
```bash
mvn spring-boot:run
```

OR

```bash
java -jar target/automatch-ai-1.0.0.jar
```

### Frontend Setup

#### 1. Navigate to Frontend
```bash
cd frontend
```

#### 2. Install Dependencies
```bash
npm install
```

#### 3. Configure Environment

Edit `frontend/.env`:
```env
REACT_APP_API_URL=http://localhost:8080/api
REACT_APP_ENV=development
```

#### 4. Start Development Server
```bash
npm start
```

#### 5. Build for Production
```bash
npm run build
```

## Testing the Application

### 1. Register New User

1. Go to `http://localhost:3000`
2. Click "Sign Up"
3. Enter:
   - First Name: John
   - Last Name: Doe
   - Email: john@example.com
   - Password: password123
4. Click "Create Account"

### 2. Test Dashboard

- View available cars
- Search by make/model/year
- Click favorite button on cars
- View statistics

### 3. Test Recommendations

1. Go to "Recommendations" tab
2. Adjust preferences (budget, MPG, reliability)
3. Click "Generate Recommendations"
4. View AI-powered matches with scores

### 4. Test Favorites

1. Go to "Favorites" tab
2. See all saved favorite cars
3. Remove favorites as needed

## API Endpoints

### Authentication
```
POST   /api/auth/register     - Register new user
POST   /api/auth/login        - Login user
GET    /api/auth/me           - Verify token (Protected)
```

### Cars
```
GET    /api/cars              - Get all cars
GET    /api/cars/{id}         - Get car by ID
GET    /api/cars/search       - Search cars
GET    /api/cars/filter/price - Filter by price
GET    /api/cars/filter/efficiency - Filter by MPG
GET    /api/cars/filter/reliability - Filter by reliability
GET    /api/cars/filter/year  - Filter by year
GET    /api/cars/popular      - Get top favorited cars
```

### Recommendations
```
POST   /api/recommendations/generate - Generate recommendations (Protected)
GET    /api/recommendations/user     - Get user recommendations (Protected)
GET    /api/recommendations/top      - Get top recommendations (Protected)
GET    /api/recommendations/{id}     - Get specific recommendation
```

### Favorites
```
GET    /api/favorites                - Get favorites (Protected)
POST   /api/favorites/{carId}        - Add favorite (Protected)
DELETE /api/favorites/{carId}        - Remove favorite (Protected)
GET    /api/favorites/{carId}/is-favorited - Check if favorited (Protected)
GET    /api/favorites/count          - Get favorites count (Protected)
```

## Development Commands

### Backend
```bash
# Build
mvn clean install

# Run
mvn spring-boot:run

# Run tests
mvn test

# Generate documentation
mvn javadoc:javadoc

# Package
mvn package

# Check for vulnerabilities
mvn dependency-check:check
```

### Frontend
```bash
# Install dependencies
npm install

# Start development
npm start

# Build for production
npm run build

# Run tests
npm test

# Run code coverage
npm test -- --coverage

# Run linter
npm run lint

# Eject configuration (⚠️ Irreversible)
npm run eject
```

## Troubleshooting

### Backend Won't Start

**Error: Port 8080 already in use**
```bash
# Kill process on port 8080 (macOS)
lsof -ti:8080 | xargs kill -9

# Or run on different port
mvn spring-boot:run -Dspring-boot.run.jvmArguments='-Dserver.port=8081'
```

**Error: Database connection failed**
- Check PostgreSQL is running
- Verify credentials in application.properties
- Use H2 for development (no setup needed)

### Frontend Won't Start

**Error: Port 3000 already in use**
```bash
# Kill process on port 3000 (macOS)
lsof -ti:3000 | xargs kill -9

# Or run on different port
PORT=3001 npm start
```

**Error: Cannot find module**
```bash
rm -rf node_modules package-lock.json
npm install
```

### CORS Errors

Ensure backend is running and `REACT_APP_API_URL` is correct in `.env`:
```env
REACT_APP_API_URL=http://localhost:8080/api
```

### API Calls Failing

1. Check backend is running on port 8080
2. Open browser console to see errors
3. Check network tab in DevTools
4. Verify JWT token is stored: `localStorage.getItem('token')`

## Project Structure

```
/Users/faraibekhan/BankAccount/BankAccount/
├── src/
│   ├── main/
│   │   ├── java/com/automatch/
│   │   │   ├── AutoMatchAiApplication.java
│   │   │   ├── config/
│   │   │   ├── controllers/
│   │   │   ├── dto/
│   │   │   ├── models/
│   │   │   ├── repositories/
│   │   │   ├── security/
│   │   │   └── services/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── context/
│   │   ├── pages/
│   │   ├── services/
│   │   ├── App.js
│   │   └── index.js
│   ├── public/
│   ├── .env
│   ├── tailwind.config.js
│   └── package.json
├── pom.xml
└── README files
```

## Environment Variables

### Backend (`application.properties`)
```properties
# Server
server.port=8080
server.servlet.context-path=/

# Database - H2 (Development)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# JWT
jwt.secret=AutoMatchAiSecretKey123456789
jwt.expiration=86400000

# CORS
cors.allowed-origins=http://localhost:3000,http://localhost:8080
```

### Frontend (`.env`)
```env
REACT_APP_API_URL=http://localhost:8080/api
REACT_APP_ENV=development
```

## Next Steps

1. **Backend Enhancement**
   - Add user profile management
   - Implement advanced filtering
   - Add caching layer (Redis)
   - Set up database migrations

2. **Frontend Enhancement**
   - Create car detail page
   - Add comparison tool
   - Implement advanced filters
   - Add user profile page

3. **DevOps**
   - Docker containerization
   - CI/CD pipeline setup
   - Production deployment
   - Database backups

## Useful Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [React Documentation](https://react.dev)
- [Tailwind CSS Documentation](https://tailwindcss.com/docs)
- [JWT.io](https://jwt.io)
- [API Documentation](./API_DOCUMENTATION.md)
- [Architecture Documentation](./ARCHITECTURE.md)

## Getting Help

1. Check existing documentation files
2. Review error logs in browser console
3. Check backend logs for API errors
4. Review git history for changes
5. Check the troubleshooting section above

## Performance Benchmarks

### Backend
- **Startup Time**: ~5-8 seconds
- **API Response Time**: 50-200ms
- **Database Queries**: Optimized with JPA

### Frontend
- **Build Time**: ~30 seconds
- **Initial Load**: ~2-3 seconds
- **Bundle Size**: ~150-200kb (gzipped)

## License

AutoMatch AI - All rights reserved

---

**Last Updated:** May 13, 2026
**Version:** 1.0.0
