package com.example.springbootoauth2backend.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_table")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name = "price")
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private CategoryEntity category;
}
