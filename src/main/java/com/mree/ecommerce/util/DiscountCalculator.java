package com.mree.ecommerce.util;

import com.mree.ecommerce.entity.Campaign;
import com.mree.ecommerce.entity.CartCampaign;
import com.mree.ecommerce.entity.CartProduct;
import com.mree.ecommerce.entity.ShoppingCart;
import com.mree.ecommerce.exception.ServiceException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DiscountCalculator {

    public Double getTotalAmount(ShoppingCart cart) throws ServiceException {
        Double totalAmount = 0.0;
        for (CartProduct cp : cart.getProductList()) {
            totalAmount += cp.getProduct().getPrice();
        }

        return totalAmount;
    }

    public Double getTotalAmountAfterDiscounts(ShoppingCart cart) throws ServiceException {

        Double totalAmount = getTotalAmount(cart);
        Double campaignDiscount = getCampaignDiscount(cart);
        Double couponDiscount = getCouponDiscount(cart);
        return totalAmount - (campaignDiscount + couponDiscount);
    }

    public Double getCampaignDiscount(ShoppingCart cart) throws ServiceException {

        Map<Long, Integer> productCountMap = new HashMap<>();
        Map<Long, Double> productTotalPriceMap = new HashMap<>();
        for (CartProduct cp : cart.getProductList()) {
            Integer count = productCountMap.get(cp.getProduct().getCategory().getId());
            if (count == null) {
                count = 1;
            } else {
                count++;
            }
            productCountMap.put(cp.getProduct().getCategory().getId(), count);

            Double price = productTotalPriceMap.get(cp.getProduct().getCategory().getId());
            if (price == null) {
                price = cp.getProduct().getPrice();
            } else {
                price += cp.getProduct().getPrice();
            }
            productTotalPriceMap.put(cp.getProduct().getCategory().getId(), price);

        }

        Double totalDiscount = 0.0;
        for (CartCampaign cc : cart.getCampaignList()) {
            Campaign campaign = cc.getCampaign();
            Integer count = productCountMap.get(campaign.getCategory().getId());
            if (count >= campaign.getCount()) {
                switch (campaign.getType()) {
                    case AMOUNT:
                        totalDiscount += campaign.getValue();
                        break;
                    case RATE:
                        Double price = productTotalPriceMap.get(campaign.getCategory().getId());
                        Double discount = price * campaign.getValue() / 100;
                        totalDiscount += discount;
                        break;
                }
            }
        }

        return totalDiscount;

    }

    public Double getCouponDiscount(ShoppingCart cart) throws ServiceException {
        Double totalAmount = getTotalAmount(cart);

        Double campaignDiscount = getCampaignDiscount(cart);

        Double discount = 0.0;
        if (cart.getCoupon().getCoupon().getMinLimit() <= totalAmount - campaignDiscount) {
            switch (cart.getCoupon().getCoupon().getType()) {
                case AMOUNT:
                    discount = cart.getCoupon().getCoupon().getDiscount().doubleValue();
                    break;
                case RATE:
                    Double totalWithCampaigns = totalAmount - campaignDiscount;
                    discount = totalWithCampaigns * cart.getCoupon().getCoupon().getDiscount() / 100;
                    break;
            }
        }


        return discount;
    }
}
