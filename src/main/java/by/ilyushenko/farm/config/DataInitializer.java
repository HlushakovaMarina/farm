package by.ilyushenko.farm.config;

import by.ilyushenko.farm.entity.Farm;
import by.ilyushenko.farm.entity.Fruit;
import by.ilyushenko.farm.entity.Vegetable;
import by.ilyushenko.farm.service.FarmServiceInterface;
import by.ilyushenko.farm.service.FruitServiceInterface;
import by.ilyushenko.farm.service.VegetableServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private FarmServiceInterface farmService;
    
    @Autowired
    private VegetableServiceInterface vegetableService;
    
    @Autowired
    private FruitServiceInterface fruitService;
    
    @Override
    public void run(String... args) throws Exception {
        // Create sample farms
        Farm farm1 = new Farm("Green Valley Farm", "California, USA");
        Farm farm2 = new Farm("Sunny Fields", "Texas, USA");
        Farm farm3 = new Farm("Organic Garden", "Oregon, USA");
        
        farm1 = farmService.createFarm(farm1);
        farm2 = farmService.createFarm(farm2);
        farm3 = farmService.createFarm(farm3);
        
        // Create sample vegetables
        Vegetable carrot = new Vegetable("Carrot", "Orange", 150.0, farm1);
        Vegetable tomato = new Vegetable("Tomato", "Red", 200.0, farm1);
        Vegetable lettuce = new Vegetable("Lettuce", "Green", 100.0, farm2);
        Vegetable cucumber = new Vegetable("Cucumber", "Green", 180.0, farm2);
        Vegetable potato = new Vegetable("Potato", "Brown", 300.0, farm3);
        
        vegetableService.createVegetable(carrot, farm1.getId());
        vegetableService.createVegetable(tomato, farm1.getId());
        vegetableService.createVegetable(lettuce, farm2.getId());
        vegetableService.createVegetable(cucumber, farm2.getId());
        vegetableService.createVegetable(potato, farm3.getId());
        
        // Create sample fruits
        Fruit apple = new Fruit("Apple", "Red", 250.0, farm1);
        Fruit banana = new Fruit("Banana", "Yellow", 120.0, farm1);
        Fruit orange = new Fruit("Orange", "Orange", 200.0, farm2);
        Fruit strawberry = new Fruit("Strawberry", "Red", 15.0, farm2);
        Fruit grape = new Fruit("Grape", "Purple", 50.0, farm3);
        
        fruitService.createFruit(apple, farm1.getId());
        fruitService.createFruit(banana, farm1.getId());
        fruitService.createFruit(orange, farm2.getId());
        fruitService.createFruit(strawberry, farm2.getId());
        fruitService.createFruit(grape, farm3.getId());
        
        System.out.println("Sample data initialized successfully!");
    }
}
