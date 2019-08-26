package com.mree.ecommerce.entity;

import com.mree.ecommerce.common.model.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Data
@Entity
public class CartProduct extends BaseEntity<BaseInfo> {

    @OneToOne
    private Product product;

    @OneToOne
    private ShoppingCart shoppingCart;

    @Override
    public BaseInfo toInfo() {
        return null;
    }

    @Override
    public void fromInfo(BaseInfo info) {

    }
}
