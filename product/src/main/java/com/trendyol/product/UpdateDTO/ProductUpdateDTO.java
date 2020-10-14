package com.trendyol.product.UpdateDTO;

import lombok.Getter;

@Getter
public class ProductUpdateDTO {
    private String category;
    private Double price;
    private String description;
    private Integer quantity;

    protected ProductUpdateDTO(){ }


}
