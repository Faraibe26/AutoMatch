import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { carAPI, favoriteAPI } from '../services/api';
import { Navbar } from '../components/Navbar';
import { CarCard } from '../components/CarCard';
import { SearchBar } from '../components/SearchBar';
import { TrendingUp, Heart, Search, Filter } from 'lucide-react';

export const Dashboard = () => {
  const { user } = useAuth();
  const [cars, setCars] = useState([]);
  const [filteredCars, setFilteredCars] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [favorites, setFavorites] = useState([]);
  const [stats, setStats] = useState({
    totalCars: 0,
    totalFavorites: 0,
    avgPrice: 0,
  });

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    setLoading(true);
    setError(null);
    try {
      const [carsResponse, favoritesResponse] = await Promise.all([
        carAPI.getAllCars(),
        favoriteAPI.getFavorites().catch(() => ({ data: [] })),
      ]);

      const carsData = carsResponse.data;
      setCars(carsData);
      setFilteredCars(carsData);

      const favoritesData = favoritesResponse.data;
      setFavorites(favoritesData.map((fav) => fav.id));

      // Calculate stats
      setStats({
        totalCars: carsData.length,
        totalFavorites: favoritesData.length,
        avgPrice:
          carsData.length > 0
            ? Math.round(
                carsData.reduce((sum, car) => sum + car.price, 0) /
                  carsData.length
              )
            : 0,
      });
    } catch (err) {
      setError('Failed to load dashboard data');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = (searchTerm) => {
    if (!searchTerm) {
      setFilteredCars(cars);
      return;
    }

    const filtered = cars.filter(
      (car) =>
        car.make.toLowerCase().includes(searchTerm.toLowerCase()) ||
        car.model.toLowerCase().includes(searchTerm.toLowerCase()) ||
        car.year.toString().includes(searchTerm)
    );

    setFilteredCars(filtered);
  };

  const toggleFavorite = (carId) => {
    setFavorites((prev) =>
      prev.includes(carId) ? prev.filter((id) => id !== carId) : [...prev, carId]
    );
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-50">
        <Navbar />
        <div className="flex items-center justify-center h-96">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />

      {/* Hero Section */}
      <div className="bg-gradient-to-r from-blue-600 to-indigo-700 text-white py-12">
        <div className="max-w-7xl mx-auto px-4">
          <h1 className="text-4xl font-bold mb-2">Welcome, {user?.firstName}!</h1>
          <p className="text-blue-100">Find your perfect car with AI-powered recommendations</p>
        </div>
      </div>

      {/* Stats Section */}
      <div className="max-w-7xl mx-auto px-4 py-8">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-12">
          {/* Available Cars Stat */}
          <div className="bg-white rounded-lg shadow p-6 hover:shadow-lg transition">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-gray-600 text-sm">Available Cars</p>
                <p className="text-3xl font-bold text-gray-900 mt-1">{stats.totalCars}</p>
              </div>
              <div className="bg-blue-100 p-4 rounded-lg">
                <Search className="w-6 h-6 text-blue-600" />
              </div>
            </div>
          </div>

          {/* Average Price Stat */}
          <div className="bg-white rounded-lg shadow p-6 hover:shadow-lg transition">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-gray-600 text-sm">Average Price</p>
                <p className="text-3xl font-bold text-gray-900 mt-1">
                  ${stats.avgPrice.toLocaleString()}
                </p>
              </div>
              <div className="bg-green-100 p-4 rounded-lg">
                <TrendingUp className="w-6 h-6 text-green-600" />
              </div>
            </div>
          </div>

          {/* Favorites Stat */}
          <div className="bg-white rounded-lg shadow p-6 hover:shadow-lg transition">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-gray-600 text-sm">Saved Favorites</p>
                <p className="text-3xl font-bold text-gray-900 mt-1">{stats.totalFavorites}</p>
              </div>
              <div className="bg-red-100 p-4 rounded-lg">
                <Heart className="w-6 h-6 text-red-600" />
              </div>
            </div>
          </div>
        </div>

        {/* Search and Filter */}
        <div className="bg-white rounded-lg shadow p-6 mb-8">
          <div className="flex flex-col md:flex-row gap-4">
            <SearchBar onSearch={handleSearch} placeholder="Search by make, model, or year..." />
            <button className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-2 rounded-lg flex items-center gap-2 font-medium">
              <Filter className="w-5 h-5" />
              Filters
            </button>
          </div>
        </div>

        {/* Error Message */}
        {error && (
          <div className="bg-red-50 border border-red-200 rounded-lg p-4 mb-8 text-red-700">
            {error}
          </div>
        )}

        {/* Cars Grid */}
        <div>
          <h2 className="text-2xl font-bold text-gray-900 mb-6">Featured Cars</h2>
          {filteredCars.length > 0 ? (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
              {filteredCars.map((car) => (
                <CarCard
                  key={car.id}
                  car={car}
                  isFavorited={favorites.includes(car.id)}
                  onFavoriteToggle={toggleFavorite}
                />
              ))}
            </div>
          ) : (
            <div className="bg-white rounded-lg shadow p-12 text-center">
              <p className="text-gray-600 text-lg">No cars found</p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};
