package com.example.springbootoauth2backend.controller;

import com.example.springbootoauth2backend.model.dto.ProductsDTO;
import com.example.springbootoauth2backend.service.ProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllProducts() {
        List<ProductsDTO> productsDTO = this.productsService.getAllProducts();
        return ResponseEntity.ok(productsDTO);
    }
}
