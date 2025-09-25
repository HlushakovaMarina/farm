package by.ilyushenko.farm.service;

import by.ilyushenko.farm.entity.Vegetable;

import java.util.List;
import java.util.Optional;

public interface VegetableServiceInterface {
    
    List<Vegetable> getAllVegetables();
    
    Optional<Vegetable> getVegetableById(Long id);
    
    Vegetable createVegetable(Vegetable vegetable, Long farmId);
    
    Vegetable updateVegetable(Long id, Vegetable vegetableDetails, Long farmId);
    
    void deleteVegetable(Long id);
    
    List<Vegetable> getVegetablesByFarmId(Long farmId);
    
    List<Vegetable> findByName(String name);
    
    List<Vegetable> findByColor(String color);
    
    boolean existsById(Long id);

    List<Vegetable> findByColorAndWeight(String color, Double weight);

    List<Vegetable> getVegetablesByMinWeight(Integer minWeight);

    Vegetable moveVegetableToFarm(Long vegetableId, Long farmId);

    void deleteVegetablesByFarmId(Long farmId);
}
