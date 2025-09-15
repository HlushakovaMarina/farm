package by.ilyushenko.farm.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "farms")
public class Farm {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Farm name is required")
    @Size(min = 2, max = 100, message = "Farm name must be between 2 and 100 characters")
    @Column(nullable = false, length = 100)
    private String name;
    
    @Size(max = 255, message = "Location must not exceed 255 characters")
    @Column(length = 255)
    private String location;
    
    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Vegetable> vegetables = new ArrayList<>();
    
    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Fruit> fruits = new ArrayList<>();
    
    // Constructors
    public Farm() {}
    
    public Farm(String name, String location) {
        this.name = name;
        this.location = location;
    }
    
    // Getters and Setters
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
    
    public List<Vegetable> getVegetables() {
        return vegetables;
    }
    
    public void setVegetables(List<Vegetable> vegetables) {
        this.vegetables = vegetables;
    }
    
    public List<Fruit> getFruits() {
        return fruits;
    }
    
    public void setFruits(List<Fruit> fruits) {
        this.fruits = fruits;
    }
    
    // Helper methods
    public void addVegetable(Vegetable vegetable) {
        vegetables.add(vegetable);
        vegetable.setFarm(this);
    }
    
    public void removeVegetable(Vegetable vegetable) {
        vegetables.remove(vegetable);
        vegetable.setFarm(null);
    }
    
    public void addFruit(Fruit fruit) {
        fruits.add(fruit);
        fruit.setFarm(this);
    }
    
    public void removeFruit(Fruit fruit) {
        fruits.remove(fruit);
        fruit.setFarm(null);
    }
    
    @Override
    public String toString() {
        return "Farm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
