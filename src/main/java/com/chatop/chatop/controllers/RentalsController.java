package com.chatop.chatop.controllers;

import com.chatop.chatop.dto.AddRentalDTO;
import com.chatop.chatop.dto.GetRentalDTO;
import com.chatop.chatop.dto.UpdateRentalDTO;
import com.chatop.chatop.entity.Rentals;
import com.chatop.chatop.schemaResApi.messageSchema;
import com.chatop.chatop.services.RentalsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "multipart/form-data",
                            schema = @Schema(implementation = AddRentalDTO.class)
                    )
            )
    )
    @PostMapping("")
    public ResponseEntity<HashMap<String, String>> addRental(@ModelAttribute AddRentalDTO addRentalDTO) {
        try {
            boolean result = rentalsService.addRental(addRentalDTO);
            String message = result ? "Location ajoutée avec succès" : "Erreur lors de l'ajout de la location";
            HttpStatus status = result ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
            HashMap<String, String> map = new HashMap<>();
            map.put("message", message);
            return new ResponseEntity<>(map, status);
        } catch (ConstraintViolationException ex) {
            throw ex; // Laissez cette exception être gérée par l'Advice
        } catch (Exception ex) {
            throw new RuntimeException("Erreur lors de l'ajout de la location", ex);
        }
    }



    @GetMapping("")
    public ResponseEntity<Map<String, List<GetRentalDTO>>> getAllRentals() {
        List<Rentals> allRentals = rentalsService.getAllRentals();
        List<GetRentalDTO> allRentalsDTO = new ArrayList<>();
        for (Rentals rental : allRentals) {
            GetRentalDTO rentalDTO = new GetRentalDTO(rental);
            allRentalsDTO.add(rentalDTO);
        }

        Map<String, List<GetRentalDTO>> response = new HashMap<>();
        response.put("rentals", allRentalsDTO);

        if (!allRentalsDTO.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<GetRentalDTO> getRentalById(
            @Parameter(description = "ID of the rental") @PathVariable Long id) {
        Rentals rental = rentalsService.getRentalById(id);
        if (rental != null) {
            GetRentalDTO rentalDTO = new GetRentalDTO(rental);
            return new ResponseEntity<>(rentalDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

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
