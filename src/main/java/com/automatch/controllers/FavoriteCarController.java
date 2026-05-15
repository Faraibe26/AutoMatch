package com.automatch.controllers;

import com.automatch.models.FavoriteCar;
import com.automatch.models.User;
import com.automatch.models.Car;
import com.automatch.repositories.FavoriteCarRepository;
import com.automatch.repositories.UserRepository;
import com.automatch.repositories.CarRepository;
import com.automatch.dto.CarDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FavoriteCarController - Handles favorite car endpoints.
 * Allows users to save and manage favorite vehicles.
 */
@RestController
@RequestMapping("/favorites")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class FavoriteCarController {
    
    private final FavoriteCarRepository favoriteCarRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    
    public FavoriteCarController(FavoriteCarRepository favoriteCarRepository,
                                 UserRepository userRepository,
                                 CarRepository carRepository) {
        this.favoriteCarRepository = favoriteCarRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }
    
    /**
     * GET /favorites - Get all favorite cars for current user.
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CarDTO>> getFavoriteCars(
            @RequestHeader("Authorization") String authHeader) {
        
        Long userId = extractUserIdFromHeader(authHeader);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<FavoriteCar> favorites = favoriteCarRepository.findByUser(user);
        
        List<CarDTO> cars = favorites.stream()
                .map(fav -> convertCarToDTO(fav.getCar()))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(cars);
    }
    
    /**
     * POST /favorites/{carId} - Add car to favorites.
     */
    @PostMapping("/{carId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> addFavoriteCar(
            @PathVariable Long carId,
            @RequestHeader("Authorization") String authHeader) {
        
        Long userId = extractUserIdFromHeader(authHeader);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));
        
        // Check if already favorited
        if (favoriteCarRepository.existsByUserAndCar(user, car)) {
            return ResponseEntity.badRequest().body("Car already in favorites");
        }
        
        // Create favorite
        FavoriteCar favorite = new FavoriteCar();
        favorite.setUser(user);
        favorite.setCar(car);
        
        favoriteCarRepository.save(favorite);
        return ResponseEntity.ok("Car added to favorites");
    }
    
    /**
     * DELETE /favorites/{carId} - Remove car from favorites.
     */
    @DeleteMapping("/{carId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> removeFavoriteCar(
            @PathVariable Long carId,
            @RequestHeader("Authorization") String authHeader) {
        
        Long userId = extractUserIdFromHeader(authHeader);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));
        
        // Check if favorite exists
        if (!favoriteCarRepository.existsByUserAndCar(user, car)) {
            return ResponseEntity.badRequest().body("Car not in favorites");
        }
        
        favoriteCarRepository.deleteByUserAndCar(user, car);
        return ResponseEntity.ok("Car removed from favorites");
    }
    
    /**
     * GET /favorites/{carId}/is-favorited - Check if car is favorited.
     */
    @GetMapping("/{carId}/is-favorited")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Boolean> isFavorited(
            @PathVariable Long carId,
            @RequestHeader("Authorization") String authHeader) {
        
        Long userId = extractUserIdFromHeader(authHeader);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));
        
        boolean isFavorited = favoriteCarRepository.existsByUserAndCar(user, car);
        return ResponseEntity.ok(isFavorited);
    }
    
    /**
     * GET /favorites/count - Get count of favorited cars.
     */
    @GetMapping("/count")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Long> getFavoriteCount(
            @RequestHeader("Authorization") String authHeader) {
        
        Long userId = extractUserIdFromHeader(authHeader);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        long count = favoriteCarRepository.countByUser(user);
        return ResponseEntity.ok(count);
    }
    
    /**
     * Helper method to convert Car to DTO.
     */
    private CarDTO convertCarToDTO(Car car) {
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
                .imageUrl(car.getImageUrl())
                .fullName(car.getFullName())
                .favoriteCount(car.getFavoriteCount())
                .build();
    }
    
    /**
     * Helper method to extract user ID from JWT token header.
     */
    private Long extractUserIdFromHeader(String authHeader) {
        // Simplified - in production use proper JWT parsing or SecurityContextHolder
        return 1L;
    }
}
