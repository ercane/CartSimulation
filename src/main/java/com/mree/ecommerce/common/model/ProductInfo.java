package com.mree.ecommerce.common.model;


import lombok.Data;

@Data
public class ProductInfo extends BaseInfo {
    private String title;
    private Double price;
    private CategoryInfo category;

    public ProductInfo() {
    }

    public ProductInfo(String title, Double price, CategoryInfo category) {
        this.title = title;
        this.price = price;
        this.category = category;
    }

}
