package com.mree.ecommerce.common.model;

import com.mree.ecommerce.common.ref.DiscountType;
import lombok.Data;

@Data
public class CouponInfo extends BaseInfo {
    private Double minLimit;
    private Integer discount;
    private DiscountType type;

    public CouponInfo() {
    }

    public CouponInfo(Double minLimit, Integer discount, DiscountType type) {
        this.minLimit = minLimit;
        this.discount = discount;
        this.type = type;
    }
}
