package by.ilyushenko.farm.repository;

import by.ilyushenko.farm.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {
    
    Optional<Farm> findByName(String name);
    
    List<Farm> findByLocationContainingIgnoreCase(String location);
    
    @Query("SELECT f FROM Farm f WHERE f.id = :id")
    Optional<Farm> findByIdWithVegetablesAndFruits(@Param("id") Long id);
    
    @Query("SELECT f FROM Farm f")
    List<Farm> findAllWithVegetablesAndFruits();
}
