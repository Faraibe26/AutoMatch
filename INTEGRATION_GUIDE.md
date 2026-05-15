# Frontend & Backend Integration Guide

## System Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                         Client Browser                          │
│                    (http://localhost:3000)                      │
├─────────────────────────────────────────────────────────────────┤
│                                                                   │
│   ┌─────────────────────────────────────────────────────────┐   │
│   │              React Frontend (SPA)                       │   │
│   │  ┌─────────────────────────────────────────────────┐    │   │
│   │  │  Pages: Login, Register, Dashboard, etc.       │    │   │
│   │  └─────────────────────────────────────────────────┘    │   │
│   │  ┌─────────────────────────────────────────────────┐    │   │
│   │  │  Context API (AuthContext)                     │    │   │
│   │  │  - User state management                       │    │   │
│   │  │  - Auth token storage (localStorage)           │    │   │
│   │  └─────────────────────────────────────────────────┘    │   │
│   │  ┌─────────────────────────────────────────────────┐    │   │
│   │  │  Axios API Client (services/api.js)            │    │   │
│   │  │  - JWT token injection                         │    │   │
│   │  │  - Request/response interceptors               │    │   │
│   │  │  - Error handling                              │    │   │
│   │  └─────────────────────────────────────────────────┘    │   │
│   └─────────────────────────────────────────────────────────┘   │
│                                                                   │
│          HTTPS/CORS Request/Response with JWT Token             │
│                                                                   │
└─────────────────────────────────────────────────────────────────┘
                             ↕↕↕
┌─────────────────────────────────────────────────────────────────┐
│                Spring Boot Backend API                           │
│              (http://localhost:8080/api)                        │
├─────────────────────────────────────────────────────────────────┤
│                                                                   │
│   ┌─────────────────────────────────────────────────────────┐   │
│   │            REST Controllers                            │   │
│   │  ┌────────────────────────────────────────────────┐    │   │
│   │  │  AuthController      (/api/auth/*)           │    │   │
│   │  │  CarController       (/api/cars/*)           │    │   │
│   │  │  RecommendationController (/api/recommendations/*)  │    │
│   │  │  FavoriteCarController    (/api/favorites/*)       │    │
│   │  └────────────────────────────────────────────────┘    │   │
│   └─────────────────────────────────────────────────────────┘   │
│                                                                   │
│   ┌─────────────────────────────────────────────────────────┐   │
│   │            Service Layer                               │   │
│   │  ┌────────────────────────────────────────────────┐    │   │
│   │  │  AuthService         - User authentication    │    │   │
│   │  │  CarService          - Car operations        │    │   │
│   │  │  RecommendationService - AI recommendation   │    │   │
│   │  └────────────────────────────────────────────────┘    │   │
│   └─────────────────────────────────────────────────────────┘   │
│                                                                   │
│   ┌─────────────────────────────────────────────────────────┐   │
│   │            Repository Layer (JPA)                       │   │
│   │  ┌────────────────────────────────────────────────┐    │   │
│   │  │  UserRepository, CarRepository, etc.          │    │   │
│   │  │  - CRUD operations                            │    │   │
│   │  │  - Custom queries                             │    │   │
│   │  └────────────────────────────────────────────────┘    │   │
│   └─────────────────────────────────────────────────────────┘   │
│                                                                   │
│   ┌─────────────────────────────────────────────────────────┐   │
│   │            Database (H2 or PostgreSQL)                  │   │
│   │  ┌────────────────────────────────────────────────┐    │   │
│   │  │  Tables: users, cars, recommendations, etc.   │    │   │
│   │  │  JPA/Hibernate ORM mapping                     │    │   │
│   │  └────────────────────────────────────────────────┘    │   │
│   └─────────────────────────────────────────────────────────┘   │
│                                                                   │
└─────────────────────────────────────────────────────────────────┘
```

## Data Flow

### 1. Authentication Flow

```
User Input (Email, Password)
            ↓
   React Component (Login.js)
            ↓
   AuthContext.login()
            ↓
   API Call: POST /api/auth/login
            ↓
   Spring Backend: AuthController.login()
            ↓
   AuthService: Verify credentials
            ↓
   Generate JWT Token
            ↓
   Response: { token, user }
            ↓
   localStorage.setItem('token', token)
            ↓
   Set AuthContext.user
            ↓
   Navigate to Dashboard
            ↓
   JWT auto-injected in all subsequent requests
```

### 2. Car Recommendation Flow

```
User Sets Preferences
            ↓
   Recommendations.js Component
            ↓
   API Call: POST /api/recommendations/generate
            ↓
   Request Body: { preferences }
            ↓
   Spring Backend: RecommendationController
            ↓
   RecommendationService.generateRecommendations()
            ↓
   For each available car:
     - Calculate budget score
     - Calculate MPG score
     - Calculate reliability score
     - Calculate maintenance score
     - Calculate mileage score
     - Apply weights (20/15/25/20/20)
     - Generate explanation
            ↓
   Sort by match score (descending)
            ↓
   Return top recommendations
            ↓
   React: Display recommendations with scores
```

### 3. Favorite Management Flow

```
User Clicks Heart Icon
            ↓
   CarCard.onFavoriteToggle()
            ↓
   if favorited:
     API Call: DELETE /api/favorites/{carId}
   else:
     API Call: POST /api/favorites/{carId}
            ↓
   Spring Backend: FavoriteCarController
            ↓
   FavoriteCarService: Add/Remove favorite
            ↓
   Database: Update favorite_cars table
            ↓
   Response: Success/Error
            ↓
   React: Update UI (heart icon state)
```

## API Contract Examples

### Register
```javascript
// Request
POST /api/auth/register
{
  "email": "user@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe"
}

// Response (200 OK)
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "user": {
    "id": 1,
    "email": "user@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "createdAt": "2026-05-13T10:00:00"
  },
  "message": "User registered successfully"
}
```

### Generate Recommendations
```javascript
// Request
POST /api/recommendations/generate
Authorization: Bearer <JWT_TOKEN>
{
  "minBudget": 20000,
  "maxBudget": 60000,
  "minMpg": 20,
  "minReliability": 75,
  "maxMaintenanceCost": 500,
  "budgetWeight": 20,
  "mpgWeight": 15,
  "reliabilityWeight": 25,
  "maintenanceWeight": 20,
  "mileageWeight": 20
}

// Response (200 OK)
[
  {
    "id": 1,
    "matchScore": 92,
    "budgetScore": 90,
    "mpgScore": 85,
    "reliabilityScore": 98,
    "maintenanceScore": 88,
    "mileageScore": 92,
    "explanation": "Excellent match! Great reliability, low maintenance, and excellent fuel efficiency.",
    "car": {
      "id": 5,
      "year": 2023,
      "make": "Toyota",
      "model": "RAV4",
      "price": 35200,
      "mpg": 29,
      "reliabilityScore": 95,
      "maintenanceCost": 450,
      "mileage": 5000
    }
  },
  ...
]
```

## CORS Configuration

### Backend (Spring Boot)

```java
@Bean
public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry
              .addMapping("/api/**")
              .allowedOrigins("http://localhost:3000", "http://localhost:8080")
              .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
              .allowedHeaders("*")
              .allowCredentials(true)
              .maxAge(3600);
        }
    };
}
```

### Frontend (React)

```javascript
// Automatic JWT injection via Axios interceptor
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);
```

## Environment-Specific Configuration

### Development

**Backend** (`application.properties`):
```properties
server.port=8080
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true
logging.level.root=INFO
logging.level.com.automatch=DEBUG
```

**Frontend** (`.env`):
```env
REACT_APP_API_URL=http://localhost:8080/api
REACT_APP_ENV=development
```

### Production

**Backend** (`application-prod.properties`):
```properties
server.port=8080
server.servlet.context-path=/api
spring.datasource.url=jdbc:postgresql://prod-db:5432/automatch
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
logging.level.root=WARN
```

**Frontend** (`.env.production`):
```env
REACT_APP_API_URL=https://api.automatch.app
REACT_APP_ENV=production
```

## Token Management

### Token Storage

Tokens are stored in browser localStorage:
```javascript
// Store token after login
localStorage.setItem('token', response.data.token);
localStorage.setItem('user', JSON.stringify(response.data.user));

// Retrieve token for API calls
const token = localStorage.getItem('token');

// Clear on logout
localStorage.removeItem('token');
localStorage.removeItem('user');
```

### Token Injection

All API requests automatically include token:
```javascript
// Axios interceptor in services/api.js
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});
```

### Token Validation

Backend validates JWT on protected endpoints:
```java
// JwtTokenProvider.validateToken()
public boolean validateToken(String token) {
    try {
        Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
            .build()
            .parseClaimsJws(token);
        return true;
    } catch (JwtException | IllegalArgumentException e) {
        return false;
    }
}
```

## Error Handling

### API Errors

**Backend Response Format**:
```json
{
  "timestamp": "2026-05-13T10:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid budget range",
  "path": "/api/recommendations/generate"
}
```

**Frontend Error Handling**:
```javascript
try {
  const response = await api.post('/endpoint', data);
  // Success
} catch (error) {
  const message = error.response?.data?.message || 'Request failed';
  // Display error to user
}
```

### Common Status Codes

| Code | Meaning | Action |
|------|---------|--------|
| 200 | OK | Success |
| 201 | Created | Resource created |
| 400 | Bad Request | Validate input |
| 401 | Unauthorized | Re-login |
| 403 | Forbidden | Check permissions |
| 404 | Not Found | Resource doesn't exist |
| 500 | Server Error | Contact support |

## Testing Integration

### 1. Test Authentication

```bash
# Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "test123",
    "firstName": "Test",
    "lastName": "User"
  }'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "test123"
  }'
```

### 2. Test Car Endpoints

```bash
# Get all cars
curl http://localhost:8080/api/cars

# Search cars
curl "http://localhost:8080/api/cars/search?make=Toyota&model=RAV4"

# Filter by price
curl "http://localhost:8080/api/cars/filter/price?minPrice=20000&maxPrice=60000"
```

### 3. Test Recommendations

```bash
# Generate recommendations (requires JWT token)
curl -X POST http://localhost:8080/api/recommendations/generate \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN>" \
  -d '{
    "minBudget": 20000,
    "maxBudget": 60000,
    "minMpg": 20,
    "minReliability": 75,
    "maxMaintenanceCost": 500,
    "budgetWeight": 20,
    "mpgWeight": 15,
    "reliabilityWeight": 25,
    "maintenanceWeight": 20,
    "mileageWeight": 20
  }'
```

## Performance Optimization

### Frontend Optimization
- Implement React.memo() for expensive components
- Use lazy loading for pages
- Implement request debouncing for search
- Cache API responses

### Backend Optimization
- Add database indexing
- Implement caching (Redis)
- Use query pagination
- Optimize JPA queries with projections

## Monitoring & Logging

### Backend Logging

```java
private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

@PostMapping("/login")
public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
    logger.info("Login attempt for email: {}", request.getEmail());
    try {
        // Process login
        logger.info("Login successful for: {}", request.getEmail());
    } catch (Exception e) {
        logger.error("Login failed: {}", e.getMessage());
    }
}
```

### Frontend Logging

```javascript
// services/api.js
api.interceptors.request.use((config) => {
  console.log('[API Request]', config.method.toUpperCase(), config.url);
  return config;
});

api.interceptors.response.use(
  (response) => {
    console.log('[API Response]', response.status, response.data);
    return response;
  },
  (error) => {
    console.error('[API Error]', error.response?.status, error.message);
    return Promise.reject(error);
  }
);
```

## Deployment Considerations

### Backend Deployment
- Use environment variables for sensitive data
- Configure connection pooling
- Set up database migrations
- Configure logging
- Set up health checks

### Frontend Deployment
- Optimize build size
- Enable gzip compression
- Use CDN for assets
- Set up environment-specific builds
- Configure analytics

## Security Best Practices

1. **JWT Token Security**
   - Use HTTPS in production
   - Set token expiration time
   - Implement token refresh
   - Store tokens securely

2. **CORS Configuration**
   - Restrict origins to known domains
   - Don't allow * in production
   - Disable credentials if not needed

3. **API Validation**
   - Validate all inputs
   - Use proper error messages
   - Implement rate limiting
   - Log security events

4. **Password Security**
   - Use BCrypt for hashing
   - Enforce minimum length
   - Don't expose passwords in logs

## Next Steps

1. **Testing**
   - Write unit tests for services
   - Create integration tests
   - Set up end-to-end tests

2. **Monitoring**
   - Set up error tracking (Sentry)
   - Implement analytics
   - Create dashboards

3. **Performance**
   - Monitor API response times
   - Optimize database queries
   - Implement caching

4. **Security**
   - Penetration testing
   - Security audit
   - Dependency scanning

---

**Last Updated:** May 13, 2026
**Version:** 1.0.0
