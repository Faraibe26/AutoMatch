import React from 'react';
import { Link } from 'react-router-dom';
import { Heart, Gauge, Zap, Wrench } from 'lucide-react';

export const CarCard = ({ car, isFavorited, onFavoriteToggle }) => {
  const handleFavoriteClick = (e) => {
    e.preventDefault();
    onFavoriteToggle(car.id);
  };

  return (
    <Link to={`/car/${car.id}`} className="group">
      <div className="bg-white rounded-lg shadow-md hover:shadow-xl transition-shadow overflow-hidden">
        {/* Image Section */}
        <div className="relative bg-gradient-to-br from-blue-100 to-indigo-100 h-48 flex items-center justify-center">
          <span className="text-6xl">🚗</span>
          <button
            onClick={handleFavoriteClick}
            className="absolute top-3 right-3 bg-white rounded-full p-2 hover:bg-red-50 transition"
          >
            <Heart
              className={`w-5 h-5 ${
                isFavorited
                  ? 'fill-red-500 text-red-500'
                  : 'text-gray-400 hover:text-red-500'
              } transition`}
            />
          </button>
        </div>

        {/* Content Section */}
        <div className="p-4">
          {/* Year, Make, Model */}
          <h3 className="text-lg font-bold text-gray-900 group-hover:text-blue-600 transition">
            {car.year} {car.make} {car.model}
          </h3>

          {/* Price */}
          <p className="text-2xl font-bold text-blue-600 mt-2">
            ${car.price.toLocaleString()}
          </p>

          {/* Stats Grid */}
          <div className="grid grid-cols-2 gap-3 mt-4">
            {/* MPG */}
            <div className="bg-blue-50 p-3 rounded-lg">
              <div className="flex items-center gap-1 text-gray-600 text-xs mb-1">
                <Zap className="w-4 h-4" />
                <span>MPG</span>
              </div>
              <p className="font-bold text-gray-900">{car.mpg}</p>
            </div>

            {/* Reliability */}
            <div className="bg-green-50 p-3 rounded-lg">
              <div className="flex items-center gap-1 text-gray-600 text-xs mb-1">
                <Gauge className="w-4 h-4" />
                <span>Reliability</span>
              </div>
              <p className="font-bold text-gray-900">{car.reliabilityScore}/100</p>
            </div>

            {/* Mileage */}
            <div className="bg-purple-50 p-3 rounded-lg">
              <div className="text-gray-600 text-xs mb-1">Mileage</div>
              <p className="font-bold text-gray-900">{car.mileage.toLocaleString()} mi</p>
            </div>

            {/* Maintenance */}
            <div className="bg-orange-50 p-3 rounded-lg">
              <div className="flex items-center gap-1 text-gray-600 text-xs mb-1">
                <Wrench className="w-4 h-4" />
                <span>Maintenance</span>
              </div>
              <p className="font-bold text-gray-900">${car.maintenanceCost}/yr</p>
            </div>
          </div>

          {/* Description */}
          {car.description && (
            <p className="text-sm text-gray-600 mt-3 line-clamp-2">
              {car.description}
            </p>
          )}

          {/* View Details Link */}
          <button className="w-full mt-4 bg-blue-600 hover:bg-blue-700 text-white font-medium py-2 px-4 rounded-lg transition duration-200">
            View Details
          </button>
        </div>
      </div>
    </Link>
  );
};
