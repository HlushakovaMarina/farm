package by.ilyushenko.farm.dto;

public class FarmStats {
    private Long farmId;
    private String farmName;
    private Long fruitCount;
    private Long vegetableCount;
    private Long totalWeight;

    public FarmStats(String farmName, Long fruitCount, Long vegetableCount, Long totalWeight) {
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
}
