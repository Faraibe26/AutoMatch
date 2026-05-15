# AutoMatch AI - API Documentation

## API Base URL

```
http://localhost:8080/api
```

---

## 🔐 Authentication

All endpoints marked with 🔒 require a valid JWT token.

### Request Header Format
```
Authorization: Bearer {jwt_token}
```

---

## Authentication Endpoints

### Register New User

**POST** `/auth/register`

Register a new user account.

**Request Body:**
```json
{
  "email": "john.doe@example.com",
  "password": "SecurePassword123!",
  "firstName": "John",
  "lastName": "Doe"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwi...",
  "type": "Bearer",
  "userId": 1,
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "message": "User registered successfully"
}
```

**Response (400 Bad Request):**
```json
{
  "error": "Email already registered"
}
```

---

### Login User

**POST** `/auth/login`

Authenticate user and receive JWT token.

**Request Body:**
```json
{
  "email": "john.doe@example.com",
  "password": "SecurePassword123!"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwi...",
  "type": "Bearer",
  "userId": 1,
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "message": "Login successful"
}
```

**Response (401 Unauthorized):**
```json
{
  "error": "Invalid email or password"
}
```

---

### Verify Authentication 🔒

**GET** `/auth/me`

Verify that authentication token is valid.

**Headers:**
```
Authorization: Bearer {jwt_token}
```

**Response (200 OK):**
```json
{
  "message": "Authenticated"
}
```

**Response (401 Unauthorized):**
```json
{
  "error": "Invalid or expired token"
}
```

---

## 🚗 Car Catalog Endpoints

### Get All Available Cars

**GET** `/cars`

Retrieve list of all available cars in the catalog.

**Query Parameters:** (optional)
- `page` (int) - Page number (0-indexed)
- `size` (int) - Items per page

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "make": "Honda",
    "model": "CR-V",
    "year": 2023,
    "price": 32500,
    "mileage": 15000,
    "mpg": 28.5,
    "fuelType": "Gasoline",
    "driveType": "AWD",
    "bodyType": "SUV",
    "reliabilityScore": 92,
    "annualMaintenanceCost": 450,
    "imageUrl": "https://images.unsplash.com/...",
    "description": "Reliable Honda CR-V with excellent fuel economy...",
    "available": true,
    "fullName": "2023 Honda CR-V",
    "favoriteCount": 45
  },
  ...
]
```

---

### Get Car by ID

**GET** `/cars/{id}`

Retrieve detailed information about a specific car.

**Path Parameters:**
- `id` (long) - Car ID

**Response (200 OK):**
```json
{
  "id": 1,
  "make": "Honda",
  "model": "CR-V",
  "year": 2023,
  "price": 32500,
  "mileage": 15000,
  "mpg": 28.5,
  "fuelType": "Gasoline",
  "driveType": "AWD",
  "bodyType": "SUV",
  "reliabilityScore": 92,
  "annualMaintenanceCost": 450,
  "imageUrl": "https://images.unsplash.com/...",
  "description": "Reliable Honda CR-V with excellent fuel economy...",
  "available": true,
  "fullName": "2023 Honda CR-V",
  "favoriteCount": 45
}
```

**Response (404 Not Found):**
```json
{
  "error": "Car not found"
}
```

---

### Search Cars

**GET** `/cars/search`

Search cars by make and/or model.

**Query Parameters:**
- `make` (string, optional) - Car make (Honda, Toyota, BMW, etc.)
- `model` (string, optional) - Car model (CR-V, RAV4, etc.)

**Example:**
```
GET /api/cars/search?make=Honda&model=CR-V
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "make": "Honda",
    "model": "CR-V",
    "year": 2023,
    "price": 32500,
    ...
  }
]
```

---

### Filter Cars by Price Range

**GET** `/cars/filter/price`

Find cars within a specific price range.

**Query Parameters:**
- `minPrice` (BigDecimal, required) - Minimum price
- `maxPrice` (BigDecimal, required) - Maximum price

**Example:**
```
GET /api/cars/filter/price?minPrice=25000&maxPrice=50000
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "make": "Honda",
    "model": "CR-V",
    "year": 2023,
    "price": 32500,
    ...
  },
  ...
]
```

---

### Get Fuel-Efficient Cars

**GET** `/cars/filter/efficiency`

Find cars with specified minimum fuel economy (MPG).

**Query Parameters:**
- `minMpg` (Double, default: 20.0) - Minimum MPG

**Example:**
```
GET /api/cars/filter/efficiency?minMpg=25
```

**Response (200 OK):**
```json
[
  {
    "id": 2,
    "make": "Toyota",
    "model": "RAV4",
    "mpg": 29.0,
    ...
  },
  ...
]
```

---

### Get Reliable Cars

**GET** `/cars/filter/reliability`

Find highly reliable cars.

**Query Parameters:**
- `minScore` (Integer, default: 75) - Minimum reliability score (0-100)

**Example:**
```
GET /api/cars/filter/reliability?minScore=85
```

**Response (200 OK):**
```json
[
  {
    "id": 2,
    "make": "Toyota",
    "model": "RAV4",
    "reliabilityScore": 95,
    ...
  },
  ...
]
```

---

### Get Popular/Top Favorited Cars

**GET** `/cars/popular`

Get the most frequently favorited cars.

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "make": "Honda",
    "model": "CR-V",
    "favoriteCount": 125,
    ...
  },
  ...
]
```

---

### Get Cars by Year Range

**GET** `/cars/filter/year`

Find cars from specific years.

**Query Parameters:**
- `startYear` (Integer, required) - Start year (inclusive)
- `endYear` (Integer, required) - End year (inclusive)

**Example:**
```
GET /api/cars/filter/year?startYear=2022&endYear=2023
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "make": "Honda",
    "model": "CR-V",
    "year": 2023,
    ...
  },
  ...
]
```

---

## 🤖 Recommendation Endpoints

### Generate Recommendations 🔒

**POST** `/recommendations/generate`

Generate AI-powered recommendations based on user preferences.

**Headers:**
```
Authorization: Bearer {jwt_token}
Content-Type: application/json
```

**Request Body:**
```json
{
  "minBudget": 25000,
  "maxBudget": 50000,
  "minMpg": 25,
  "maxMileage": 100000,
  "minReliabilityScore": 80,
  "preferredFuelType": "Hybrid",
  "preferredDriveType": "AWD",
  "preferredBodyType": "SUV",
  "preferredBrands": "Honda,Toyota,Lexus",
  "excludedBrands": "Ford,Chevy",
  "maxAnnualMaintenance": 700,
  "budgetWeight": 20,
  "mpgWeight": 15,
  "reliabilityWeight": 25,
  "maintenanceWeight": 20,
  "mileageWeight": 20,
  "name": "Family SUV Search"
}
```

**Response (200 OK):**
```json
[
  {
    "id": 101,
    "car": {
      "id": 1,
      "make": "Honda",
      "model": "CR-V",
      "year": 2023,
      "price": 32500,
      "mileage": 15000,
      "mpg": 28.5,
      "reliabilityScore": 92,
      "fullName": "2023 Honda CR-V",
      "imageUrl": "https://..."
    },
    "matchScore": 92,
    "budgetScore": 92,
    "mpgScore": 100,
    "reliabilityScore": 88,
    "maintenanceScore": 95,
    "mileageScore": 90,
    "explanation": "Why the 2023 Honda CR-V is recommended:\n\n✓ Budget: Excellent price match...\n✓ Fuel Economy: Excellent MPG...",
    "rank": 1,
    "tierLabel": "Excellent Match",
    "summary": "2023 Honda CR-V - Excellent Match (92% match)"
  },
  ...
]
```

**Response (401 Unauthorized):**
```json
{
  "error": "Invalid or expired token"
}
```

---

### Get User Recommendations 🔒

**GET** `/recommendations/user`

Retrieve all recommendations generated for the current user.

**Headers:**
```
Authorization: Bearer {jwt_token}
```

**Response (200 OK):**
```json
[
  {
    "id": 101,
    "car": { ... },
    "matchScore": 92,
    "budgetScore": 92,
    ...
  },
  ...
]
```

---

### Get Top Recommendations 🔒

**GET** `/recommendations/top`

Get the top N recommendations for the current user.

**Headers:**
```
Authorization: Bearer {jwt_token}
```

**Query Parameters:**
- `limit` (int, default: 5) - Number of recommendations to return

**Example:**
```
GET /api/recommendations/top?limit=10
```

**Response (200 OK):**
```json
[
  {
    "id": 101,
    "car": { ... },
    "matchScore": 92,
    ...
  },
  ...
]
```

---

### Get Specific Recommendation

**GET** `/recommendations/{id}`

Retrieve details of a specific recommendation.

**Path Parameters:**
- `id` (long) - Recommendation ID

**Response (200 OK):**
```json
{
  "id": 101,
  "car": { ... },
  "matchScore": 92,
  "budgetScore": 92,
  "mpgScore": 100,
  "reliabilityScore": 88,
  "maintenanceScore": 95,
  "mileageScore": 90,
  "explanation": "...",
  "rank": 1,
  "tierLabel": "Excellent Match",
  "summary": "2023 Honda CR-V - Excellent Match (92% match)"
}
```

---

## ❤️ Favorite Car Endpoints

### Get Favorite Cars 🔒

**GET** `/favorites`

Retrieve all favorite cars for the current user.

**Headers:**
```
Authorization: Bearer {jwt_token}
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "make": "Honda",
    "model": "CR-V",
    "year": 2023,
    "price": 32500,
    "mileage": 15000,
    "mpg": 28.5,
    ...
  },
  ...
]
```

---

### Add Car to Favorites 🔒

**POST** `/favorites/{carId}`

Add a car to the current user's favorites.

**Headers:**
```
Authorization: Bearer {jwt_token}
```

**Path Parameters:**
- `carId` (long) - Car ID to favorite

**Response (200 OK):**
```json
{
  "message": "Car added to favorites"
}
```

**Response (400 Bad Request):**
```json
{
  "error": "Car already in favorites"
}
```

---

### Remove from Favorites 🔒

**DELETE** `/favorites/{carId}`

Remove a car from favorites.

**Headers:**
```
Authorization: Bearer {jwt_token}
```

**Path Parameters:**
- `carId` (long) - Car ID to remove

**Response (200 OK):**
```json
{
  "message": "Car removed from favorites"
}
```

**Response (400 Bad Request):**
```json
{
  "error": "Car not in favorites"
}
```

---

### Check if Car is Favorited 🔒

**GET** `/favorites/{carId}/is-favorited`

Check whether a car is in the user's favorites.

**Headers:**
```
Authorization: Bearer {jwt_token}
```

**Path Parameters:**
- `carId` (long) - Car ID to check

**Response (200 OK):**
```json
true
```

or

```json
false
```

---

### Get Favorite Count 🔒

**GET** `/favorites/count`

Get total number of favorited cars.

**Headers:**
```
Authorization: Bearer {jwt_token}
```

**Response (200 OK):**
```json
7
```

---

## Error Handling

### Standard Error Response Format

```json
{
  "error": "Error message",
  "status": 400,
  "timestamp": "2026-05-13T10:30:00Z"
}
```

### Common Status Codes

| Code | Meaning |
|------|---------|
| 200 | ✅ Success |
| 201 | ✅ Created |
| 400 | ❌ Bad Request (Invalid input) |
| 401 | ❌ Unauthorized (No/invalid token) |
| 403 | ❌ Forbidden (Insufficient permissions) |
| 404 | ❌ Not Found |
| 409 | ❌ Conflict (Duplicate entry) |
| 500 | ❌ Internal Server Error |

---

## Rate Limiting

- Default: 100 requests per minute per IP
- Header: `X-RateLimit-Remaining`

---

## CORS Configuration

**Allowed Origins:**
- http://localhost:3000 (Frontend)
- http://localhost:8080

**Allowed Methods:** GET, POST, PUT, DELETE, OPTIONS

**Allowed Headers:** * (all)

---

## Example Usage - cURL

### Register User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "SecurePass123!",
    "firstName": "John",
    "lastName": "Doe"
  }'
```

### Get All Cars
```bash
curl http://localhost:8080/api/cars
```

### Search Cars
```bash
curl "http://localhost:8080/api/cars/search?make=Honda&model=CR-V"
```

### Generate Recommendations
```bash
curl -X POST http://localhost:8080/api/recommendations/generate \
  -H "Authorization: Bearer {jwt_token}" \
  -H "Content-Type: application/json" \
  -d '{
    "minBudget": 25000,
    "maxBudget": 50000,
    "minMpg": 25,
    "maxMileage": 100000,
    "minReliabilityScore": 80
  }'
```

---

## WebSocket Events (Future)

- `recommendations.generated` - When recommendations are ready
- `favorite.added` - When car is favorited
- `favorite.removed` - When car is unfavorited

---

**API Documentation Version:** 1.0.0  
**Last Updated:** May 13, 2026
