# AutoMatch AI - Frontend Documentation

## Overview

The AutoMatch AI frontend is a modern React application built with Tailwind CSS that provides users with an intuitive interface to browse cars, receive AI-powered recommendations, and manage their favorite vehicles.

## Technology Stack

- **React 18** - UI library
- **React Router v6** - Client-side routing
- **Tailwind CSS** - Utility-first CSS framework
- **Axios** - HTTP client for API calls
- **Lucide React** - Icon library
- **Context API** - State management for authentication

## Project Structure

```
frontend/
├── src/
│   ├── components/
│   │   ├── Navbar.js              # Navigation bar component
│   │   ├── CarCard.js             # Car display card
│   │   ├── SearchBar.js           # Search input component
│   │   └── ProtectedRoute.js      # Route protection wrapper
│   ├── context/
│   │   └── AuthContext.js         # Authentication state management
│   ├── pages/
│   │   ├── Login.js               # Login page
│   │   ├── Register.js            # Registration page
│   │   ├── Dashboard.js           # Main dashboard
│   │   ├── Recommendations.js     # AI recommendations page
│   │   └── Favorites.js           # Favorites management page
│   ├── services/
│   │   └── api.js                 # API client with Axios
│   ├── App.js                     # Main app component with routing
│   ├── index.js                   # React entry point
│   └── index.css                  # Tailwind CSS configuration
├── public/
│   └── index.html
├── .env                           # Environment variables
├── tailwind.config.js             # Tailwind configuration
├── postcss.config.js              # PostCSS configuration
├── package.json                   # Dependencies
└── README.md
```

## Installation & Setup

### Prerequisites
- Node.js (v14 or higher)
- npm or yarn

### Steps

1. **Navigate to frontend directory:**
   ```bash
   cd frontend
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Create .env file:**
   The `.env` file is already configured. Update if needed:
   ```
   REACT_APP_API_URL=http://localhost:8080/api
   REACT_APP_ENV=development
   ```

4. **Start the development server:**
   ```bash
   npm start
   ```

   The application will open at `http://localhost:3000`

## Key Features

### Authentication
- **Login/Register** - User authentication with JWT tokens
- **Token Management** - Automatic token storage and refresh
- **Protected Routes** - Role-based route protection

### Dashboard
- **Car Browsing** - View all available cars
- **Search & Filter** - Find cars by make, model, year, and price
- **Statistics** - View total cars, average price, and saved favorites
- **Car Cards** - Display car details with quick favorite toggle

### Recommendations
- **AI Recommendations** - Generate personalized car matches
- **Preference Configuration** - Set budget, MPG, reliability, and maintenance preferences
- **Weighted Scoring** - View component scores and overall match percentage
- **Detailed Explanations** - Understand why each car was recommended

### Favorites
- **Save Favorites** - Add cars to your favorites
- **Manage Collection** - View and remove favorite cars
- **Quick Access** - Easy navigation to your saved cars

## Component Documentation

### Login Component (`pages/Login.js`)
Handles user authentication with email and password.

**Props:** None (uses AuthContext)

**Features:**
- Email and password input fields
- Error message display
- Loading state handling
- Link to registration page

### Register Component (`pages/Register.js`)
User registration with form validation.

**Props:** None (uses AuthContext)

**Features:**
- First name, last name, email, password inputs
- Password confirmation
- Form validation
- Link to login page

### Dashboard Component (`pages/Dashboard.js`)
Main application dashboard showing cars and statistics.

**Props:** None (uses AuthContext and custom hooks)

**Features:**
- Statistics cards (total cars, average price, saved favorites)
- Car search functionality
- Car grid with CarCard components
- Favorite toggle functionality
- Loading and error states

### Recommendations Component (`pages/Recommendations.js`)
AI-powered car recommendation system.

**Props:** None (uses API context)

**Features:**
- Preference configuration panel
- Budget, MPG, reliability, maintenance, and mileage inputs
- Weighted preference settings
- Recommendation generation
- Score breakdown for each match
- Color-coded match percentages

### Favorites Component (`pages/Favorites.js`)
Display and manage saved favorite cars.

**Props:** None (uses API context)

**Features:**
- List of saved favorite cars
- Remove from favorites functionality
- Empty state messaging
- Link to browse cars

### Navbar Component (`components/Navbar.js`)
Navigation component with menu and logout.

**Props:**
- None (uses AuthContext)

**Features:**
- Logo and branding
- Navigation links (Dashboard, Recommendations, Favorites)
- Logout functionality
- Mobile-responsive menu
- Sticky positioning

### CarCard Component (`components/CarCard.js`)
Reusable car display card.

**Props:**
- `car` (object) - Car data
- `isFavorited` (boolean) - Favorite status
- `onFavoriteToggle` (function) - Favorite toggle callback

**Features:**
- Car image placeholder
- Favorite button with heart icon
- Car details (year, make, model)
- Price display
- Stats grid (MPG, reliability, mileage, maintenance)
- Description preview
- Link to car details page

### SearchBar Component (`components/SearchBar.js`)
Search input with clear button.

**Props:**
- `onSearch` (function) - Search callback
- `placeholder` (string, optional) - Input placeholder

**Features:**
- Real-time search input
- Clear button
- Search icon
- Focus states

### ProtectedRoute Component (`components/ProtectedRoute.js`)
Route wrapper for authentication.

**Props:**
- `children` (React Node) - Component to protect

**Features:**
- Authentication check
- Redirect to login if not authenticated
- Loading state display

## API Integration

### AuthContext (`context/AuthContext.js`)
Manages authentication state and provides auth methods.

**Methods:**
- `register(email, password, firstName, lastName)` - Create new account
- `login(email, password)` - Authenticate user
- `logout()` - Clear authentication

**State:**
- `user` - Current logged-in user object
- `isAuthenticated` - Boolean indicating auth status
- `loading` - Loading state for auth operations
- `error` - Error messages

### API Client (`services/api.js`)
Axios instance with JWT interceptors.

**Features:**
- Automatic token injection in headers
- Request/response interceptors
- Token refresh logic
- Grouped API methods for each resource

**API Methods:**
- `authAPI` - Auth endpoints (register, login, verify)
- `carAPI` - Car endpoints (get all, search, filter)
- `recommendationAPI` - Recommendation endpoints (generate, get)
- `favoriteAPI` - Favorite endpoints (get, add, remove)

## Styling

### Tailwind CSS Configuration

The project uses Tailwind CSS for styling with custom configuration in `tailwind.config.js`:

**Custom Colors:**
- `primary` - #2563eb (Blue)
- `secondary` - #1e40af (Dark Blue)
- `accent` - #0ea5e9 (Cyan)
- `success` - #10b981 (Green)
- `danger` - #ef4444 (Red)
- `warning` - #f59e0b (Amber)

**Responsive Breakpoints:**
- `sm` - 640px
- `md` - 768px
- `lg` - 1024px
- `xl` - 1280px
- `2xl` - 1536px

## Environment Variables

Create a `.env` file in the `frontend` directory:

```env
REACT_APP_API_URL=http://localhost:8080/api
REACT_APP_ENV=development
```

### Available Variables:
- `REACT_APP_API_URL` - Backend API base URL
- `REACT_APP_ENV` - Environment (development/production)

## Development Guide

### Running Development Server
```bash
npm start
```

### Building for Production
```bash
npm run build
```

### Running Tests
```bash
npm test
```

### Code Quality
```bash
npm run eject  # Careful! This is irreversible
```

## Features Roadmap

### Phase 1 (Current) ✅
- [x] Authentication (Login/Register)
- [x] Dashboard with car listing
- [x] Search and basic filtering
- [x] Favorite cars management
- [x] AI recommendations with preferences
- [x] Responsive design

### Phase 2 (Next)
- [ ] Car detail page with full specifications
- [ ] Car comparison tool (side-by-side)
- [ ] Advanced filters (make, year, MPG range)
- [ ] User profile management
- [ ] Recommendation history

### Phase 3 (Future)
- [ ] Saved search preferences
- [ ] Email notifications
- [ ] Reviews and ratings
- [ ] Dealer integration
- [ ] Test drive booking
- [ ] Payment integration

## Common Issues & Troubleshooting

### Backend Connection Issues
If you see CORS errors or connection timeouts:

1. Ensure backend is running on `http://localhost:8080`
2. Check `.env` file has correct `REACT_APP_API_URL`
3. Verify backend CORS configuration includes `http://localhost:3000`

### Authentication Issues
- Clear browser cache and localStorage
- Check token expiration in browser console
- Verify email/password in test account

### Styling Not Applied
- Clear browser cache
- Rebuild Tailwind CSS: `npm run build`
- Restart development server

## Performance Tips

1. **Code Splitting** - Use React.lazy() for large components
2. **Memoization** - Use React.memo() to prevent unnecessary re-renders
3. **Image Optimization** - Optimize car images for web
4. **API Caching** - Implement query caching for better UX
5. **Bundle Size** - Monitor bundle size with webpack-bundle-analyzer

## Deployment

### Vercel (Recommended)
```bash
npm i -g vercel
vercel
```

### Netlify
```bash
npm run build
# Drag and drop 'build' folder to Netlify
```

### Docker
```dockerfile
FROM node:18-alpine
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build
EXPOSE 3000
CMD ["npm", "start"]
```

## Contributing

1. Create a new branch for your feature
2. Make your changes
3. Test thoroughly
4. Submit a pull request with description

## License

This project is part of AutoMatch AI - All rights reserved

## Support

For issues or questions:
- Check existing documentation
- Review console errors
- Check backend API logs
- Create an issue with detailed description

---

**Last Updated:** May 13, 2026
**Version:** 1.0.0
