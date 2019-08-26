package com.mree.ecommerce.entity;

import com.mree.ecommerce.common.model.CampaignInfo;
import com.mree.ecommerce.common.model.ProductInfo;
import com.mree.ecommerce.common.model.ShoppingCartInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
public class ShoppingCart extends BaseEntity<ShoppingCartInfo> {

    @OneToMany(mappedBy = "shoppingCart")
    private List<CartProduct> productList;

    @OneToMany(mappedBy = "shoppingCart")
    private List<CartCampaign> campaignList;

    @OneToOne
    private CartCoupon coupon;

    public ShoppingCart() {
        productList = new ArrayList<>();
        campaignList = new ArrayList<>();
    }

    @Override
    public ShoppingCartInfo toInfo() {
        ShoppingCartInfo i = new ShoppingCartInfo();
        BeanUtils.copyProperties(this, i);
        if (Objects.nonNull(getProductList())) {
            List<ProductInfo> list = new ArrayList<>();
            getProductList().stream().forEach(cp -> list.add(cp.getProduct().toInfo()));
            i.setProductList(list);
        }

        if (Objects.nonNull(getCampaignList())) {
            List<CampaignInfo> list = new ArrayList<>();
            getCampaignList().stream().forEach(cp -> list.add(cp.getCampaign().toInfo()));
            i.setCampaignList(list);
        }

        if (Objects.nonNull(getCoupon())) {
            i.setCoupon(getCoupon().getCoupon().toInfo());
        }

        return i;
    }

    @Override
    public void fromInfo(ShoppingCartInfo info) {
        BeanUtils.copyProperties(info, this);
    }
}
