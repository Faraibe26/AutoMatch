import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { Menu, X, Heart, LogOut, Home, Zap } from 'lucide-react';

export const Navbar = () => {
  const { logout } = useAuth();
  const navigate = useNavigate();
  const [isOpen, setIsOpen] = useState(false);

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="bg-white shadow-md sticky top-0 z-50">
      <div className="max-w-7xl mx-auto px-4">
        <div className="flex justify-between items-center h-16">
          {/* Logo */}
          <Link to="/dashboard" className="flex items-center gap-2">
            <span className="text-2xl">🚗</span>
            <span className="text-xl font-bold text-blue-600">AutoMatch AI</span>
          </Link>

          {/* Desktop Menu */}
          <div className="hidden md:flex items-center gap-8">
            <Link
              to="/dashboard"
              className="flex items-center gap-1 text-gray-700 hover:text-blue-600 transition"
            >
              <Home className="w-4 h-4" />
              Dashboard
            </Link>
            <Link
              to="/recommendations"
              className="flex items-center gap-1 text-gray-700 hover:text-blue-600 transition"
            >
              <Zap className="w-4 h-4" />
              Recommendations
            </Link>
            <Link
              to="/favorites"
              className="flex items-center gap-1 text-gray-700 hover:text-blue-600 transition"
            >
              <Heart className="w-4 h-4" />
              Favorites
            </Link>
            <button
              onClick={handleLogout}
              className="flex items-center gap-1 text-gray-700 hover:text-red-600 transition"
            >
              <LogOut className="w-4 h-4" />
              Logout
            </button>
          </div>

          {/* Mobile Menu Button */}
          <button
            className="md:hidden"
            onClick={() => setIsOpen(!isOpen)}
          >
            {isOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
          </button>
        </div>

        {/* Mobile Menu */}
        {isOpen && (
          <div className="md:hidden border-t border-gray-200 py-4 space-y-2">
            <Link
              to="/dashboard"
              className="block px-4 py-2 text-gray-700 hover:bg-blue-50 rounded-lg"
              onClick={() => setIsOpen(false)}
            >
              Dashboard
            </Link>
            <Link
              to="/recommendations"
              className="block px-4 py-2 text-gray-700 hover:bg-blue-50 rounded-lg"
              onClick={() => setIsOpen(false)}
            >
              Recommendations
            </Link>
            <Link
              to="/favorites"
              className="block px-4 py-2 text-gray-700 hover:bg-blue-50 rounded-lg"
              onClick={() => setIsOpen(false)}
            >
              Favorites
            </Link>
            <button
              onClick={() => {
                handleLogout();
                setIsOpen(false);
              }}
              className="block w-full text-left px-4 py-2 text-gray-700 hover:bg-red-50 rounded-lg text-red-600"
            >
              Logout
            </button>
          </div>
        )}
      </div>
    </nav>
  );
};
