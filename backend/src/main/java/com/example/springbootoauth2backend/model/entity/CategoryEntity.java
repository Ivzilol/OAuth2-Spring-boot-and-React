package com.example.springbootoauth2backend.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category_table")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String name;

}
