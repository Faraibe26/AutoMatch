package com.automatch.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * User entity representing a registered user in the AutoMatch AI system.
 * Refactored from the Banking System's Customer model.
 * 
 * Each user can have search preferences, view recommendations, and save favorite cars.
 * Uses JPA for database persistence with PostgreSQL.
 */
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(unique = true, nullable = false)
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    @NotBlank(message = "First name is required")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    private String lastName;
    
    private String profileImageUrl;
    
    @Column(nullable = false)
    private String role = "USER"; // USER, ADMIN
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    private boolean active = true;
    
    // Relationships
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<SearchPreference> searchPreferences = new HashSet<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Recommendation> recommendations = new HashSet<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<FavoriteCar> favoriteCars = new HashSet<>();
    
    // Constructors
    public User() {}

    public User(Long id, String email, String password, String firstName, String lastName,
                String profileImageUrl, String role, LocalDateTime createdAt, LocalDateTime updatedAt,
                boolean active, Set<SearchPreference> searchPreferences, Set<Recommendation> recommendations,
                Set<FavoriteCar> favoriteCars) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.active = active;
        this.searchPreferences = searchPreferences;
        this.recommendations = recommendations;
        this.favoriteCars = favoriteCars;
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
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<SearchPreference> getSearchPreferences() {
        return searchPreferences;
    }

    public void setSearchPreferences(Set<SearchPreference> searchPreferences) {
        this.searchPreferences = searchPreferences;
    }

    public Set<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(Set<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }

    public Set<FavoriteCar> getFavoriteCars() {
        return favoriteCars;
    }

    public void setFavoriteCars(Set<FavoriteCar> favoriteCars) {
        this.favoriteCars = favoriteCars;
    }

    /**
     * Gets the user's full name.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    /**
     * Adds a search preference for this user.
     */
    public void addSearchPreference(SearchPreference preference) {
        searchPreferences.add(preference);
        preference.setUser(this);
    }
    
    /**
     * Adds a recommendation for this user.
     */
    public void addRecommendation(Recommendation recommendation) {
        recommendations.add(recommendation);
        recommendation.setUser(this);
    }
    
    /**
     * Adds a favorite car for this user.
     */
    public void addFavoriteCar(FavoriteCar favoriteCar) {
        favoriteCars.add(favoriteCar);
        favoriteCar.setUser(this);
    }
}
