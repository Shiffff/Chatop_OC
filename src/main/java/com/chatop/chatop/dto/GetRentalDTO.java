package com.chatop.chatop.dto;



public class GetRentalDTO {
    public Long id;

    public String name;
    public int surface;
    public int price;
    public String description;
    public String picture;

    public GetRentalDTO(Long id, String name, int surface, int price, String description, String picture) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.description = description;
        this.picture = picture;
    }
}
