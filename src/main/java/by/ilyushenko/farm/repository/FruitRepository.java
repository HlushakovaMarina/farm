package by.ilyushenko.farm.repository;

import by.ilyushenko.farm.entity.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FruitRepository extends JpaRepository<Fruit, Long> {
    
    List<Fruit> findByFarmId(Long farmId);
    
    List<Fruit> findByNameContainingIgnoreCase(String name);
    
    List<Fruit> findByColorContainingIgnoreCase(String color);
    
    @Query("SELECT f FROM Fruit f JOIN FETCH f.farm WHERE f.id = :id")
    Optional<Fruit> findByIdWithFarm(@Param("id") Long id);
    
    @Query("SELECT f FROM Fruit f JOIN FETCH f.farm")
    List<Fruit> findAllWithFarm();
    
    @Query("SELECT f FROM Fruit f JOIN FETCH f.farm WHERE f.farm.id = :farmId")
    List<Fruit> findByFarmIdWithFarm(@Param("farmId") Long farmId);

    List<Fruit> findByColor(String color);

    long countByFarmId(Long farmId);

    @Transactional
    void deleteByFarmId(Long farmId);
}
