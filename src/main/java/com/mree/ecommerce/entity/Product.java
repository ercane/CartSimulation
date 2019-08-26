package com.mree.ecommerce.entity;


import com.mree.ecommerce.common.model.ProductInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Objects;

@Data
@Entity
public class Product extends BaseEntity<ProductInfo> {
    private String title;
    private Double price;

    @OneToOne
    private Category category;

    public Product() {
    }

    @Override
    public ProductInfo toInfo() {
        ProductInfo info = new ProductInfo();
        BeanUtils.copyProperties(this, info);
        if (Objects.nonNull(getCategory())) {
            info.setCategory(getCategory().toInfo());
        }
        return info;
    }

    @Override
    public void fromInfo(ProductInfo info) {
        BeanUtils.copyProperties(info, this);
    }

    @Override
    public boolean equals(Object obj) {
        try {
            Product p = (Product) obj;
            return getId().longValue() == p.getId().longValue();
        } catch (Exception e) {
            return false;
        }
    }
}
