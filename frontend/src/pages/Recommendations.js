import React, { useState } from 'react';
import { Navbar } from '../components/Navbar';
import { recommendationAPI } from '../services/api';
import { Zap, AlertCircle } from 'lucide-react';

export const Recommendations = () => {
  const [preferences, setPreferences] = useState({
    minBudget: 20000,
    maxBudget: 60000,
    minMpg: 20,
    minReliability: 75,
    maxMaintenanceCost: 500,
    minMileage: 0,
    maxMileage: 200000,
    budgetWeight: 20,
    mpgWeight: 15,
    reliabilityWeight: 25,
    maintenanceWeight: 20,
    mileageWeight: 20,
  });
  const [recommendations, setRecommendations] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setPreferences((prev) => ({
      ...prev,
      [name]: name.includes('Weight') ? parseInt(value) : parseInt(value),
    }));
  };

  const handleGenerateRecommendations = async () => {
    setLoading(true);
    setError(null);

    try {
      const response = await recommendationAPI.generateRecommendations(preferences);
      setRecommendations(response.data);
    } catch (err) {
      setError('Failed to generate recommendations. Please try again.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const getScoreColor = (score) => {
    if (score >= 80) return 'text-green-600';
    if (score >= 60) return 'text-yellow-600';
    return 'text-red-600';
  };

  const getScoreBgColor = (score) => {
    if (score >= 80) return 'bg-green-50';
    if (score >= 60) return 'bg-yellow-50';
    return 'bg-red-50';
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />

      {/* Hero Section */}
      <div className="bg-gradient-to-r from-purple-600 to-pink-600 text-white py-12">
        <div className="max-w-7xl mx-auto px-4">
          <div className="flex items-center gap-3 mb-2">
            <Zap className="w-8 h-8" />
            <h1 className="text-4xl font-bold">AI Recommendations</h1>
          </div>
          <p className="text-purple-100">
            Set your preferences and let our AI find the perfect car for you
          </p>
        </div>
      </div>

      <div className="max-w-7xl mx-auto px-4 py-8">
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* Preferences Panel */}
          <div className="lg:col-span-1">
            <div className="bg-white rounded-lg shadow p-6 sticky top-20">
              <h2 className="text-xl font-bold text-gray-900 mb-6">Your Preferences</h2>

              {/* Budget Range */}
              <div className="mb-6">
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Budget Range
                </label>
                <div className="space-y-2">
                  <input
                    type="number"
                    name="minBudget"
                    value={preferences.minBudget}
                    onChange={handleInputChange}
                    placeholder="Min budget"
                    className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent outline-none"
                  />
                  <input
                    type="number"
                    name="maxBudget"
                    value={preferences.maxBudget}
                    onChange={handleInputChange}
                    placeholder="Max budget"
                    className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent outline-none"
                  />
                </div>
              </div>

              {/* MPG */}
              <div className="mb-6">
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Minimum MPG
                </label>
                <input
                  type="number"
                  name="minMpg"
                  value={preferences.minMpg}
                  onChange={handleInputChange}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent outline-none"
                />
              </div>

              {/* Reliability */}
              <div className="mb-6">
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Minimum Reliability Score
                </label>
                <input
                  type="number"
                  name="minReliability"
                  value={preferences.minReliability}
                  onChange={handleInputChange}
                  min="0"
                  max="100"
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent outline-none"
                />
              </div>

              {/* Maintenance Cost */}
              <div className="mb-6">
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Max Annual Maintenance
                </label>
                <input
                  type="number"
                  name="maxMaintenanceCost"
                  value={preferences.maxMaintenanceCost}
                  onChange={handleInputChange}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent outline-none"
                />
              </div>

              {/* Mileage Range */}
              <div className="mb-6">
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Mileage Range
                </label>
                <div className="space-y-2">
                  <input
                    type="number"
                    name="minMileage"
                    value={preferences.minMileage}
                    onChange={handleInputChange}
                    placeholder="Min mileage"
                    className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent outline-none"
                  />
                  <input
                    type="number"
                    name="maxMileage"
                    value={preferences.maxMileage}
                    onChange={handleInputChange}
                    placeholder="Max mileage"
                    className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent outline-none"
                  />
                </div>
              </div>

              {/* Generate Button */}
              <button
                onClick={handleGenerateRecommendations}
                disabled={loading}
                className="w-full bg-purple-600 hover:bg-purple-700 disabled:bg-purple-400 text-white font-medium py-2 px-4 rounded-lg transition duration-200"
              >
                {loading ? 'Generating...' : 'Generate Recommendations'}
              </button>
            </div>
          </div>

          {/* Results Panel */}
          <div className="lg:col-span-2">
            {error && (
              <div className="bg-red-50 border border-red-200 rounded-lg p-4 mb-8 flex items-start gap-3">
                <AlertCircle className="w-5 h-5 text-red-600 mt-0.5 flex-shrink-0" />
                <p className="text-red-700">{error}</p>
              </div>
            )}

            {recommendations.length > 0 ? (
              <div className="space-y-4">
                <h2 className="text-2xl font-bold text-gray-900 mb-6">
                  Top Matches ({recommendations.length})
                </h2>
                {recommendations.map((rec, index) => (
                  <div
                    key={rec.id}
                    className={`${getScoreBgColor(
                      rec.matchScore
                    )} border border-gray-200 rounded-lg p-6 hover:shadow-lg transition`}
                  >
                    <div className="flex items-start justify-between mb-4">
                      <div>
                        <div className="text-sm font-medium text-gray-600 mb-1">
                          Match #{index + 1}
                        </div>
                        <h3 className="text-xl font-bold text-gray-900">
                          {rec.car.year} {rec.car.make} {rec.car.model}
                        </h3>
                      </div>
                      <div className={`text-3xl font-bold ${getScoreColor(rec.matchScore)}`}>
                        {rec.matchScore}%
                      </div>
                    </div>

                    {/* Score Breakdown */}
                    <div className="grid grid-cols-2 md:grid-cols-5 gap-3 mb-4">
                      <div className="bg-white p-3 rounded">
                        <div className="text-xs text-gray-600 mb-1">Budget</div>
                        <div className="font-bold text-gray-900">{rec.budgetScore}</div>
                      </div>
                      <div className="bg-white p-3 rounded">
                        <div className="text-xs text-gray-600 mb-1">MPG</div>
                        <div className="font-bold text-gray-900">{rec.mpgScore}</div>
                      </div>
                      <div className="bg-white p-3 rounded">
                        <div className="text-xs text-gray-600 mb-1">Reliability</div>
                        <div className="font-bold text-gray-900">{rec.reliabilityScore}</div>
                      </div>
                      <div className="bg-white p-3 rounded">
                        <div className="text-xs text-gray-600 mb-1">Maintenance</div>
                        <div className="font-bold text-gray-900">{rec.maintenanceScore}</div>
                      </div>
                      <div className="bg-white p-3 rounded">
                        <div className="text-xs text-gray-600 mb-1">Mileage</div>
                        <div className="font-bold text-gray-900">{rec.mileageScore}</div>
                      </div>
                    </div>

                    {/* Car Details */}
                    <div className="grid grid-cols-2 md:grid-cols-4 gap-3 mb-4 bg-white p-4 rounded">
                      <div>
                        <div className="text-xs text-gray-600">Price</div>
                        <div className="font-bold text-gray-900">
                          ${rec.car.price.toLocaleString()}
                        </div>
                      </div>
                      <div>
                        <div className="text-xs text-gray-600">MPG</div>
                        <div className="font-bold text-gray-900">{rec.car.mpg}</div>
                      </div>
                      <div>
                        <div className="text-xs text-gray-600">Reliability</div>
                        <div className="font-bold text-gray-900">{rec.car.reliabilityScore}/100</div>
                      </div>
                      <div>
                        <div className="text-xs text-gray-600">Maintenance</div>
                        <div className="font-bold text-gray-900">${rec.car.maintenanceCost}/yr</div>
                      </div>
                    </div>

                    {/* Explanation */}
                    {rec.explanation && (
                      <p className="text-sm text-gray-700 italic">{rec.explanation}</p>
                    )}

                    <button className="mt-4 w-full bg-purple-600 hover:bg-purple-700 text-white font-medium py-2 px-4 rounded-lg transition duration-200">
                      View Details
                    </button>
                  </div>
                ))}
              </div>
            ) : (
              <div className="bg-white rounded-lg shadow p-12 text-center">
                <Zap className="w-12 h-12 text-gray-400 mx-auto mb-4" />
                <p className="text-gray-600 text-lg">
                  Click "Generate Recommendations" to see AI-powered matches
                </p>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};
