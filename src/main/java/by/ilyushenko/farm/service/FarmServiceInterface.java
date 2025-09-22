package by.ilyushenko.farm.service;

import by.ilyushenko.farm.dto.FarmDto;
import by.ilyushenko.farm.entity.Farm;
import by.ilyushenko.farm.entity.Vegetable;

import java.util.List;
import java.util.Optional;

public interface FarmServiceInterface {
    
    List<Farm> getAllFarms();
    
    Optional<Farm> getFarmById(Long id);
    
    Farm createFarm(Farm farm);
    
    Farm updateFarm(Long id, Farm farmDetails);
    
    void deleteFarm(Long id);
    
    boolean existsById(Long id);
    
    Optional<Farm> findByName(String name);
    
    List<Farm> findByLocation(String location);

    List<Farm> getFarmByLocation(String location);

    FarmDto getFarmWithFruitCount(Long id);
}
