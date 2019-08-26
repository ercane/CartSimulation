package com.mree.ecommerce.service;

import com.mree.ecommerce.common.model.CampaignInfo;
import com.mree.ecommerce.common.model.ShoppingCartInfo;
import com.mree.ecommerce.entity.ShoppingCart;
import com.mree.ecommerce.exception.ServiceException;

import java.util.List;

public interface IShoppingCartService extends IBaseService<ShoppingCart, ShoppingCartInfo> {
    ShoppingCartInfo addProduct(Long cartId, Long productId, Long amount) throws ServiceException;

    ShoppingCartInfo applyDiscounts(Long cartId, List<CampaignInfo> campaignInfos) throws ServiceException;

    ShoppingCartInfo applyCoupon(Long cartId, Long couponId) throws ServiceException;

    Double getTotalAmountAfterDiscounts(Long cartId) throws ServiceException;

    Double getCampaignDiscount(Long cartId) throws ServiceException;

    Double getCouponDiscount(Long cartId) throws ServiceException;

    Double getDeliveryDiscount(Long cartId) throws ServiceException;

    String print(Long cartId) throws ServiceException;
}
