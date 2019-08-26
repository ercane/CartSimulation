package com.mree.ecommerce.entity;

import com.mree.ecommerce.common.model.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Data
@Entity
public class CartCoupon extends BaseEntity<BaseInfo> {

    @OneToOne
    private Coupon coupon;

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
