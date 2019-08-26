package com.mree.ecommerce.ws;

import com.mree.ecommerce.common.model.CampaignInfo;
import com.mree.ecommerce.common.model.ShoppingCartInfo;
import com.mree.ecommerce.entity.ShoppingCart;
import com.mree.ecommerce.exception.ServiceException;
import com.mree.ecommerce.service.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController extends BaseController<ShoppingCart, ShoppingCartInfo, IShoppingCartService> {

    @Autowired
    private IShoppingCartService shoppingCartService;

    @Override
    public IShoppingCartService getService() {
        return shoppingCartService;
    }

    @PostMapping("/add/{cartId}/{productId}/{amount}")
    public ShoppingCartInfo addProduct(
            @PathVariable Long cartId,
            @PathVariable Long productId,
            @PathVariable Long amount) throws ServiceException {
        return getService().addProduct(cartId, productId, amount);
    }

    @PostMapping("/applyDiscounts/{cartId}")
    public ShoppingCartInfo applyDiscounts(
            @PathVariable Long cartId,
            @RequestBody List<CampaignInfo> campaignInfos) throws ServiceException {
        return getService().applyDiscounts(cartId, campaignInfos);
    }

    @GetMapping("/applyCoupon/{cartId}/{couponId}")
    public ShoppingCartInfo applyCoupon(
            @PathVariable Long cartId,
            @PathVariable Long couponId) throws ServiceException {
        return getService().applyCoupon(cartId, couponId);
    }

    @GetMapping("/getTotalAmountAfterDiscounts/{cartId}")
    public Double getTotalAmountAfterDiscounts(@PathVariable Long cartId) throws ServiceException {
        return getService().getTotalAmountAfterDiscounts(cartId);
    }

    @GetMapping("/getDeliveryDiscount/{cartId}")
    public Double getDeliveryDiscount(@PathVariable Long cartId) throws ServiceException {
        return getService().getDeliveryDiscount(cartId);
    }

    @GetMapping("/print/{cartId}")
    public String print(@PathVariable Long cartId) throws ServiceException {
        return getService().print(cartId);
    }
}
