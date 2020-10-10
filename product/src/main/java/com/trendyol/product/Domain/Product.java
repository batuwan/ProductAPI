package com.trendyol.product.Domain;


import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String id;
    private String category;
    private double price;
    private String description;
    private int quantity;



}

