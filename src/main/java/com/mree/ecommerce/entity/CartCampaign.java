package com.mree.ecommerce.entity;

import com.mree.ecommerce.common.model.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Data
@Entity
public class CartCampaign extends BaseEntity<BaseInfo> {

    @OneToOne
    private Campaign campaign;

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
