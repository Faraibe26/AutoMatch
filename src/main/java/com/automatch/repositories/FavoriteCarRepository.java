package com.automatch.repositories;

import com.automatch.models.FavoriteCar;
import com.automatch.models.User;
import com.automatch.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository for FavoriteCar entity operations.
 */
@Repository
public interface FavoriteCarRepository extends JpaRepository<FavoriteCar, Long> {
    
    List<FavoriteCar> findByUser(User user);
    
    Optional<FavoriteCar> findByUserAndCar(User user, Car car);
    
    boolean existsByUserAndCar(User user, Car car);
    
    long countByUser(User user);
    
    void deleteByUserAndCar(User user, Car car);
}
