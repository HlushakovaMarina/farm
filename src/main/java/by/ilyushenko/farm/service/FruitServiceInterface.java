package by.ilyushenko.farm.service;

import by.ilyushenko.farm.entity.Fruit;

import java.util.List;
import java.util.Optional;

public interface FruitServiceInterface {
    
    List<Fruit> getAllFruits();
    
    Optional<Fruit> getFruitById(Long id);
    
    Fruit createFruit(Fruit fruit, Long farmId);
    
    Fruit updateFruit(Long id, Fruit fruitDetails, Long farmId);
    
    void deleteFruit(Long id);
    
    List<Fruit> getFruitsByFarmId(Long farmId);
    
    List<Fruit> findByName(String name);
    
    List<Fruit> findByColor(String color);
    
    boolean existsById(Long id);

    List<Fruit> filterFruitsByColor(String color);

    Fruit moveFruitToFarm(Long fruitId, Long farmId);

    void deleteFruitsByFarmId(Long farmId);
}
