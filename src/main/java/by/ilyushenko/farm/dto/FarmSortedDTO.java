package by.ilyushenko.farm.dto;

public class FarmSortedDTO {
    private Long id;
    private String name;
    private String location;
    private Long fruitCount;
    private Long vegetableCount;

    public FarmSortedDTO(Long id, String name, String location, Long fruitCount, Long vegetableCount) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.fruitCount = fruitCount;
        this.vegetableCount = vegetableCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getFruitCount() {
        return fruitCount;
    }

    public void setFruitCount(Long fruitCount) {
        this.fruitCount = fruitCount;
    }

    public Long getVegetableCount() {
        return vegetableCount;
    }

    public void setVegetableCount(Long vegetableCount) {
        this.vegetableCount = vegetableCount;
    }
}
