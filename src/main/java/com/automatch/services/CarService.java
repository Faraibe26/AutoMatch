package com.automatch.services;

import com.automatch.models.Car;
import com.automatch.models.User;
import com.automatch.repositories.CarRepository;
import com.automatch.repositories.FavoriteCarRepository;
import com.automatch.dto.CarDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * CarService - Manages car catalog and operations.
 * Refactored from the Banking System's AccountService pattern.
 * 
 * Handles car listings, filtering, and favorite tracking.
 */
@Service
@Transactional
public class CarService {
    
    private final CarRepository carRepository;
    private final FavoriteCarRepository favoriteCarRepository;
    
    public CarService(CarRepository carRepository, FavoriteCarRepository favoriteCarRepository) {
        this.carRepository = carRepository;
        this.favoriteCarRepository = favoriteCarRepository;
    }
    
    /**
     * Gets all available cars.
     */
    public List<CarDTO> getAllAvailableCars() {
        return carRepository.findByAvailableTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Gets a specific car by ID.
     */
    public Optional<CarDTO> getCarById(Long id) {
        return carRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    /**
     * Filters cars by price range.
     */
    public List<CarDTO> getCarsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return carRepository.findAvailableCarsByPriceRange(minPrice, maxPrice).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Gets most fuel-efficient cars.
     */
    public List<CarDTO> getMostFuelEfficientCars(Double minMpg) {
        return carRepository.findMostFuelEfficientCars(minMpg).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Gets most reliable cars.
     */
    public List<CarDTO> getMostReliableCars(Integer minScore) {
        return carRepository.findMostReliableCars(minScore).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Gets top favorited cars.
     */
    public List<CarDTO> getTopFavoritedCars() {
        return carRepository.findTopFavoritedCars().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Searches cars by make and model.
     */
    public List<CarDTO> searchCars(String make, String model) {
        if (make != null && model != null) {
            return carRepository.findByMakeIgnoreCaseAndModelIgnoreCase(make, model).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else if (make != null) {
            return carRepository.findByMakeIgnoreCase(make).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        return getAllAvailableCars();
    }
    
    /**
     * Gets cars by year range.
     */
    public List<CarDTO> getCarsByYear(Integer startYear, Integer endYear) {
        return carRepository.findByYearBetween(startYear, endYear).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Creates a new car (admin only).
     */
    public CarDTO createCar(Car car) {
        Car saved = carRepository.save(car);
        return convertToDTO(saved);
    }
    
    /**
     * Updates an existing car (admin only).
     */
    public CarDTO updateCar(Long id, Car carDetails) {
        Optional<Car> carOpt = carRepository.findById(id);
        if (carOpt.isPresent()) {
            Car car = carOpt.get();
            if (carDetails.getMake() != null) car.setMake(carDetails.getMake());
            if (carDetails.getModel() != null) car.setModel(carDetails.getModel());
            if (carDetails.getYear() != null) car.setYear(carDetails.getYear());
            if (carDetails.getPrice() != null) car.setPrice(carDetails.getPrice());
            if (carDetails.getMileage() != null) car.setMileage(carDetails.getMileage());
            if (carDetails.getMpg() != null) car.setMpg(carDetails.getMpg());
            if (carDetails.getReliabilityScore() != null) car.setReliabilityScore(carDetails.getReliabilityScore());
            if (carDetails.getAnnualMaintenanceCost() != null) car.setAnnualMaintenanceCost(carDetails.getAnnualMaintenanceCost());
            
            Car updated = carRepository.save(car);
            return convertToDTO(updated);
        }
        return null;
    }
    
    /**
     * Converts Car entity to DTO.
     */
    private CarDTO convertToDTO(Car car) {
        return CarDTO.builder()
                .id(car.getId())
                .make(car.getMake())
                .model(car.getModel())
                .year(car.getYear())
                .price(car.getPrice())
                .mileage(car.getMileage())
                .mpg(car.getMpg())
                .fuelType(car.getFuelType())
                .driveType(car.getDriveType())
                .bodyType(car.getBodyType())
                .reliabilityScore(car.getReliabilityScore())
                .annualMaintenanceCost(car.getAnnualMaintenanceCost())
                .imageUrl(car.getImageUrl())
                .description(car.getDescription())
                .available(car.isAvailable())
                .fullName(car.getFullName())
                .favoriteCount(car.getFavoriteCount())
                .build();
    }
}
