package com.automatch.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * Recommendation entity representing an AI-generated car recommendation.
 * Stores matching scores and explanations for why a car was recommended.
 * 
 * Central entity in the AutoMatch AI system for tracking recommendations.
 */
@Entity
@Table(name = "recommendations")
public class Recommendation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "search_preference_id")
    private SearchPreference searchPreference;
    
    // Recommendation Score (0-100)
    @NotNull(message = "Match score is required")
    @Min(0)
    @Max(100)
    private Integer matchScore;
    
    // Component Scores (0-100 each)
    @Min(0)
    @Max(100)
    private Integer budgetScore = 0;
    
    @Min(0)
    @Max(100)
    private Integer mpgScore = 0;
    
    @Min(0)
    @Max(100)
    private Integer reliabilityScore = 0;
    
    @Min(0)
    @Max(100)
    private Integer maintenanceScore = 0;
    
    @Min(0)
    @Max(100)
    private Integer mileageScore = 0;
    
    // Human-readable explanation
    @Column(columnDefinition = "TEXT")
    private String explanation;
    
    // Ranking
    @Min(1)
    @Max(10)
    private Integer rank = 1;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "recommended_at")
    private LocalDateTime recommendedAt;
    
    // Constructors
    public Recommendation() {}

    public Recommendation(Long id, User user, Car car, SearchPreference searchPreference, Integer matchScore,
                         Integer budgetScore, Integer mpgScore, Integer reliabilityScore,
                         Integer maintenanceScore, Integer mileageScore, String explanation,
                         Integer rank, LocalDateTime createdAt, LocalDateTime recommendedAt) {
        this.id = id;
        this.user = user;
        this.car = car;
        this.searchPreference = searchPreference;
        this.matchScore = matchScore;
        this.budgetScore = budgetScore;
        this.mpgScore = mpgScore;
        this.reliabilityScore = reliabilityScore;
        this.maintenanceScore = maintenanceScore;
        this.mileageScore = mileageScore;
        this.explanation = explanation;
        this.rank = rank;
        this.createdAt = createdAt;
        this.recommendedAt = recommendedAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        recommendedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public SearchPreference getSearchPreference() {
        return searchPreference;
    }

    public void setSearchPreference(SearchPreference searchPreference) {
        this.searchPreference = searchPreference;
    }

    public Integer getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(Integer matchScore) {
        this.matchScore = matchScore;
    }

    public Integer getBudgetScore() {
        return budgetScore;
    }

    public void setBudgetScore(Integer budgetScore) {
        this.budgetScore = budgetScore;
    }

    public Integer getMpgScore() {
        return mpgScore;
    }

    public void setMpgScore(Integer mpgScore) {
        this.mpgScore = mpgScore;
    }

    public Integer getReliabilityScore() {
        return reliabilityScore;
    }

    public void setReliabilityScore(Integer reliabilityScore) {
        this.reliabilityScore = reliabilityScore;
    }

    public Integer getMaintenanceScore() {
        return maintenanceScore;
    }

    public void setMaintenanceScore(Integer maintenanceScore) {
        this.maintenanceScore = maintenanceScore;
    }

    public Integer getMileageScore() {
        return mileageScore;
    }

    public void setMileageScore(Integer mileageScore) {
        this.mileageScore = mileageScore;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getRecommendedAt() {
        return recommendedAt;
    }

    public void setRecommendedAt(LocalDateTime recommendedAt) {
        this.recommendedAt = recommendedAt;
    }
    
    /**
     * Calculates the average component score.
     */
    public Integer getAverageComponentScore() {
        int sum = budgetScore + mpgScore + reliabilityScore + maintenanceScore + mileageScore;
        return sum / 5;
    }
    
    /**
     * Checks if this is a strong recommendation (score >= 75).
     */
    public boolean isStrongRecommendation() {
        return matchScore >= 75;
    }
    
    /**
     * Checks if this is a moderate recommendation (score 50-74).
     */
    public boolean isModerateRecommendation() {
        return matchScore >= 50 && matchScore < 75;
    }
    
    /**
     * Checks if this is a weak recommendation (score < 50).
     */
    public boolean isWeakRecommendation() {
        return matchScore < 50;
    }
    
    /**
     * Gets a recommendation tier label.
     */
    public String getTierLabel() {
        if (isStrongRecommendation()) {
            return "Excellent Match";
        } else if (isModerateRecommendation()) {
            return "Good Match";
        } else {
            return "Consider";
        }
    }
    
    /**
     * Gets a summary string for quick overview.
     */
    public String getSummary() {
        return String.format("%s - %s (%d%% match)",
                car.getFullName(),
                getTierLabel(),
                matchScore);
    }
}
