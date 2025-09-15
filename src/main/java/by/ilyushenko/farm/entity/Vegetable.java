package by.ilyushenko.farm.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "vegetables")
public class Vegetable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Vegetable name is required")
    @Size(min = 2, max = 50, message = "Vegetable name must be between 2 and 50 characters")
    @Column(nullable = false, length = 50)
    private String name;
    
    @Size(max = 50, message = "Color must not exceed 50 characters")
    @Column(length = 50)
    private String color;
    
    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be greater than 0")
    @Column(nullable = false)
    private Double weight;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id", nullable = false)
    @JsonBackReference
    private Farm farm;
    
    // Constructors
    public Vegetable() {}
    
    public Vegetable(String name, String color, Double weight, Farm farm) {
        this.name = name;
        this.color = color;
        this.weight = weight;
        this.farm = farm;
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
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public Double getWeight() {
        return weight;
    }
    
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    
    public Farm getFarm() {
        return farm;
    }
    
    public void setFarm(Farm farm) {
        this.farm = farm;
    }
    
    @Override
    public String toString() {
        return "Vegetable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", weight=" + weight +
                ", farm=" + (farm != null ? farm.getName() : "null") +
                '}';
    }
}
