import React, { useState, useEffect } from 'react';
import { Navbar } from '../components/Navbar';
import { CarCard } from '../components/CarCard';
import { favoriteAPI } from '../services/api';
import { Heart } from 'lucide-react';

export const Favorites = () => {
  const [favorites, setFavorites] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchFavorites();
  }, []);

  const fetchFavorites = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await favoriteAPI.getFavorites();
      setFavorites(response.data);
    } catch (err) {
      setError('Failed to load favorites');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleRemoveFavorite = async (carId) => {
    try {
      await favoriteAPI.removeFavorite(carId);
      setFavorites((prev) => prev.filter((fav) => fav.id !== carId));
    } catch (err) {
      console.error('Failed to remove favorite:', err);
    }
  };

  const handleToggleFavorite = (carId) => {
    const isFavorited = favorites.some((fav) => fav.id === carId);
    if (isFavorited) {
      handleRemoveFavorite(carId);
    }
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
      <div className="bg-gradient-to-r from-red-600 to-pink-600 text-white py-12">
        <div className="max-w-7xl mx-auto px-4">
          <div className="flex items-center gap-3 mb-2">
            <Heart className="w-8 h-8" />
            <h1 className="text-4xl font-bold">Saved Favorites</h1>
          </div>
          <p className="text-red-100">Your collection of favorite cars</p>
        </div>
      </div>

      <div className="max-w-7xl mx-auto px-4 py-12">
        {error && (
          <div className="bg-red-50 border border-red-200 rounded-lg p-4 mb-8 text-red-700">
            {error}
          </div>
        )}

        {favorites.length > 0 ? (
          <div>
            <div className="mb-8">
              <h2 className="text-2xl font-bold text-gray-900">
                {favorites.length} Saved {favorites.length === 1 ? 'Car' : 'Cars'}
              </h2>
            </div>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
              {favorites.map((car) => (
                <CarCard
                  key={car.id}
                  car={car}
                  isFavorited={true}
                  onFavoriteToggle={() => handleToggleFavorite(car.id)}
                />
              ))}
            </div>
          </div>
        ) : (
          <div className="bg-white rounded-lg shadow p-12 text-center">
            <Heart className="w-12 h-12 text-gray-400 mx-auto mb-4" />
            <p className="text-gray-600 text-lg mb-4">No saved favorites yet</p>
            <p className="text-gray-500 mb-6">
              Start exploring cars and add your favorites to this collection
            </p>
            <a
              href="/dashboard"
              className="inline-block bg-red-600 hover:bg-red-700 text-white font-medium py-2 px-6 rounded-lg transition duration-200"
            >
              Browse Cars
            </a>
          </div>
        )}
      </div>
    </div>
  );
};
