package com.automatch.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * SearchPreference entity representing a user's car search criteria.
 * Refactored from the Banking System's Transaction model.
 * 
 * Stores user preferences for finding recommended vehicles.
 * Used by RecommendationService to find matching cars.
 */
@Entity
@Table(name = "search_preferences")
public class SearchPreference {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    // Budget Preferences
    @NotNull(message = "Minimum budget is required")
    @DecimalMin(value = "0.0")
    private BigDecimal minBudget;
    
    @NotNull(message = "Maximum budget is required")
    @DecimalMin(value = "0.0")
    private BigDecimal maxBudget;
    
    // Performance Preferences
    @Min(0)
    @Max(100)
    private Integer minMpg = 0; // Minimum fuel economy
    
    @Min(0)
    @Max(500000)
    private Long maxMileage = 500000L; // Maximum mileage acceptable
    
    @Min(0)
    @Max(100)
    private Integer minReliabilityScore = 0; // Minimum reliability rating
    
    // Features Preferences
    private String preferredFuelType; // Gasoline, Diesel, Electric, Hybrid, null = any
    
    private String preferredDriveType; // AWD, FWD, RWD, 4WD, null = any
    
    private String preferredBodyType; // SUV, Sedan, Truck, Coupe, etc., null = any
    
    private String preferredBrands; // Comma-separated list: "Honda,Toyota,BMW"
    
    private String excludedBrands; // Comma-separated list of brands to exclude
    
    @DecimalMax(value = "50000.00")
    private BigDecimal maxAnnualMaintenance; // Maximum acceptable annual maintenance
    
    // Preference Weights (0-100, represent importance)
    @Min(0)
    @Max(100)
    private Integer budgetWeight = 20;
    
    @Min(0)
    @Max(100)
    private Integer mpgWeight = 15;
    
    @Min(0)
    @Max(100)
    private Integer reliabilityWeight = 25;
    
    @Min(0)
    @Max(100)
    private Integer maintenanceWeight = 20;
    
    @Min(0)
    @Max(100)
    private Integer mileageWeight = 20;
    
    private String name; // Optional: "Family SUV Search", "Budget Economy", etc.
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public SearchPreference() {}

    public SearchPreference(Long id, User user, BigDecimal minBudget, BigDecimal maxBudget,
                            Integer minMpg, Long maxMileage, Integer minReliabilityScore,
                            String preferredFuelType, String preferredDriveType, String preferredBodyType,
                            String preferredBrands, String excludedBrands, BigDecimal maxAnnualMaintenance,
                            Integer budgetWeight, Integer mpgWeight, Integer reliabilityWeight,
                            Integer maintenanceWeight, Integer mileageWeight, String name,
                            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.minBudget = minBudget;
        this.maxBudget = maxBudget;
        this.minMpg = minMpg;
        this.maxMileage = maxMileage;
        this.minReliabilityScore = minReliabilityScore;
        this.preferredFuelType = preferredFuelType;
        this.preferredDriveType = preferredDriveType;
        this.preferredBodyType = preferredBodyType;
        this.preferredBrands = preferredBrands;
        this.excludedBrands = excludedBrands;
        this.maxAnnualMaintenance = maxAnnualMaintenance;
        this.budgetWeight = budgetWeight;
        this.mpgWeight = mpgWeight;
        this.reliabilityWeight = reliabilityWeight;
        this.maintenanceWeight = maintenanceWeight;
        this.mileageWeight = mileageWeight;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public BigDecimal getMinBudget() {
        return minBudget;
    }

    public void setMinBudget(BigDecimal minBudget) {
        this.minBudget = minBudget;
    }

    public BigDecimal getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(BigDecimal maxBudget) {
        this.maxBudget = maxBudget;
    }

    public Integer getMinMpg() {
        return minMpg;
    }

    public void setMinMpg(Integer minMpg) {
        this.minMpg = minMpg;
    }

    public Long getMaxMileage() {
        return maxMileage;
    }

    public void setMaxMileage(Long maxMileage) {
        this.maxMileage = maxMileage;
    }

    public Integer getMinReliabilityScore() {
        return minReliabilityScore;
    }

    public void setMinReliabilityScore(Integer minReliabilityScore) {
        this.minReliabilityScore = minReliabilityScore;
    }

    public String getPreferredFuelType() {
        return preferredFuelType;
    }

    public void setPreferredFuelType(String preferredFuelType) {
        this.preferredFuelType = preferredFuelType;
    }

    public String getPreferredDriveType() {
        return preferredDriveType;
    }

    public void setPreferredDriveType(String preferredDriveType) {
        this.preferredDriveType = preferredDriveType;
    }

    public String getPreferredBodyType() {
        return preferredBodyType;
    }

    public void setPreferredBodyType(String preferredBodyType) {
        this.preferredBodyType = preferredBodyType;
    }

    public String getPreferredBrands() {
        return preferredBrands;
    }

    public void setPreferredBrands(String preferredBrands) {
        this.preferredBrands = preferredBrands;
    }

    public String getExcludedBrands() {
        return excludedBrands;
    }

    public void setExcludedBrands(String excludedBrands) {
        this.excludedBrands = excludedBrands;
    }

    public BigDecimal getMaxAnnualMaintenance() {
        return maxAnnualMaintenance;
    }

    public void setMaxAnnualMaintenance(BigDecimal maxAnnualMaintenance) {
        this.maxAnnualMaintenance = maxAnnualMaintenance;
    }

    public Integer getBudgetWeight() {
        return budgetWeight;
    }

    public void setBudgetWeight(Integer budgetWeight) {
        this.budgetWeight = budgetWeight;
    }

    public Integer getMpgWeight() {
        return mpgWeight;
    }

    public void setMpgWeight(Integer mpgWeight) {
        this.mpgWeight = mpgWeight;
    }

    public Integer getReliabilityWeight() {
        return reliabilityWeight;
    }

    public void setReliabilityWeight(Integer reliabilityWeight) {
        this.reliabilityWeight = reliabilityWeight;
    }

    public Integer getMaintenanceWeight() {
        return maintenanceWeight;
    }

    public void setMaintenanceWeight(Integer maintenanceWeight) {
        this.maintenanceWeight = maintenanceWeight;
    }

    public Integer getMileageWeight() {
        return mileageWeight;
    }

    public void setMileageWeight(Integer mileageWeight) {
        this.mileageWeight = mileageWeight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    /**
     * Validates that budget range is logical.
     */
    public boolean isBudgetValid() {
        return minBudget != null && maxBudget != null && 
               minBudget.compareTo(maxBudget) <= 0;
    }
    
    /**
     * Gets total weight sum (should be approximately 100).
     */
    public int getTotalWeight() {
        return budgetWeight + mpgWeight + reliabilityWeight + maintenanceWeight + mileageWeight;
    }
    
    /**
     * Normalizes weights to sum to 100.
     */
    public void normalizeWeights() {
        int total = getTotalWeight();
        if (total != 0 && total != 100) {
            double factor = 100.0 / total;
            budgetWeight = (int) (budgetWeight * factor);
            mpgWeight = (int) (mpgWeight * factor);
            reliabilityWeight = (int) (reliabilityWeight * factor);
            maintenanceWeight = (int) (maintenanceWeight * factor);
            mileageWeight = (int) (mileageWeight * factor);
        }
    }
    
    /**
     * Checks if a brand is in the preferred list.
     */
    public boolean isPreferredBrand(String brand) {
        if (preferredBrands == null || preferredBrands.isEmpty()) {
            return true; // No preference = all brands accepted
        }
        return preferredBrands.toLowerCase().contains(brand.toLowerCase());
    }
    
    /**
     * Checks if a brand is in the excluded list.
     */
    public boolean isExcludedBrand(String brand) {
        if (excludedBrands == null || excludedBrands.isEmpty()) {
            return false; // No exclusions
        }
        return excludedBrands.toLowerCase().contains(brand.toLowerCase());
    }
}
