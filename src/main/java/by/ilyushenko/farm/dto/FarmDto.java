package by.ilyushenko.farm.dto;

public class FarmDto {
    private String name;
    private int countFruit;

    public FarmDto(String name, int countFruit) {
        this.name = name;
        this.countFruit = countFruit;
    }

    public String getName() {
        return name;
    }

    public int getCountFruit() {
        return countFruit;
    }
}
