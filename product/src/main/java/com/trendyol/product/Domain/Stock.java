package com.trendyol.product.Domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class Stock {
    private String id;
    private String itemId;
    private int quantity;
}
