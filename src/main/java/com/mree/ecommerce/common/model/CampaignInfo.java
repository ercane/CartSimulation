package com.mree.ecommerce.common.model;

import com.mree.ecommerce.common.ref.DiscountType;
import lombok.Data;

@Data
public class CampaignInfo extends BaseInfo {
    private CategoryInfo category;
    private Double value;
    private Integer count;
    private DiscountType type;

    public CampaignInfo() {
    }

    public CampaignInfo(CategoryInfo category, Double value, Integer count, DiscountType type) {
        this.category = category;
        this.value = value;
        this.count = count;
        this.type = type;
    }


}
