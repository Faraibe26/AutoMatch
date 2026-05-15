package com.automatch.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * SearchPreferenceRequest DTO for creating/updating search preferences.
 */
public class SearchPreferenceRequest {
    
    @NotNull(message = "Minimum budget is required")
    @DecimalMin(value = "0.0")
    private BigDecimal minBudget;
    
    @NotNull(message = "Maximum budget is required")
    @DecimalMin(value = "0.0")
    private BigDecimal maxBudget;
    
    @Min(0)
    @Max(100)
    private Integer minMpg = 0;
    
    @Min(0)
    @Max(500000)
    private Long maxMileage = 500000L;
    
    @Min(0)
    @Max(100)
    private Integer minReliabilityScore = 0;
    
    private String preferredFuelType;
    private String preferredDriveType;
    private String preferredBodyType;
    private String preferredBrands;
    private String excludedBrands;
    private BigDecimal maxAnnualMaintenance;
    
    private Integer budgetWeight = 20;
    private Integer mpgWeight = 15;
    private Integer reliabilityWeight = 25;
    private Integer maintenanceWeight = 20;
    private Integer mileageWeight = 20;
    
    private String name;

    // Constructors
    public SearchPreferenceRequest() {}

    public SearchPreferenceRequest(BigDecimal minBudget, BigDecimal maxBudget, Integer minMpg,
                                   Long maxMileage, Integer minReliabilityScore, String preferredFuelType,
                                   String preferredDriveType, String preferredBodyType, String preferredBrands,
                                   String excludedBrands, BigDecimal maxAnnualMaintenance, Integer budgetWeight,
                                   Integer mpgWeight, Integer reliabilityWeight, Integer maintenanceWeight,
                                   Integer mileageWeight, String name) {
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
    }

    // Getters and Setters
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
}
