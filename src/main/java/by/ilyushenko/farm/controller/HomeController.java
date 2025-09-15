package by.ilyushenko.farm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "Home", description = "Home and health check endpoints")
public class HomeController {
    
    @GetMapping("/")
    @Operation(summary = "Welcome message", description = "Returns a welcome message and API information")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to Farm Management API");
        response.put("version", "1.0.0");
        response.put("swagger-ui", "/swagger-ui.html");
        response.put("api-docs", "/api-docs");
        response.put("h2-console", "/h2-console");
        return response;
    }
    
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Returns the health status of the application")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Farm Management API");
        return response;
    }
}
