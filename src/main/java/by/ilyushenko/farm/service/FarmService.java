package by.ilyushenko.farm.service;

import by.ilyushenko.farm.entity.Farm;
import by.ilyushenko.farm.exception.ResourceNotFoundException;
import by.ilyushenko.farm.repository.FarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FarmService implements FarmServiceInterface {
    
    private final FarmRepository farmRepository;
    
    @Autowired
    public FarmService(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }
    
    @Transactional(readOnly = true)
    public List<Farm> getAllFarms() {
        return farmRepository.findAllWithVegetablesAndFruits();
    }
    
    @Transactional(readOnly = true)
    public Optional<Farm> getFarmById(Long id) {
        return farmRepository.findByIdWithVegetablesAndFruits(id);
    }
    
    public Farm createFarm(Farm farm) {
        return farmRepository.save(farm);
    }
    
    public Farm updateFarm(Long id, Farm farmDetails) {
        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Farm not found with id: " + id));
        
        farm.setName(farmDetails.getName());
        farm.setLocation(farmDetails.getLocation());
        
        return farmRepository.save(farm);
    }
    
    public void deleteFarm(Long id) {
        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Farm not found with id: " + id));
        
        farmRepository.delete(farm);
    }
    
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return farmRepository.existsById(id);
    }
    
    @Transactional(readOnly = true)
    public Optional<Farm> findByName(String name) {
        return farmRepository.findByName(name);
    }
    
    @Transactional(readOnly = true)
    public List<Farm> findByLocation(String location) {
        return farmRepository.findByLocationContainingIgnoreCase(location);
    }
}
