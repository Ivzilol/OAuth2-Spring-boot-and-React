package com.example.springbootoauth2backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductsDTO {

    private String name;

    private Integer price;

    private String category;
}
