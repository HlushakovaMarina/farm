package by.ilyushenko.farm.service;

import by.ilyushenko.farm.dto.FarmDto;
import by.ilyushenko.farm.entity.Farm;
import by.ilyushenko.farm.exception.FarmNotFoundException;
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
    private final FruitRepository fruitRepository;
    private final VegetableRepository vegetableRepository;

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
@Transactional(readOnly = true)
    public List<Farm> getFarmByLocation(String location) {
        List<Farm> farms = farmRepository.findByLocationContainingIgnoreCase(location);
        if(farms.isEmpty()){
            throw new FarmNotFoundException(String.format("Ферма %s с такой локацией не найдена", location));
        } else{
            return farms;
        }
    }
    public FarmDto getFarmWithFruitCount(Long id){
        Farm farm = farmRepository.findByIdWithVegetablesAndFruits(id)
                .orElseThrow(()-> new ResourceNotFoundException("fa" +
                        "Farm not found with id: " + id));
        int fruitCount = farm.getFruits().size();
        FarmDto farmDto = new FarmDto(farm.getName(),fruitCount);
        return farmDto;
    }
    //1. Поиск ферм по названию (частичный поиск)
    public List<Farm> searchFarmsByName(String searchTerm) {
        return farmRepository.findByNameContainingIgnoreCase(searchTerm);
    }
//5. Получение статистики по ферме
    public FarmStats getFarmStats(Long farmId) {
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Farm not found"));

        FarmStats stats = new FarmStats();
        stats.setFarmId(farmId);
        stats.setFarmName(farm.getName());
        stats.setFruitCount(fruitRepository.countByFarmId(farmId));
        stats.setVegetableCount(vegetableRepository.countByFarmId(farmId));

        long totalWeight = 0;
        List<Vegetable> vegetables = vegetableRepository.findByFarmId(farmId);
        for (Vegetable vegetable : vegetables) {
            totalWeight += vegetable.getWeight();
        }
        List<Fruit> fruits = fruitRepository.findByFarmId(farmId);
        for (Fruit fruit : fruits) {
            totalWeight += fruit.getWeight();
        }
        stats.setTotalWeight(totalWeight);
        return stats;
    }
}
