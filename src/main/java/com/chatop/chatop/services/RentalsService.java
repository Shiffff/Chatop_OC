package com.chatop.chatop.services;

import com.chatop.chatop.dto.AddRentalDTO;
import com.chatop.chatop.dto.UpdateRentalDTO;
import com.chatop.chatop.entity.Rentals;
import com.chatop.chatop.entity.User;
import com.chatop.chatop.repository.RentalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class RentalsService {

    private final RentalsRepository rentalsRepository;

    @Autowired
    public RentalsService(RentalsRepository rentalsRepository) {
        this.rentalsRepository = rentalsRepository;
    }

    public boolean addRental(AddRentalDTO addRentalDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            // Enregistrer l'image sur le disque
            String picturePath = savePictureOnDisk(addRentalDTO.picture());

            // Créer une nouvelle instance de Rentals
            Rentals rental = new Rentals();
            rental.setName(addRentalDTO.name());
            rental.setSurface(addRentalDTO.surface());
            rental.setPrice(addRentalDTO.price());
            rental.setDescription(addRentalDTO.description());
            rental.setPicture(picturePath);
            rental.setOwner_id(user.getId()); // Définir l'owner_id avec l'ID de l'utilisateur connecté

            // Enregistrer les détails de la location dans la base de données
            rentalsRepository.save(rental);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private String savePictureOnDisk(MultipartFile picture) throws IOException {
        String uploadDir = System.getProperty("user.dir") + File.separator + "src/main/resources/public";
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }
        String originalFileName = picture.getOriginalFilename();
        String fileName = generateUniqueFileName(originalFileName);
        File file = new File(uploadPath, fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(picture.getBytes());
        fileOutputStream.close();
        return "http://localhost:3001/api/public/" + fileName;
    }

    private String generateUniqueFileName(String originalFileName) {
        String fileNameWithoutSpaces = originalFileName.replace(" ", "_");
        String uniqueFileName = generateUniqueIdentifier() + "_" + fileNameWithoutSpaces;
        return uniqueFileName;
    }
    private static long idCounter = 0;

    private String generateUniqueIdentifier() {
        return String.valueOf(System.currentTimeMillis()) + "-" + (++idCounter);
    }

    public List<Rentals> getAllRentals() {
        return rentalsRepository.findAll();
    }

    public Rentals getRentalById(Long id) {
        return rentalsRepository.findById(id).orElse(null);
    }
    public boolean updateRental(Long id, UpdateRentalDTO updateRentalDTO) {
        Rentals existingRental = rentalsRepository.findById(id).orElse(null);

        if (existingRental != null) {
            if (updateRentalDTO.name() != null) {
                existingRental.setName(updateRentalDTO.name());
            }
            if (updateRentalDTO.surface() != null) {
                existingRental.setSurface(updateRentalDTO.surface());
            }
            if (updateRentalDTO.price() != null) {
                existingRental.setPrice(updateRentalDTO.price());
            }
            if (updateRentalDTO.description() != null) {
                existingRental.setDescription(updateRentalDTO.description());
            }

            rentalsRepository.save(existingRental);
            return true;
        } else {
            return false;
        }
    }


}