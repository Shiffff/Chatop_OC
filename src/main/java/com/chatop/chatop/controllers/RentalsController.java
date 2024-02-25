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
