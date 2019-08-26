package com.mree.ecommerce.service.impl;

import com.mree.ecommerce.common.model.CampaignInfo;
import com.mree.ecommerce.common.model.CategoryInfo;
import com.mree.ecommerce.common.model.CouponInfo;
import com.mree.ecommerce.common.model.ProductInfo;
import com.mree.ecommerce.common.model.ShoppingCartInfo;
import com.mree.ecommerce.common.ref.DiscountType;
import com.mree.ecommerce.exception.ServiceException;
import com.mree.ecommerce.service.ICampaignService;
import com.mree.ecommerce.service.ICategoryService;
import com.mree.ecommerce.service.ICouponService;
import com.mree.ecommerce.service.IDemoService;
import com.mree.ecommerce.service.IProductService;
import com.mree.ecommerce.service.IShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DemoService implements IDemoService {
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ICampaignService campaignService;

    @Autowired
    private ICouponService couponService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IShoppingCartService cartService;

    @Override
    public String prepare() throws ServiceException {
        StringBuilder sb = new StringBuilder();

        ShoppingCartInfo cartInfo = new ShoppingCartInfo();
        cartInfo = cartService.create(cartInfo).toInfo();
        sb.append(String.format("Sepet yaratıldı. Id: %d\n", cartInfo.getId()));

        CategoryInfo yiyecek = new CategoryInfo("Yiyecek");
        yiyecek = categoryService.create(yiyecek).toInfo();
        sb.append("Yiyecek kategorisi yaratıldı\n");

        CategoryInfo meyve = new CategoryInfo("Meyve");
        meyve.setParentCategory(yiyecek);
        CategoryInfo sebze = new CategoryInfo("Sebze");
        sebze.setParentCategory(yiyecek);

        meyve = categoryService.create(meyve).toInfo();
        sb.append("Meyve kategorisi yaratıldı\n");
        sebze = categoryService.create(sebze).toInfo();
        sb.append("Sebze kategorisi yaratıldı\n");

        ProductInfo elma = new ProductInfo("Elma", 6.99, meyve);
        ProductInfo lahana = new ProductInfo("Lahana", 2.99, sebze);

        elma = productService.create(elma).toInfo();
        sb.append("Elma yaratıldı\n");
        lahana = productService.create(lahana).toInfo();
        sb.append("Lahana yaratıldı\n");

        CampaignInfo campaign1 = new CampaignInfo(meyve, 5.0, 2, DiscountType.AMOUNT);
        CampaignInfo campaign2 = new CampaignInfo(meyve, 10.0, 2, DiscountType.RATE);
        CampaignInfo campaign3 = new CampaignInfo(sebze, 7.5, 2, DiscountType.AMOUNT);
        CampaignInfo campaign4 = new CampaignInfo(sebze, 15.0, 2, DiscountType.RATE);

        campaign1 = campaignService.create(campaign1).toInfo();
        campaign2 = campaignService.create(campaign2).toInfo();
        campaign3 = campaignService.create(campaign3).toInfo();
        campaign4 = campaignService.create(campaign4).toInfo();
        sb.append("4 kampanya yaratıldı.\n");

        CouponInfo coupon = new CouponInfo(50.0, 5, DiscountType.RATE);
        coupon = couponService.create(coupon).toInfo();
        sb.append("1 kupon yaratıldı.\n");

        cartService.addProduct(cartInfo.getId(), elma.getId(), 10l);
        sb.append("10 kilo elma sepete atıldı.");
        cartService.addProduct(cartInfo.getId(), lahana.getId(), 5l);
        sb.append("5 demet lahana sepete atıldı.\n");

        List<CampaignInfo> campaignList = new ArrayList<CampaignInfo>();
        campaignList.add(campaign1);
        campaignList.add(campaign2);
        campaignList.add(campaign3);
        campaignList.add(campaign4);
        cartService.applyDiscounts(cartInfo.getId(), campaignList);
        sb.append("4 kampanya sepete uygulandı.\n");

        cartService.applyCoupon(cartInfo.getId(), coupon.getId());
        sb.append("Kupon sepete uygulandı.\n");

        log.debug(sb.toString());
        return sb.toString();
    }

    @Override
    public String demo(Long cartId) throws ServiceException {
        return cartService.print(cartId);
    }
}
