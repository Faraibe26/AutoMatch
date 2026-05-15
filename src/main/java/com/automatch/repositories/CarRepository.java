package com.automatch.repositories;

import com.automatch.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Car entity operations.
 * Provides database access and custom queries for cars.
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    
    List<Car> findByAvailableTrue();
    
    List<Car> findByMakeIgnoreCase(String make);
    
    List<Car> findByMakeIgnoreCaseAndModelIgnoreCase(String make, String model);
    
    List<Car> findByYearBetween(Integer startYear, Integer endYear);
    
    List<Car> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    @Query("SELECT c FROM Car c WHERE c.available = true AND c.price BETWEEN :minPrice AND :maxPrice ORDER BY c.price ASC")
    List<Car> findAvailableCarsByPriceRange(@Param("minPrice") BigDecimal minPrice, 
                                            @Param("maxPrice") BigDecimal maxPrice);
    
    @Query("SELECT c FROM Car c WHERE c.available = true AND c.reliabilityScore >= :minScore ORDER BY c.reliabilityScore DESC")
    List<Car> findMostReliableCars(@Param("minScore") Integer minScore);
    
    @Query("SELECT c FROM Car c WHERE c.available = true ORDER BY c.reliabilityScore DESC")
    List<Car> findMostFuelEfficientCars(@Param("minMpg") Double minMpg);
    
    @Query("SELECT c FROM Car c LEFT JOIN c.favoritedBy f WHERE c.available = true GROUP BY c.id ORDER BY COUNT(f) DESC LIMIT 10")
    List<Car> findTopFavoritedCars();
}
