package by.ilyushenko.farm.dto;

public class FarmStatsDTO {
    private Long farmId;
    private String farmName;
    private Long fruitCount;
    private Long vegetableCount;
    private Long totalWeight;

    public FarmStatsDTO(String farmName, Long fruitCount, Long vegetableCount, Long totalWeight) {
        this.farmName = farmName;
        this.fruitCount = fruitCount;
        this.vegetableCount = vegetableCount;
        this.totalWeight = totalWeight;
    }

    public FarmStatsDTO(Long farmId, String farmName, Long fruitCount, Long vegetableCount, Long totalWeight) {
        this.farmId = farmId;
        this.farmName = farmName;
        this.fruitCount = fruitCount;
        this.vegetableCount = vegetableCount;
        this.totalWeight = totalWeight;
    }

    public Long getFarmId() {
        return farmId;
    }

    public String getFarmName() {
        return farmName;
    }

    public Long getFruitCount() {
        return fruitCount;
    }

    public Long getVegetableCount() {
        return vegetableCount;
    }

    public Long getTotalWeight() {
        return totalWeight;
    }

    public void setFarmId(Long farmId) {
        this.farmId = farmId;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public void setFruitCount(Long fruitCount) {
        this.fruitCount = fruitCount;
    }

    public void setVegetableCount(Long vegetableCount) {
        this.vegetableCount = vegetableCount;
    }

    public void setTotalWeight(Long totalWeight) {
        this.totalWeight = totalWeight;
    }
}

