package com.automatch.repositories;

import com.automatch.models.Recommendation;
import com.automatch.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository for Recommendation entity operations.
 */
@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    
    List<Recommendation> findByUser(User user);
    
    List<Recommendation> findByUserOrderByMatchScoreDesc(User user);
    
    List<Recommendation> findByUserAndSearchPreference(User user, com.automatch.models.SearchPreference preference);
    
    @Query("SELECT r FROM Recommendation r WHERE r.user = :user ORDER BY r.matchScore DESC LIMIT 10")
    List<Recommendation> findTopRecommendationsForUser(@Param("user") User user);
    
    long countByUser(User user);
}
