import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor to add JWT token to requests
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Interceptor to handle token refresh
api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      try {
        // Refresh token logic could go here
        localStorage.removeItem('token');
        window.location.href = '/login';
      } catch (refreshError) {
        return Promise.reject(refreshError);
      }
    }
    return Promise.reject(error);
  }
);

// Auth API calls
export const authAPI = {
  register: (email, password, firstName, lastName) =>
    api.post('/auth/register', { email, password, firstName, lastName }),
  login: (email, password) =>
    api.post('/auth/login', { email, password }),
  verifyToken: () => api.get('/auth/me'),
};

// Car API calls
export const carAPI = {
  getAllCars: () => api.get('/cars'),
  getCarById: (id) => api.get(`/cars/${id}`),
  searchCars: (make, model) => api.get('/cars/search', { params: { make, model } }),
  filterByPrice: (minPrice, maxPrice) =>
    api.get('/cars/filter/price', { params: { minPrice, maxPrice } }),
  filterByEfficiency: (minMpg) =>
    api.get('/cars/filter/efficiency', { params: { minMpg } }),
  filterByReliability: (minReliability) =>
    api.get('/cars/filter/reliability', { params: { minReliability } }),
  filterByYear: (minYear, maxYear) =>
    api.get('/cars/filter/year', { params: { minYear, maxYear } }),
  getPopularCars: () => api.get('/cars/popular'),
};

// Recommendation API calls
export const recommendationAPI = {
  generateRecommendations: (preferences) =>
    api.post('/recommendations/generate', preferences),
  getUserRecommendations: () => api.get('/recommendations/user'),
  getTopRecommendations: (limit = 10) =>
    api.get('/recommendations/top', { params: { limit } }),
  getRecommendationById: (id) => api.get(`/recommendations/${id}`),
};

// Favorite API calls
export const favoriteAPI = {
  getFavorites: () => api.get('/favorites'),
  addFavorite: (carId, notes = '') =>
    api.post(`/favorites/${carId}`, { notes }),
  removeFavorite: (carId) =>
    api.delete(`/favorites/${carId}`),
  isFavorited: (carId) =>
    api.get(`/favorites/${carId}/is-favorited`),
  getFavoritesCount: () => api.get('/favorites/count'),
};

export default api;
