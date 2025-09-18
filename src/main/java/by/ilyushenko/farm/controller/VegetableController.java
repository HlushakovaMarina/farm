package by.ilyushenko.farm.controller;

import by.ilyushenko.farm.entity.Vegetable;
import by.ilyushenko.farm.service.VegetableServiceInterface;
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
@RequestMapping("/api/vegetables")
@Tag(name = "Vegetable Management", description = "APIs for managing vegetables")
public class VegetableController {
    
    private final VegetableServiceInterface vegetableService;
    
    @Autowired
    public VegetableController(VegetableServiceInterface vegetableService) {
        this.vegetableService = vegetableService;
    }
    
    @GetMapping
    @Operation(summary = "Get all vegetables", description = "Retrieve a list of all vegetables with their farm information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of vegetables"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Vegetable>> getAllVegetables() {
        List<Vegetable> vegetables = vegetableService.getAllVegetables();
        return ResponseEntity.ok(vegetables);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get vegetable by ID", description = "Retrieve a specific vegetable by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved vegetable"),
            @ApiResponse(responseCode = "404", description = "Vegetable not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Vegetable> getVegetableById(
            @Parameter(description = "Vegetable ID", required = true)
            @PathVariable Long id) {
        Optional<Vegetable> vegetable = vegetableService.getVegetableById(id);
        return vegetable.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @Operation(summary = "Create a new vegetable", description = "Create a new vegetable and assign it to a farm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vegetable created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data or farm not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Vegetable> createVegetable(
            @Parameter(description = "Vegetable details", required = true)
            @Valid @RequestBody Vegetable vegetable,
            @Parameter(description = "Farm ID to assign the vegetable to", required = true)
            @RequestParam Long farmId) {
        Vegetable createdVegetable = vegetableService.createVegetable(vegetable, farmId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVegetable);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update vegetable", description = "Update an existing vegetable and optionally change its farm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vegetable updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data or farm not found"),
            @ApiResponse(responseCode = "404", description = "Vegetable not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Vegetable> updateVegetable(
            @Parameter(description = "Vegetable ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated vegetable details", required = true)
            @Valid @RequestBody Vegetable vegetableDetails,
            @Parameter(description = "Farm ID to assign the vegetable to", required = true)
            @RequestParam Long farmId) {
        Vegetable updatedVegetable = vegetableService.updateVegetable(id, vegetableDetails, farmId);
        return ResponseEntity.ok(updatedVegetable);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete vegetable", description = "Delete a vegetable by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vegetable deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Vegetable not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteVegetable(
            @Parameter(description = "Vegetable ID", required = true)
            @PathVariable Long id) {
        vegetableService.deleteVegetable(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/farm/{farmId}")
    @Operation(summary = "Get vegetables by farm ID", description = "Retrieve all vegetables belonging to a specific farm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved vegetables for the farm"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Vegetable>> getVegetablesByFarmId(
            @Parameter(description = "Farm ID", required = true)
            @PathVariable Long farmId) {
        List<Vegetable> vegetables = vegetableService.getVegetablesByFarmId(farmId);
        return ResponseEntity.ok(vegetables);
    }
    @GetMapping("/getByColorAndWeight")
    public ResponseEntity<List<Vegetable>>getVegetableByColorAndWeight
            (@RequestParam(name = "color") String color,
             @RequestParam(name = "weight") Double weight){
        List<Vegetable> vegetables = vegetableService.findByColorAndWeight(color, weight);
        return ResponseEntity.ok(vegetables);
    }

}
