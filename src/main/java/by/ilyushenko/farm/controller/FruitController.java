package by.ilyushenko.farm.controller;

import by.ilyushenko.farm.entity.Fruit;
import by.ilyushenko.farm.service.FruitServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fruits")
@Tag(name = "Fruit Management", description = "APIs for managing fruits")
public class FruitController {
    
    private final FruitServiceInterface fruitService;
    
    @Autowired
    public FruitController(FruitServiceInterface fruitService) {
        this.fruitService = fruitService;
    }
    
    @GetMapping
    @Operation(summary = "Get all fruits", description = "Retrieve a list of all fruits with their farm information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of fruits"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Fruit>> getAllFruits() {
        List<Fruit> fruits = fruitService.getAllFruits();
        return ResponseEntity.ok(fruits);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get fruit by ID", description = "Retrieve a specific fruit by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved fruit"),
            @ApiResponse(responseCode = "404", description = "Fruit not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Fruit> getFruitById(
            @Parameter(description = "Fruit ID", required = true)
            @PathVariable Long id) {
        Optional<Fruit> fruit = fruitService.getFruitById(id);
        return fruit.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @Operation(summary = "Create a new fruit", description = "Create a new fruit and assign it to a farm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Fruit created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data or farm not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Fruit> createFruit(
            @Parameter(description = "Fruit details", required = true)
            @Valid @RequestBody Fruit fruit,
            @Parameter(description = "Farm ID to assign the fruit to", required = true)
            @RequestParam Long farmId) {
        Fruit createdFruit = fruitService.createFruit(fruit, farmId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFruit);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update fruit", description = "Update an existing fruit and optionally change its farm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fruit updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data or farm not found"),
            @ApiResponse(responseCode = "404", description = "Fruit not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Fruit> updateFruit(
            @Parameter(description = "Fruit ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated fruit details", required = true)
            @Valid @RequestBody Fruit fruitDetails,
            @Parameter(description = "Farm ID to assign the fruit to", required = true)
            @RequestParam Long farmId) {
        Fruit updatedFruit = fruitService.updateFruit(id, fruitDetails, farmId);
        return ResponseEntity.ok(updatedFruit);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete fruit", description = "Delete a fruit by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Fruit deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Fruit not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteFruit(
            @Parameter(description = "Fruit ID", required = true)
            @PathVariable Long id) {
        fruitService.deleteFruit(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/farm/{farmId}")
    @Operation(summary = "Get fruits by farm ID", description = "Retrieve all fruits belonging to a specific farm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved fruits for the farm"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Fruit>> getFruitsByFarmId(
            @Parameter(description = "Farm ID", required = true)
            @PathVariable Long farmId) {
        List<Fruit> fruits = fruitService.getFruitsByFarmId(farmId);
        return ResponseEntity.ok(fruits);
    }
}
