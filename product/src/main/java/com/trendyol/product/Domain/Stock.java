package com.trendyol.product.Domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Stock {
    private String id;
    private String itemId;
    private int quantity;

    public Stock(){
        this.id = UUID.randomUUID().toString();
    }
}
