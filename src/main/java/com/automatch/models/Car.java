package com.automatch.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Car entity representing a vehicle in the AutoMatch AI catalog.
 * Refactored from the Banking System's Account model.
 * 
 * Contains all vehicle information needed for recommendations and comparisons.
 * Uses JPA for database persistence with PostgreSQL.
 */
@Entity
@Table(name = "cars")
public class Car {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Make is required")
    private String make; // Honda, Toyota, BMW, etc.
    
    @NotBlank(message = "Model is required")
    private String model; // CR-V, RAV4, X5, etc.
    
    @NotNull(message = "Year is required")
    @Min(1980)
    @Max(2030)
    @Column(name = "\"year\"")
    private Integer year;
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;
    
    @NotNull(message = "Mileage is required")
    @Min(0)
    private Long mileage; // in miles
    
    @NotNull(message = "MPG is required")
    @DecimalMin(value = "5.0")
    @DecimalMax(value = "50.0")
    private Double mpg; // Miles Per Gallon
    
    private String fuelType; // Gasoline, Diesel, Electric, Hybrid
    
    private String driveType; // AWD, FWD, RWD, 4WD
    
    private String bodyType; // SUV, Sedan, Truck, Coupe, Wagon, etc.
    
    @Min(0)
    @Max(100)
    private Integer reliabilityScore; // 0-100 rating
    
    @DecimalMin(value = "0.0")
    private BigDecimal annualMaintenanceCost;
    
    private String imageUrl;
    
    private String description;
    
    @Column(nullable = false)
    private boolean available = true;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relationships
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Recommendation> recommendations = new HashSet<>();
    
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<FavoriteCar> favoritedBy = new HashSet<>();

    // Constructors
    public Car() {}

    public Car(Long id, String make, String model, Integer year, BigDecimal price, Long mileage,
               Double mpg, String fuelType, String driveType, String bodyType, Integer reliabilityScore,
               BigDecimal annualMaintenanceCost, String imageUrl, String description, boolean available,
               LocalDateTime createdAt, LocalDateTime updatedAt, Set<Recommendation> recommendations,
               Set<FavoriteCar> favoritedBy) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
        this.mileage = mileage;
        this.mpg = mpg;
        this.fuelType = fuelType;
        this.driveType = driveType;
        this.bodyType = bodyType;
        this.reliabilityScore = reliabilityScore;
        this.annualMaintenanceCost = annualMaintenanceCost;
        this.imageUrl = imageUrl;
        this.description = description;
        this.available = available;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.recommendations = recommendations;
        this.favoritedBy = favoritedBy;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getMileage() {
        return mileage;
    }

    public void setMileage(Long mileage) {
        this.mileage = mileage;
    }

    public Double getMpg() {
        return mpg;
    }

    public void setMpg(Double mpg) {
        this.mpg = mpg;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getDriveType() {
        return driveType;
    }

    public void setDriveType(String driveType) {
        this.driveType = driveType;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public Integer getReliabilityScore() {
        return reliabilityScore;
    }

    public void setReliabilityScore(Integer reliabilityScore) {
        this.reliabilityScore = reliabilityScore;
    }

    public BigDecimal getAnnualMaintenanceCost() {
        return annualMaintenanceCost;
    }

    public void setAnnualMaintenanceCost(BigDecimal annualMaintenanceCost) {
        this.annualMaintenanceCost = annualMaintenanceCost;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
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

    public Set<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(Set<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }

    public Set<FavoriteCar> getFavoritedBy() {
        return favoritedBy;
    }

    public void setFavoritedBy(Set<FavoriteCar> favoritedBy) {
        this.favoritedBy = favoritedBy;
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
     * Gets the full vehicle name.
     */
    public String getFullName() {
        return year + " " + make + " " + model;
    }
    
    /**
     * Calculates the cost per mile (useful for budget considerations).
     */
    public BigDecimal getCostPerMile() {
        if (mileage == 0) return price;
        return price.divide(new BigDecimal(mileage), 4, java.math.RoundingMode.HALF_UP);
    }
    
    /**
     * Adds a recommendation for this car.
     */
    public void addRecommendation(Recommendation recommendation) {
        recommendations.add(recommendation);
        recommendation.setCar(this);
    }
    
    /**
     * Adds a user favorite for this car.
     */
    public void addFavorite(FavoriteCar favoriteCar) {
        favoritedBy.add(favoriteCar);
        favoriteCar.setCar(this);
    }
    
    /**
     * Gets the number of times this car has been favorited.
     */
    public long getFavoriteCount() {
        return favoritedBy.size();
    }
}
