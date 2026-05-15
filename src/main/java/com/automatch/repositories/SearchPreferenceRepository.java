package com.automatch.repositories;

import com.automatch.models.SearchPreference;
import com.automatch.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository for SearchPreference entity operations.
 */
@Repository
public interface SearchPreferenceRepository extends JpaRepository<SearchPreference, Long> {
    
    List<SearchPreference> findByUser(User user);
    
    Optional<SearchPreference> findByIdAndUser(Long id, User user);
    
    List<SearchPreference> findByUserOrderByCreatedAtDesc(User user);
}
