package com.artisthub.repository;

import com.artisthub.entity.ArtClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtClassRepository extends JpaRepository<ArtClass, Long> {

    List<ArtClass> findByCategoryIgnoreCase(String category);

    List<ArtClass> findByLevelIgnoreCase(String level);

    List<ArtClass> findByModeIgnoreCase(String mode);

    List<ArtClass> findByTeacherId(Long teacherId);

    @Query("SELECT c FROM ArtClass c WHERE " +
           "(:category IS NULL OR :category = '' OR LOWER(c.category) = LOWER(:category)) AND " +
           "(:level IS NULL OR :level = '' OR LOWER(c.level) = LOWER(:level)) AND " +
           "(:mode IS NULL OR :mode = '' OR LOWER(c.mode) = LOWER(:mode)) AND " +
           "(:search IS NULL OR :search = '' OR LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(c.description) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
           "(:maxFee IS NULL OR c.fee <= :maxFee)")
    List<ArtClass> findWithFilters(@Param("category") String category,
                                   @Param("level") String level,
                                   @Param("mode") String mode,
                                   @Param("search") String search,
                                   @Param("maxFee") Double maxFee);
}
