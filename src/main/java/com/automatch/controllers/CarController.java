package com.automatch.controllers;

import com.automatch.services.CarService;
import com.automatch.dto.CarDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * CarController - Handles car catalog endpoints.
 * Provides endpoints for browsing and filtering cars.
 */
@RestController
@RequestMapping("/cars")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CarController {
    
    private final CarService carService;
    
    public CarController(CarService carService) {
        this.carService = carService;
    }
    
    /**
     * GET /cars - Get all available cars.
     */
    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars() {
        List<CarDTO> cars = carService.getAllAvailableCars();
        return ResponseEntity.ok(cars);
    }
    
    /**
     * GET /cars/{id} - Get specific car by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable Long id) {
        return carService.getCarById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * GET /cars/search - Search cars by make and model.
     */
    @GetMapping("/search")
    public ResponseEntity<List<CarDTO>> searchCars(
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String model) {
        List<CarDTO> cars = carService.searchCars(make, model);
        return ResponseEntity.ok(cars);
    }
    
    /**
     * GET /cars/filter/price - Filter cars by price range.
     */
    @GetMapping("/filter/price")
    public ResponseEntity<List<CarDTO>> getCarsByPrice(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        List<CarDTO> cars = carService.getCarsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(cars);
    }
    
    /**
     * GET /cars/filter/efficiency - Get fuel-efficient cars.
     */
    @GetMapping("/filter/efficiency")
    public ResponseEntity<List<CarDTO>> getFuelEfficientCars(
            @RequestParam(defaultValue = "20.0") Double minMpg) {
        List<CarDTO> cars = carService.getMostFuelEfficientCars(minMpg);
        return ResponseEntity.ok(cars);
    }
    
    /**
     * GET /cars/filter/reliability - Get reliable cars.
     */
    @GetMapping("/filter/reliability")
    public ResponseEntity<List<CarDTO>> getReliableCars(
            @RequestParam(defaultValue = "75") Integer minScore) {
        List<CarDTO> cars = carService.getMostReliableCars(minScore);
        return ResponseEntity.ok(cars);
    }
    
    /**
     * GET /cars/popular - Get top favorited cars.
     */
    @GetMapping("/popular")
    public ResponseEntity<List<CarDTO>> getPopularCars() {
        List<CarDTO> cars = carService.getTopFavoritedCars();
        return ResponseEntity.ok(cars);
    }
    
    /**
     * GET /cars/filter/year - Get cars by year range.
     */
    @GetMapping("/filter/year")
    public ResponseEntity<List<CarDTO>> getCarsByYear(
            @RequestParam Integer startYear,
            @RequestParam Integer endYear) {
        List<CarDTO> cars = carService.getCarsByYear(startYear, endYear);
        return ResponseEntity.ok(cars);
    }
}
