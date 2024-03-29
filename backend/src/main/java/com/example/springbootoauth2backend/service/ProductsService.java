package com.example.springbootoauth2backend.service;

import com.example.springbootoauth2backend.model.dto.ProductsDTO;
import com.example.springbootoauth2backend.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;

    public List<ProductsDTO> getAllProducts() {
        return this.productsRepository.findAllProducts();
    }
}
