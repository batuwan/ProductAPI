package com.trendyol.product.Domain;


import lombok.*;

import java.util.UUID;


@Getter
@Setter
@Builder
//@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String id;
    private String category;
    private double price;
    private String description;
    private int quantity;

    public Product(){
        this.id = UUID.randomUUID().toString();
    }



}

