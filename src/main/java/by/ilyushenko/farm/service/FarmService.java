package by.ilyushenko.farm.service;

import by.ilyushenko.farm.dto.FarmDto;
import by.ilyushenko.farm.dto.FarmSortedDTO;
import by.ilyushenko.farm.dto.FarmStatsDTO;
import by.ilyushenko.farm.entity.Farm;
import by.ilyushenko.farm.entity.Fruit;
import by.ilyushenko.farm.entity.Vegetable;
import by.ilyushenko.farm.exception.FarmNotFoundException;
import by.ilyushenko.farm.exception.ResourceNotFoundException;
import by.ilyushenko.farm.repository.FarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (farms.isEmpty()) {
            throw new FarmNotFoundException(String.format("Ферма %s с такой локацией не найдена", location));
        } else {
            return farms;
        }
    }

    public FarmDto getFarmWithFruitCount(Long id) {
        Farm farm = farmRepository.findByIdWithVegetablesAndFruits(id)
                .orElseThrow(() -> new ResourceNotFoundException("fa" +
                        "Farm not found with id: " + id));
        int fruitCount = farm.getFruits().size();
        FarmDto farmDto = new FarmDto(farm.getName(), fruitCount);
        return farmDto;
    }

    //1. Поиск ферм по названию (частичный поиск)
    public List<Farm> searchFarmsByName(String searchTerm) {
        return farmRepository.findByNameContainingIgnoreCase(searchTerm);
    }

    //5. Получение статистики по ферме
    public FarmStatsDTO getFarmStats(Long farmId) {
        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm not found with id: " + farmId));
        int fruitCount = farm.getFruits().size();
        int vegetableCount = farm.getVegetables().size();
        double totalWeight = farm.getFruits().stream().mapToDouble(Fruit::getWeight).sum() +
                farm.getVegetables().stream().mapToDouble(Vegetable::getWeight).sum();
        return new FarmStatsDTO(farmId, farm.getName(), fruitCount, vegetableCount, totalWeight);
    }

    //8.
    public List<FarmSortedDTO> findFarmsSorted(String sortBy, String order) {
        if (!"fruits".equals(sortBy) && !"vegetables".equals(sortBy)) {
            throw new IllegalArgumentException("sortBy должен быть или 'fruits' или 'vegetable")
        }
        if (!"asc".equals(order) && !"desc".equals(order)) {
            throw new IllegalArgumentException("order должен быть или 'asc' или 'desc")
        }
        List<Farm> farms = farmRepository.findAll();
        List<FarmSortedDTO> farmsSortedDTO = farms.stream()
                .map(farm -> new FarmSortedDTO(
                        farm.getId(),
                        farm.getName(),
                        farm.getLocation(),
                        farm.getFruits().size(),
                        farm.getVegetables().size()
                )).sorted(f1, f2 -> {
                    sortBy.equals("fruits") ?
                            Long.compare(f1.getFruitCount(), f2.getFtuitCount()) :
                            Long.compare(f1.getVegetableCount(), f2.getVegetableCount()):
                    return order.equals("asc") ? compare : -compare;
                }).collect(Collectors.toList());
        return farmsSortedDTO;
    }
}
