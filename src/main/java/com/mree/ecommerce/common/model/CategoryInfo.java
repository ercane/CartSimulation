package com.mree.ecommerce.common.model;

import lombok.Data;

@Data
public class CategoryInfo extends BaseInfo {
    private String title;
    private CategoryInfo parentCategory;

    public CategoryInfo() {
    }

    public CategoryInfo(String title) {
        this.title = title;
    }

}
