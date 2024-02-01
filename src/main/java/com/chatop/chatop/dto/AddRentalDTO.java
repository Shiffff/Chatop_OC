package com.chatop.chatop.dto;

import org.springframework.web.multipart.MultipartFile;

public record AddRentalDTO(String name, int surface, int price, String description, MultipartFile picture) {
}
