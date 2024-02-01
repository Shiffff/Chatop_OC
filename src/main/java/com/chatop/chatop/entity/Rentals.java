package com.chatop.chatop.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rentals")
public class Rentals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int surface;
    private int price;
    private String picture;
    private String description;
    private Integer  owner_id;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @CreationTimestamp
    private Date createdAt;


}
