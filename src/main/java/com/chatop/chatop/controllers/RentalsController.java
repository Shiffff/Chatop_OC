package com.chatop.chatop.controllers;

import com.chatop.chatop.dto.AddRentalDTO;
import com.chatop.chatop.dto.UpdateRentalDTO;
import com.chatop.chatop.entity.Rentals;
import com.chatop.chatop.services.RentalsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rentals")
@SecurityRequirement(name = "auth")
public class RentalsController {

    private final RentalsService rentalsService;

    @Autowired
    public RentalsController(RentalsService rentalsService) {
        this.rentalsService = rentalsService;
    }

    @Operation(
            summary = "Add a new rental",
            description = "Endpoint to add a new rental",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Rental added successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "multipart/form-data",
                            schema = @Schema(implementation = AddRentalDTO.class)
                    )
            )
    )
    @PostMapping("")
    public ResponseEntity<HashMap<String, String>> addRental(@ModelAttribute AddRentalDTO addRentalDTO) {

        String message;
        HttpStatus status;
        boolean result = rentalsService.addRental(addRentalDTO);
        if (result) {
            message = "Location ajoutée avec succès";
            status = HttpStatus.OK;
        } else {
            message = "Erreur lors de l'ajout de la location";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("message", message);
        return new ResponseEntity<>(map, status);
    }

    @Operation(
            summary = "Get all rentals",
            description = "Endpoint to retrieve all rentals",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of all rentals")
            }
    )
    @GetMapping("")
    public ResponseEntity<Map<String, List<Rentals>>> getAllRentals() {
        List<Rentals> allRentals = rentalsService.getAllRentals();
        Map<String, List<Rentals>> response = new HashMap<>();
        response.put("rentals", allRentals);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Get a rental by ID",
            description = "Endpoint to retrieve a rental by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Rental found"),
                    @ApiResponse(responseCode = "404", description = "Rental not found")
            }
    )

    @GetMapping("/{id}")
    public ResponseEntity<Rentals> getRentalById(
            @Parameter(description = "ID of the rental") @PathVariable Long id) {
        Rentals rental = rentalsService.getRentalById(id);
        if (rental != null) {
            return new ResponseEntity<>(rental, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Update a rental by ID",
            description = "Endpoint to update a rental by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Rental updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Rental not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String, String>> updateRental(
            @Parameter(description = "ID of the rental") @PathVariable Long id,
            @ModelAttribute UpdateRentalDTO updateRentalDTO) {
        String message;
        HttpStatus status;
        boolean result = rentalsService.updateRental(id, updateRentalDTO);
        if (result) {
            message = "Location mise à jour avec succès";
            status = HttpStatus.OK;
        } else {
            message = "Erreur location non mise à jour";
            status = HttpStatus.NOT_FOUND;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("message", message);
        return new ResponseEntity<>(map, status);
    }
}
