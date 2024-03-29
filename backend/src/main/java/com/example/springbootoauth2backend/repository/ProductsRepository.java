package com.example.springbootoauth2backend.repository;

import com.example.springbootoauth2backend.model.dto.ProductsDTO;
import com.example.springbootoauth2backend.model.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsEntity, Long> {

    @Query("select new com.example.springbootoauth2backend.model.dto.ProductsDTO(" +
            "p.name, p.price, c.name)" +
            " from ProductsEntity as p" +
            " join CategoryEntity as c on p.id = c.id")
    List<ProductsDTO> findAllProducts();
}
