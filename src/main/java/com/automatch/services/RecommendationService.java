package com.automatch.services;

import com.automatch.models.*;
import com.automatch.repositories.*;
import com.automatch.dto.RecommendationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * RecommendationService - Core AI recommendation engine.
 * Refactored from the Banking System's AccountService pattern.
 * 
 * Calculates match scores (0-100) based on weighted factors:
 * - Budget match (weight: 20%)
 * - Fuel Economy/MPG (weight: 15%)
 * - Reliability (weight: 25%)
 * - Maintenance Cost (weight: 20%)
 * - Mileage (weight: 20%)
 * 
 * Generates human-readable explanations for recommendations.
 */
@Service
@Transactional
public class RecommendationService {
    
    private final RecommendationRepository recommendationRepository;
    private final CarRepository carRepository;
    private final SearchPreferenceRepository searchPreferenceRepository;
    private final UserRepository userRepository;
    
    public RecommendationService(RecommendationRepository recommendationRepository,
                                CarRepository carRepository,
                                SearchPreferenceRepository searchPreferenceRepository,
                                UserRepository userRepository) {
        this.recommendationRepository = recommendationRepository;
        this.carRepository = carRepository;
        this.searchPreferenceRepository = searchPreferenceRepository;
        this.userRepository = userRepository;
    }
    
    /**
     * Generates recommendations for a user based on their search preferences.
     * This is the main AI logic.
     *
     * @param user the user
     * @param preference the user's search preferences
     * @return list of recommendations sorted by match score
     */
    public List<Recommendation> generateRecommendations(User user, SearchPreference preference) {
        // Normalize weights to ensure they sum to 100
        preference.normalizeWeights();
        
        // Get all available cars
        List<Car> availableCars = carRepository.findByAvailableTrue();
        
        // Calculate scores for each car
        List<Recommendation> recommendations = availableCars.stream()
                .map(car -> calculateRecommendationScore(user, car, preference))
                .filter(r -> r.getMatchScore() > 0) // Only include cars with some match
                .sorted(Comparator.comparingInt(Recommendation::getMatchScore).reversed())
                .collect(Collectors.toList());
        
        // Assign ranks
        for (int i = 0; i < recommendations.size(); i++) {
            recommendations.get(i).setRank(i + 1);
            recommendationRepository.save(recommendations.get(i));
        }
        
        return recommendations;
    }
    
    /**
     * Calculates the recommendation score for a specific car against user preferences.
     * Returns a Recommendation object with component scores and explanation.
     *
     * @param user the user
     * @param car the car to evaluate
     * @param preference the user's preferences
     * @return Recommendation object with scores and explanation
     */
    private Recommendation calculateRecommendationScore(User user, Car car, SearchPreference preference) {
        // Calculate component scores (0-100 each)
        int budgetScore = calculateBudgetScore(car, preference);
        int mpgScore = calculateMpgScore(car, preference);
        int reliabilityScore = calculateReliabilityScore(car, preference);
        int maintenanceScore = calculateMaintenanceScore(car, preference);
        int mileageScore = calculateMileageScore(car, preference);
        
        // Validate that scores are within range
        budgetScore = Math.max(0, Math.min(100, budgetScore));
        mpgScore = Math.max(0, Math.min(100, mpgScore));
        reliabilityScore = Math.max(0, Math.min(100, reliabilityScore));
        maintenanceScore = Math.max(0, Math.min(100, maintenanceScore));
        mileageScore = Math.max(0, Math.min(100, mileageScore));
        
        // Calculate weighted overall match score
        int matchScore = calculateWeightedScore(
                budgetScore, mpgScore, reliabilityScore, maintenanceScore, mileageScore,
                preference.getBudgetWeight(), preference.getMpgWeight(), 
                preference.getReliabilityWeight(), preference.getMaintenanceWeight(),
                preference.getMileageWeight()
        );
        
        // Generate explanation
        String explanation = generateExplanation(car, preference, budgetScore, mpgScore, 
                reliabilityScore, maintenanceScore, mileageScore);
        
        Recommendation recommendation = new Recommendation();
        recommendation.setUser(user);
        recommendation.setCar(car);
        recommendation.setSearchPreference(preference);
        recommendation.setMatchScore(matchScore);
        recommendation.setBudgetScore(budgetScore);
        recommendation.setMpgScore(mpgScore);
        recommendation.setReliabilityScore(reliabilityScore);
        recommendation.setMaintenanceScore(maintenanceScore);
        recommendation.setMileageScore(mileageScore);
        recommendation.setExplanation(explanation);
        return recommendation;
    }
    
    /**
     * Calculates budget match score (0-100).
     * 100 = price exactly in middle of budget range
     * 0 = price outside acceptable range
     */
    private int calculateBudgetScore(Car car, SearchPreference preference) {
        BigDecimal price = car.getPrice();
        BigDecimal minBudget = preference.getMinBudget();
        BigDecimal maxBudget = preference.getMaxBudget();
        
        // If price is outside budget range, return low score
        if (price.compareTo(minBudget) < 0 || price.compareTo(maxBudget) > 0) {
            return 20; // Still give some credit for being considered
        }
        
        // Calculate distance from optimal price (middle of range)
        BigDecimal budgetRange = maxBudget.subtract(minBudget);
        BigDecimal midpoint = minBudget.add(budgetRange.divide(new BigDecimal(2)));
        BigDecimal distance = price.subtract(midpoint).abs();
        
        // Score decreases as distance increases
        double ratio = distance.divide(budgetRange, 4, java.math.RoundingMode.HALF_UP).doubleValue();
        return (int) (100 * (1 - ratio * 0.5)); // Max 50% reduction
    }
    
    /**
     * Calculates MPG (fuel economy) match score (0-100).
     * 100 = meets or exceeds minimum MPG requirement
     * Decreases below minimum
     */
    private int calculateMpgScore(Car car, SearchPreference preference) {
        Double carMpg = car.getMpg();
        Integer minMpg = preference.getMinMpg();
        
        if (carMpg >= minMpg) {
            return 100; // Meets requirement
        } else if (carMpg >= minMpg * 0.8) {
            return 70; // Close to requirement
        } else {
            return Math.max(0, (int) ((carMpg / minMpg) * 100));
        }
    }
    
    /**
     * Calculates reliability match score (0-100).
     * Based on car's built-in reliability score and whether it meets user minimum.
     */
    private int calculateReliabilityScore(Car car, SearchPreference preference) {
        Integer carReliability = car.getReliabilityScore();
        Integer minReliability = preference.getMinReliabilityScore();
        
        if (carReliability >= minReliability) {
            // Car meets requirement - scale up from minimum to 100
            int exceedance = carReliability - minReliability;
            return Math.min(100, minReliability + (exceedance / 2));
        } else {
            // Car doesn't meet requirement
            return Math.max(0, (carReliability * 100) / minReliability);
        }
    }
    
    /**
     * Calculates maintenance cost match score (0-100).
     * Lower maintenance costs score higher.
     */
    private int calculateMaintenanceScore(Car car, SearchPreference preference) {
        BigDecimal carMaintenance = car.getAnnualMaintenanceCost();
        BigDecimal maxMaintenance = preference.getMaxAnnualMaintenance();
        
        if (maxMaintenance == null || carMaintenance == null) {
            return 75; // Neutral score if data missing
        }
        
        if (carMaintenance.compareTo(maxMaintenance) <= 0) {
            // Within budget
            double ratio = carMaintenance.divide(maxMaintenance, 4, java.math.RoundingMode.HALF_UP).doubleValue();
            return (int) (100 * (1 - ratio * 0.3)); // Max 30% reduction
        } else {
            // Over budget
            return Math.max(20, (int) ((maxMaintenance.divide(carMaintenance, 4, java.math.RoundingMode.HALF_UP).doubleValue()) * 100));
        }
    }
    
    /**
     * Calculates mileage match score (0-100).
     * Lower mileage scores higher, but all cars under max mileage get high scores.
     */
    private int calculateMileageScore(Car car, SearchPreference preference) {
        Long carMileage = car.getMileage();
        Long maxMileage = preference.getMaxMileage();
        
        if (carMileage <= maxMileage) {
            // Within acceptable range
            double ratio = (double) carMileage / maxMileage;
            return (int) (100 * (1 - ratio * 0.3)); // Max 30% reduction based on usage
        } else {
            // Over acceptable mileage
            return Math.max(10, (int) ((maxMileage.doubleValue() / carMileage) * 100));
        }
    }
    
    /**
     * Calculates weighted overall score.
     */
    private int calculateWeightedScore(int budgetScore, int mpgScore, int reliabilityScore,
                                      int maintenanceScore, int mileageScore,
                                      int budgetWeight, int mpgWeight, int reliabilityWeight,
                                      int maintenanceWeight, int mileageWeight) {
        double totalWeight = budgetWeight + mpgWeight + reliabilityWeight + maintenanceWeight + mileageWeight;
        
        double weightedScore = (budgetScore * budgetWeight +
                               mpgScore * mpgWeight +
                               reliabilityScore * reliabilityWeight +
                               maintenanceScore * maintenanceWeight +
                               mileageScore * mileageWeight) / totalWeight;
        
        return (int) weightedScore;
    }
    
    /**
     * Generates a human-readable explanation for why a car was recommended.
     */
    private String generateExplanation(Car car, SearchPreference preference,
                                      int budgetScore, int mpgScore, int reliabilityScore,
                                      int maintenanceScore, int mileageScore) {
        StringBuilder explanation = new StringBuilder();
        
        explanation.append("Why the ").append(car.getFullName()).append(" is recommended:\n\n");
        
        // Budget explanation
        if (budgetScore >= 80) {
            explanation.append("✓ Budget: Excellent price match within your ").append(preference.getMinBudget())
                    .append(" - ").append(preference.getMaxBudget()).append(" range.\n");
        } else if (budgetScore >= 60) {
            explanation.append("✓ Budget: Good price match at $").append(car.getPrice()).append(".\n");
        }
        
        // Fuel economy explanation
        if (mpgScore >= 80) {
            explanation.append("✓ Fuel Economy: Excellent MPG of ").append(String.format("%.1f", car.getMpg()))
                    .append(" exceeds your preferences.\n");
        } else if (mpgScore >= 60) {
            explanation.append("✓ Fuel Economy: Good fuel economy at ").append(String.format("%.1f", car.getMpg())).append(" MPG.\n");
        }
        
        // Reliability explanation
        if (reliabilityScore >= 80) {
            explanation.append("✓ Reliability: Highly reliable with a score of ").append(car.getReliabilityScore())
                    .append("/100.\n");
        } else if (reliabilityScore >= 60) {
            explanation.append("✓ Reliability: Dependable vehicle with good track record.\n");
        }
        
        // Maintenance explanation
        if (maintenanceScore >= 80) {
            explanation.append("✓ Maintenance: Low annual maintenance cost of $")
                    .append(car.getAnnualMaintenanceCost()).append(".\n");
        } else if (maintenanceScore >= 60) {
            explanation.append("✓ Maintenance: Reasonable maintenance costs.\n");
        }
        
        // Mileage explanation
        if (mileageScore >= 80) {
            explanation.append("✓ Condition: Low mileage at ").append(String.format("%,d", car.getMileage()))
                    .append(" miles.\n");
        }
        
        return explanation.toString();
    }
    
    /**
     * Gets top recommendations for a user.
     */
    public List<Recommendation> getTopRecommendations(User user, int limit) {
        List<Recommendation> all = recommendationRepository.findByUserOrderByMatchScoreDesc(user);
        return all.stream().limit(limit).collect(Collectors.toList());
    }
    
    /**
     * Gets all recommendations for a user.
     */
    public List<Recommendation> getRecommendationsForUser(User user) {
        return recommendationRepository.findByUserOrderByMatchScoreDesc(user);
    }
    
    /**
     * Gets a specific recommendation.
     */
    public Optional<Recommendation> getRecommendation(Long id) {
        return recommendationRepository.findById(id);
    }
}
