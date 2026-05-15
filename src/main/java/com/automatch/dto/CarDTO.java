package com.automatch.dto;

import java.math.BigDecimal;

/**
 * CarDTO for API responses containing car information.
 */
public class CarDTO {
    
    private Long id;
    private String make;
    private String model;
    private Integer year;
    private BigDecimal price;
    private Long mileage;
    private Double mpg;
    private String fuelType;
    private String driveType;
    private String bodyType;
    private Integer reliabilityScore;
    private BigDecimal annualMaintenanceCost;
    private String imageUrl;
    private String description;
    private boolean available;
    private String fullName;
    private long favoriteCount;

    public CarDTO() {}

    public CarDTO(Long id, String make, String model, Integer year, BigDecimal price, Long mileage, 
                 Double mpg, String fuelType, String driveType, String bodyType, Integer reliabilityScore, 
                 BigDecimal annualMaintenanceCost, String imageUrl, String description, boolean available, 
                 String fullName, long favoriteCount) {
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
        this.fullName = fullName;
        this.favoriteCount = favoriteCount;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Long getMileage() { return mileage; }
    public void setMileage(Long mileage) { this.mileage = mileage; }

    public Double getMpg() { return mpg; }
    public void setMpg(Double mpg) { this.mpg = mpg; }

    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }

    public String getDriveType() { return driveType; }
    public void setDriveType(String driveType) { this.driveType = driveType; }

    public String getBodyType() { return bodyType; }
    public void setBodyType(String bodyType) { this.bodyType = bodyType; }

    public Integer getReliabilityScore() { return reliabilityScore; }
    public void setReliabilityScore(Integer reliabilityScore) { this.reliabilityScore = reliabilityScore; }

    public BigDecimal getAnnualMaintenanceCost() { return annualMaintenanceCost; }
    public void setAnnualMaintenanceCost(BigDecimal annualMaintenanceCost) { this.annualMaintenanceCost = annualMaintenanceCost; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public long getFavoriteCount() { return favoriteCount; }
    public void setFavoriteCount(long favoriteCount) { this.favoriteCount = favoriteCount; }

    public static CarDTOBuilder builder() {
        return new CarDTOBuilder();
    }

    public static class CarDTOBuilder {
        private Long id;
        private String make;
        private String model;
        private Integer year;
        private BigDecimal price;
        private Long mileage;
        private Double mpg;
        private String fuelType;
        private String driveType;
        private String bodyType;
        private Integer reliabilityScore;
        private BigDecimal annualMaintenanceCost;
        private String imageUrl;
        private String description;
        private boolean available;
        private String fullName;
        private long favoriteCount;

        public CarDTOBuilder id(Long id) { this.id = id; return this; }
        public CarDTOBuilder make(String make) { this.make = make; return this; }
        public CarDTOBuilder model(String model) { this.model = model; return this; }
        public CarDTOBuilder year(Integer year) { this.year = year; return this; }
        public CarDTOBuilder price(BigDecimal price) { this.price = price; return this; }
        public CarDTOBuilder mileage(Long mileage) { this.mileage = mileage; return this; }
        public CarDTOBuilder mpg(Double mpg) { this.mpg = mpg; return this; }
        public CarDTOBuilder fuelType(String fuelType) { this.fuelType = fuelType; return this; }
        public CarDTOBuilder driveType(String driveType) { this.driveType = driveType; return this; }
        public CarDTOBuilder bodyType(String bodyType) { this.bodyType = bodyType; return this; }
        public CarDTOBuilder reliabilityScore(Integer reliabilityScore) { this.reliabilityScore = reliabilityScore; return this; }
        public CarDTOBuilder annualMaintenanceCost(BigDecimal annualMaintenanceCost) { this.annualMaintenanceCost = annualMaintenanceCost; return this; }
        public CarDTOBuilder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }
        public CarDTOBuilder description(String description) { this.description = description; return this; }
        public CarDTOBuilder available(boolean available) { this.available = available; return this; }
        public CarDTOBuilder fullName(String fullName) { this.fullName = fullName; return this; }
        public CarDTOBuilder favoriteCount(long favoriteCount) { this.favoriteCount = favoriteCount; return this; }

        public CarDTO build() {
            return new CarDTO(id, make, model, year, price, mileage, mpg, fuelType, driveType, bodyType, 
                             reliabilityScore, annualMaintenanceCost, imageUrl, description, available, fullName, favoriteCount);
        }
    }
}
