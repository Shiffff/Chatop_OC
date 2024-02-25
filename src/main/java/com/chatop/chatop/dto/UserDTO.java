package com.chatop.chatop.dto;
import com.chatop.chatop.entity.User;
import java.time.LocalDateTime;
import java.util.Date;


public class UserDTO {
    public String email;
    public Long id;
    public String name;
    public Date created_at;
    public LocalDateTime updated_at;

    public UserDTO(User user) {
        this.email = user.getEmail();
        this.id = user.getId();
        this.updated_at = user.getUpdated_at();
        this.name = user.getName();
        this.created_at = user.getCreated_at();

    }

}