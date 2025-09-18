package by.ilyushenko.farm.controller;

import by.ilyushenko.farm.entity.Farm;
import by.ilyushenko.farm.exception.BusinessException;
import by.ilyushenko.farm.exception.GlobalExceptionHandler;
import by.ilyushenko.farm.exception.ResourceNotFoundException;
import by.ilyushenko.farm.service.FarmServiceInterface;
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
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/farms")
@Tag(name = "Farm Management", description = "APIs for managing farms")
public class FarmController {
    
    private final FarmServiceInterface farmService;
    
    @Autowired
    public FarmController(FarmServiceInterface farmService) {
        this.farmService = farmService;
    }


    @GetMapping(value = "/testBusinessException")
    public ResponseEntity<Void> testException(
            @RequestParam String message){
        throw new BusinessException(message);
    }

    @GetMapping
    @Operation(summary = "Get all farms", description = "Retrieve a list of all farms with their vegetables and fruits")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of farms"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })




    public ResponseEntity<List<Farm>> getAllFarms() {
        List<Farm> farms = farmService.getAllFarms();
        return ResponseEntity.ok(farms);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get farm by ID", description = "Retrieve a specific farm by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved farm"),
            @ApiResponse(responseCode = "404", description = "Farm not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Farm> getFarmById(
            @Parameter(description = "Farm ID", required = true)
            @PathVariable Long id) {
        Optional<Farm> farm = farmService.getFarmById(id);
        return farm.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @Operation(summary = "Create a new farm", description = "Create a new farm with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Farm created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Farm> createFarm(
            @Parameter(description = "Farm details", required = true)
            @Valid @RequestBody Farm farm) {
        Farm createdFarm = farmService.createFarm(farm);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFarm);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update farm", description = "Update an existing farm by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Farm updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Farm not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Farm> updateFarm(
            @Parameter(description = "Farm ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated farm details", required = true)
            @Valid @RequestBody Farm farmDetails) {
        Farm updatedFarm = farmService.updateFarm(id, farmDetails);
        return ResponseEntity.ok(updatedFarm);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete farm", description = "Delete a farm by its ID. This will also delete all associated vegetables and fruits.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Farm deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Farm not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteFarm(
            @Parameter(description = "Farm ID", required = true)
            @PathVariable Long id) {
        farmService.deleteFarm(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(("/getByLocation"))
    public ResponseEntity<List<Farm>> getFarmByLocation(
            @RequestParam String location){
        List<Farm> farmByLocation = farmService.getFarmByLocation(location);
        return ResponseEntity.ok(farmByLocation);
    }
}
