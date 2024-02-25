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
@Table(name = "RENTALS")
public class Rentals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int surface;
    private int price;
    private String picture;
    private String description;
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Long owner_id;

    @UpdateTimestamp
    private LocalDateTime updated_at;
    @CreationTimestamp
    private Date created_at;
}
