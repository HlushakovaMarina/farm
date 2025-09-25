package by.ilyushenko.farm.service;

import by.ilyushenko.farm.entity.Farm;
import by.ilyushenko.farm.entity.Fruit;
import by.ilyushenko.farm.exception.ResourceNotFoundException;
import by.ilyushenko.farm.repository.FarmRepository;
import by.ilyushenko.farm.repository.FruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FruitService implements FruitServiceInterface {
    
    private final FruitRepository fruitRepository;
    private final FarmRepository farmRepository;
    
    @Autowired
    public FruitService(FruitRepository fruitRepository, FarmRepository farmRepository) {
        this.fruitRepository = fruitRepository;
        this.farmRepository = farmRepository;
    }
    
    @Transactional(readOnly = true)
    public List<Fruit> getAllFruits() {
        return fruitRepository.findAllWithFarm();
    }
    
    @Transactional(readOnly = true)
    public Optional<Fruit> getFruitById(Long id) {
        return fruitRepository.findByIdWithFarm(id);
    }
    
    public Fruit createFruit(Fruit fruit, Long farmId) {
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm not found with id: " + farmId));
        
        fruit.setFarm(farm);
        Fruit savedFruit = fruitRepository.save(fruit);
        
        // Add to farm's fruits list
        farm.addFruit(savedFruit);
        
        return savedFruit;
    }
    
    public Fruit updateFruit(Long id, Fruit fruitDetails, Long farmId) {
        Fruit fruit = fruitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fruit not found with id: " + id));
        
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm not found with id: " + farmId));
        
        // Update fruit properties
        fruit.setName(fruitDetails.getName());
        fruit.setColor(fruitDetails.getColor());
        fruit.setWeight(fruitDetails.getWeight());
        
        // Update farm if changed
        if (!fruit.getFarm().getId().equals(farmId)) {
            Farm oldFarm = fruit.getFarm();
            oldFarm.removeFruit(fruit);
            fruit.setFarm(farm);
            farm.addFruit(fruit);
        }
        
        return fruitRepository.save(fruit);
    }
    
    public void deleteFruit(Long id) {
        Fruit fruit = fruitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fruit not found with id: " + id));
        
        // Remove from farm's fruits list
        Farm farm = fruit.getFarm();
        farm.removeFruit(fruit);
        
        fruitRepository.delete(fruit);
    }
    
    @Transactional(readOnly = true)
    public List<Fruit> getFruitsByFarmId(Long farmId) {
        return fruitRepository.findByFarmIdWithFarm(farmId);
    }
    
    @Transactional(readOnly = true)
    public List<Fruit> findByName(String name) {
        return fruitRepository.findByNameContainingIgnoreCase(name);
    }
    
    @Transactional(readOnly = true)
    public List<Fruit> findByColor(String color) {
        return fruitRepository.findByColorContainingIgnoreCase(color);
    }
    
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return fruitRepository.existsById(id);
    }

    //2. Фильтрация фруктов по цвету
    public List<Fruit> filterFruitsByColor(String color) {
        if (color == null || color.trim().isEmpty()) {
            return fruitRepository.findAll();
        } else {
            return fruitRepository.findByColor(color);
        }
    }
    //4. Перемещение фрукта или овоща на другую ферму
    @Transactional
    public Fruit moveFruitToFarm(Long fruitId, Long farmId) {
        Fruit fruit = fruitRepository.findById(fruitId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fruit not found"));

        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Farm not found"));

        fruit.setFarm(farm);
        return fruitRepository.save(fruit);
    }
//6. Массовое удаление фруктов или овощей по ферме
    @Transactional
    public void deleteFruitsByFarmId(Long farmId) {
        if (!farmRepository.existsById(farmId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Farm not found");
        }
        fruitRepository.deleteByFarmId(farmId);
    }
}
