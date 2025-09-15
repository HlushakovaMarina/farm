package by.ilyushenko.farm.service;

import by.ilyushenko.farm.entity.Farm;
import by.ilyushenko.farm.entity.Vegetable;
import by.ilyushenko.farm.exception.ResourceNotFoundException;
import by.ilyushenko.farm.repository.FarmRepository;
import by.ilyushenko.farm.repository.VegetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VegetableService implements VegetableServiceInterface {
    
    private final VegetableRepository vegetableRepository;
    private final FarmRepository farmRepository;
    
    @Autowired
    public VegetableService(VegetableRepository vegetableRepository, FarmRepository farmRepository) {
        this.vegetableRepository = vegetableRepository;
        this.farmRepository = farmRepository;
    }
    
    @Transactional(readOnly = true)
    public List<Vegetable> getAllVegetables() {
        return vegetableRepository.findAllWithFarm();
    }
    
    @Transactional(readOnly = true)
    public Optional<Vegetable> getVegetableById(Long id) {
        return vegetableRepository.findByIdWithFarm(id);
    }
    
    public Vegetable createVegetable(Vegetable vegetable, Long farmId) {
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm not found with id: " + farmId));
        
        vegetable.setFarm(farm);
        Vegetable savedVegetable = vegetableRepository.save(vegetable);
        
        // Add to farm's vegetables list
        farm.addVegetable(savedVegetable);
        
        return savedVegetable;
    }
    
    public Vegetable updateVegetable(Long id, Vegetable vegetableDetails, Long farmId) {
        Vegetable vegetable = vegetableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vegetable not found with id: " + id));
        
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm not found with id: " + farmId));
        
        // Update vegetable properties
        vegetable.setName(vegetableDetails.getName());
        vegetable.setColor(vegetableDetails.getColor());
        vegetable.setWeight(vegetableDetails.getWeight());
        
        // Update farm if changed
        if (!vegetable.getFarm().getId().equals(farmId)) {
            Farm oldFarm = vegetable.getFarm();
            oldFarm.removeVegetable(vegetable);
            vegetable.setFarm(farm);
            farm.addVegetable(vegetable);
        }
        
        return vegetableRepository.save(vegetable);
    }
    
    public void deleteVegetable(Long id) {
        Vegetable vegetable = vegetableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vegetable not found with id: " + id));
        
        // Remove from farm's vegetables list
        Farm farm = vegetable.getFarm();
        farm.removeVegetable(vegetable);
        
        vegetableRepository.delete(vegetable);
    }
    
    @Transactional(readOnly = true)
    public List<Vegetable> getVegetablesByFarmId(Long farmId) {
        return vegetableRepository.findByFarmIdWithFarm(farmId);
    }
    
    @Transactional(readOnly = true)
    public List<Vegetable> findByName(String name) {
        return vegetableRepository.findByNameContainingIgnoreCase(name);
    }
    
    @Transactional(readOnly = true)
    public List<Vegetable> findByColor(String color) {
        return vegetableRepository.findByColorContainingIgnoreCase(color);
    }
    
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return vegetableRepository.existsById(id);
    }
    @Transactional(readOnly = true)
    public List<Vegetable> findByColorAndWeight(String color, Double weight){
        return vegetableRepository.findByColorAndWeight(color, weight);
    }
}
