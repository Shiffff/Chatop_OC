package com.chatop.chatop.dto;


import com.chatop.chatop.entity.Rentals;

import java.time.LocalDateTime;
import java.util.Date;

public class GetRentalDTO {
    public Long id;

    public String name;
    public int surface;
    public int price;
    public String description;
    public String picture;

    public int owner_id;

    public Date createdAt;

    public LocalDateTime updatedAt;

    public GetRentalDTO(Rentals rental) {
        this.id = rental.getId();
        this.name = rental.getName();
        this.surface = rental.getSurface();
        this.price = rental.getPrice();
        this.description = rental.getDescription();
        this.picture = rental.getPicture();
        this.owner_id = rental.getOwner_id();
        this.createdAt = rental.getCreatedAt();
        this.updatedAt = rental.getUpdatedAt();
    }
}
