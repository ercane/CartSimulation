package com.mree.ecommerce.common.ref;

import java.security.InvalidParameterException;

public enum DiscountType {
    AMOUNT(0),
    RATE(1);

    private Integer code;

    DiscountType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public DiscountType get(Integer i) {
        for (DiscountType type : DiscountType.values()) {
            if (type.getCode().intValue() == i.intValue())
                return type;
        }

        throw new InvalidParameterException("There is no suitable enum for " + i);
    }
}
