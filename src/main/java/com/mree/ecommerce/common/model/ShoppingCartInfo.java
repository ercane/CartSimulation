package com.mree.ecommerce.common.model;

import lombok.Data;

import java.util.List;

@Data
public class ShoppingCartInfo extends BaseInfo {
    private List<ProductInfo> productList;
    private List<CampaignInfo> campaignList;
    private CouponInfo coupon;


}
