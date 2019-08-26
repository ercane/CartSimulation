package com.mree.ecommerce.entity;

import com.mree.ecommerce.common.model.CouponInfo;
import com.mree.ecommerce.common.ref.DiscountType;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;

@Data
@Entity
public class Coupon extends BaseEntity<CouponInfo> {
    private Double minLimit;
    private Integer discount;
    private DiscountType type;

    @Override
    public CouponInfo toInfo() {
        CouponInfo i = new CouponInfo();
        BeanUtils.copyProperties(this, i);
        return i;
    }

    @Override
    public void fromInfo(CouponInfo info) {
        BeanUtils.copyProperties(info, this);
    }
}
