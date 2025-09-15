package by.ilyushenko.farm.repository;

import by.ilyushenko.farm.entity.Vegetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VegetableRepository extends JpaRepository<Vegetable, Long> {
    
    List<Vegetable> findByFarmId(Long farmId);
    
    List<Vegetable> findByNameContainingIgnoreCase(String name);
    
    List<Vegetable> findByColorContainingIgnoreCase(String color);
    
    @Query("SELECT v FROM Vegetable v JOIN FETCH v.farm WHERE v.id = :id")
    Optional<Vegetable> findByIdWithFarm(@Param("id") Long id);
    
    @Query("SELECT v FROM Vegetable v JOIN FETCH v.farm")
    List<Vegetable> findAllWithFarm();
    
    @Query("SELECT v FROM Vegetable v JOIN FETCH v.farm WHERE v.farm.id = :farmId")
    List<Vegetable> findByFarmIdWithFarm(@Param("farmId") Long farmId);

    List<Vegetable> findByColorAndWeight(String color, Double weight);
}
