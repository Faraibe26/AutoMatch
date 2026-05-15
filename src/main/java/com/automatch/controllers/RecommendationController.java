package com.automatch.controllers;

import com.automatch.models.Recommendation;
import com.automatch.models.SearchPreference;
import com.automatch.models.User;
import com.automatch.services.RecommendationService;
import com.automatch.services.AuthService;
import com.automatch.repositories.SearchPreferenceRepository;
import com.automatch.repositories.UserRepository;
import com.automatch.dto.RecommendationDTO;
import com.automatch.dto.SearchPreferenceRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * RecommendationController - Handles AI recommendation endpoints.
 * Core AI recommendation engine for matching users with cars.
 */
@RestController
@RequestMapping("/recommendations")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class RecommendationController {
    
    private final RecommendationService recommendationService;
    private final SearchPreferenceRepository searchPreferenceRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    
    public RecommendationController(RecommendationService recommendationService,
                                    SearchPreferenceRepository searchPreferenceRepository,
                                    UserRepository userRepository,
                                    AuthService authService) {
        this.recommendationService = recommendationService;
        this.searchPreferenceRepository = searchPreferenceRepository;
        this.userRepository = userRepository;
        this.authService = authService;
    }
    
    /**
     * POST /recommendations/generate - Generate recommendations for user.
     * Requires search preferences to be provided or already stored.
     */
    @PostMapping("/generate")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<RecommendationDTO>> generateRecommendations(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody SearchPreferenceRequest preferenceRequest) {
        
        // Extract user ID from auth header or use principal
        // For now, we'll use a user ID from request or header
        Long userId = extractUserIdFromHeader(authHeader);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Create search preference from request
        SearchPreference preference = new SearchPreference();
        preference.setUser(user);
        preference.setMinBudget(preferenceRequest.getMinBudget());
        preference.setMaxBudget(preferenceRequest.getMaxBudget());
        preference.setMinMpg(preferenceRequest.getMinMpg());
        preference.setMaxMileage(preferenceRequest.getMaxMileage());
        preference.setMinReliabilityScore(preferenceRequest.getMinReliabilityScore());
        preference.setPreferredFuelType(preferenceRequest.getPreferredFuelType());
        preference.setPreferredDriveType(preferenceRequest.getPreferredDriveType());
        preference.setPreferredBodyType(preferenceRequest.getPreferredBodyType());
        preference.setPreferredBrands(preferenceRequest.getPreferredBrands());
        preference.setExcludedBrands(preferenceRequest.getExcludedBrands());
        preference.setMaxAnnualMaintenance(preferenceRequest.getMaxAnnualMaintenance());
        preference.setBudgetWeight(preferenceRequest.getBudgetWeight());
        preference.setMpgWeight(preferenceRequest.getMpgWeight());
        preference.setReliabilityWeight(preferenceRequest.getReliabilityWeight());
        preference.setMaintenanceWeight(preferenceRequest.getMaintenanceWeight());
        preference.setMileageWeight(preferenceRequest.getMileageWeight());
        preference.setName(preferenceRequest.getName());
        
        // Save preference
        SearchPreference savedPreference = searchPreferenceRepository.save(preference);
        user.addSearchPreference(savedPreference);
        userRepository.save(user);
        
        // Generate recommendations
        List<Recommendation> recommendations = recommendationService.generateRecommendations(user, savedPreference);
        
        // Convert to DTOs
        List<RecommendationDTO> dtos = recommendations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(dtos);
    }
    
    /**
     * GET /recommendations/user - Get all recommendations for current user.
     */
    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<RecommendationDTO>> getUserRecommendations(
            @RequestHeader("Authorization") String authHeader) {
        
        Long userId = extractUserIdFromHeader(authHeader);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<Recommendation> recommendations = recommendationService.getRecommendationsForUser(user);
        
        List<RecommendationDTO> dtos = recommendations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(dtos);
    }
    
    /**
     * GET /recommendations/top - Get top recommendations for current user.
     */
    @GetMapping("/top")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<RecommendationDTO>> getTopRecommendations(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "5") int limit) {
        
        Long userId = extractUserIdFromHeader(authHeader);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<Recommendation> recommendations = recommendationService.getTopRecommendations(user, limit);
        
        List<RecommendationDTO> dtos = recommendations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(dtos);
    }
    
    /**
     * GET /recommendations/{id} - Get specific recommendation.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecommendationDTO> getRecommendation(@PathVariable Long id) {
        return recommendationService.getRecommendation(id)
                .map(r -> ResponseEntity.ok(convertToDTO(r)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Helper method to convert Recommendation to DTO.
     */
    private RecommendationDTO convertToDTO(Recommendation recommendation) {
        return RecommendationDTO.builder()
                .id(recommendation.getId())
                .car(com.automatch.dto.CarDTO.builder()
                        .id(recommendation.getCar().getId())
                        .make(recommendation.getCar().getMake())
                        .model(recommendation.getCar().getModel())
                        .year(recommendation.getCar().getYear())
                        .price(recommendation.getCar().getPrice())
                        .mileage(recommendation.getCar().getMileage())
                        .mpg(recommendation.getCar().getMpg())
                        .reliabilityScore(recommendation.getCar().getReliabilityScore())
                        .fullName(recommendation.getCar().getFullName())
                        .imageUrl(recommendation.getCar().getImageUrl())
                        .build())
                .matchScore(recommendation.getMatchScore())
                .budgetScore(recommendation.getBudgetScore())
                .mpgScore(recommendation.getMpgScore())
                .reliabilityScore(recommendation.getReliabilityScore())
                .maintenanceScore(recommendation.getMaintenanceScore())
                .mileageScore(recommendation.getMileageScore())
                .explanation(recommendation.getExplanation())
                .rank(recommendation.getRank())
                .tierLabel(recommendation.getTierLabel())
                .summary(recommendation.getSummary())
                .build();
    }
    
    /**
     * Helper method to extract user ID from JWT token header.
     * This is a simplified version - in production, use SecurityContextHolder.
     */
    private Long extractUserIdFromHeader(String authHeader) {
        // In a real app, you'd use @AuthenticationPrincipal or SecurityContextHolder
        // For now, returning a default user ID - this should be replaced
        // with proper JWT parsing
        return 1L;
    }
}
