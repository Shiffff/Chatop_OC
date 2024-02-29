package com.chatop.chatop.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    @NotNull
    private String name;
    @NotNull
    @Min(value=0)
    private int surface;
    @NotNull
    @Min(value=0)
    private int price;
    @NotBlank
    @NotNull
    private String picture;
    private String description;
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Long owner_id;
    @UpdateTimestamp
    private LocalDateTime updated_at;
    @CreationTimestamp
    private Date created_at;
}
