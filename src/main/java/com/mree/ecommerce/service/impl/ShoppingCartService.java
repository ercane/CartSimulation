package com.mree.ecommerce.service.impl;

import com.mree.ecommerce.common.model.CampaignInfo;
import com.mree.ecommerce.common.model.ShoppingCartInfo;
import com.mree.ecommerce.entity.Campaign;
import com.mree.ecommerce.entity.CartCampaign;
import com.mree.ecommerce.entity.CartCoupon;
import com.mree.ecommerce.entity.CartProduct;
import com.mree.ecommerce.entity.Category;
import com.mree.ecommerce.entity.Coupon;
import com.mree.ecommerce.entity.Product;
import com.mree.ecommerce.entity.ShoppingCart;
import com.mree.ecommerce.exception.ServiceException;
import com.mree.ecommerce.repo.BaseRepository;
import com.mree.ecommerce.repo.CartCampaignRepository;
import com.mree.ecommerce.repo.CartCouponRepository;
import com.mree.ecommerce.repo.CartProductRepository;
import com.mree.ecommerce.repo.ShoppingCartRepository;
import com.mree.ecommerce.service.ICampaignService;
import com.mree.ecommerce.service.ICouponService;
import com.mree.ecommerce.service.IProductService;
import com.mree.ecommerce.service.IShoppingCartService;
import com.mree.ecommerce.util.DeliveryCostCalculator;
import com.mree.ecommerce.util.DiscountCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class ShoppingCartService extends BaseService<ShoppingCart, ShoppingCartInfo> implements IShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private IProductService productService;

    @Autowired
    private ICampaignService campaignService;

    @Autowired
    private ICouponService couponService;

    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private CartCampaignRepository cartCampaignRepository;

    @Autowired
    private CartCouponRepository cartCouponRepository;

    @Autowired
    private DiscountCalculator discountCalculator;

    @Autowired
    private DeliveryCostCalculator deliveryCostCalculator;

    @Override
    public BaseRepository<ShoppingCart> getRepo() {
        return shoppingCartRepository;
    }

    @Override
    public ShoppingCart getEntityForSave(ShoppingCartInfo info) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.fromInfo(info);
        return shoppingCart;
    }

    @Override
    public ShoppingCart getEntityForUpdate(ShoppingCartInfo info) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.fromInfo(info);
        return shoppingCart;
    }

    @Override
    public void validateForCreate(ShoppingCartInfo info) throws ServiceException {
        checkFields(info);
    }

    @Override
    public void validateForUpdate(ShoppingCartInfo info) throws ServiceException {
        if (Objects.isNull(info.getId())) {
            throw new ServiceException("Id cannot be null!");
        }

        checkFields(info);
    }

    private void checkFields(ShoppingCartInfo info) throws ServiceException {

    }

    @Override
    public String getEntityName() throws ServiceException {
        return ShoppingCart.class.getSimpleName();
    }

    @Override
    public ShoppingCartInfo addProduct(Long cartId, Long productId, Long amount) throws ServiceException {
        log.debug(String.format("Add product to cart operation. CartId: %d, ProductId: %d, Amount: %d ", cartId, productId, amount));
        ShoppingCart cart = findById(cartId);

        if (cart == null) {
            throw new ServiceException("Cart cannot be found. Id: " + cartId);
        }

        cart.setProductList(null);
        Product product = productService.findById(productId);
        if (product == null) {
            throw new ServiceException("Product cannot be found. Id: " + productId);
        }

        for (int i = 0; i < amount; i++) {
            CartProduct cartProduct = new CartProduct();
            cartProduct.setProduct(product);
            cartProduct.setShoppingCart(cart);
            cartProductRepository.saveAndFlush(cartProduct);
        }

        return findById(cartId).toInfo();
    }

    @Override
    public ShoppingCartInfo applyDiscounts(Long cartId, List<CampaignInfo> campaignInfos) throws ServiceException {
        ShoppingCart cart = findById(cartId);

        for (CampaignInfo ci : campaignInfos) {
            Campaign campaign = campaignService.findById(ci.getId());
            CartCampaign cc = new CartCampaign();
            cc.setShoppingCart(cart);
            cc.setCampaign(campaign);
            cartCampaignRepository.saveAndFlush(cc);
        }

        return findById(cartId).toInfo();
    }

    @Override
    public ShoppingCartInfo applyCoupon(Long cartId, Long couponId) throws ServiceException {

        ShoppingCart cart = findById(cartId);
        Coupon coupon = couponService.findById(couponId);

        CartCoupon cartCoupon = new CartCoupon();
        cartCoupon.setShoppingCart(cart);
        cartCoupon.setCoupon(coupon);
        cart.setCoupon(cartCoupon);

        cartCouponRepository.save(cartCoupon);
        return findById(cartId).toInfo();
    }


    @Override
    public Double getTotalAmountAfterDiscounts(Long cartId) throws ServiceException {
        log.debug(String.format("Get Total Amount After Discounts operation. CartId: %d", cartId));
        ShoppingCart cart = findById(cartId);
        return discountCalculator.getTotalAmountAfterDiscounts(cart);
    }


    @Override
    public Double getCampaignDiscount(Long cartId) throws ServiceException {
        log.debug(String.format("Get Campaign Discount operation. CartId: %d", cartId));
        ShoppingCart cart = findById(cartId);
        return discountCalculator.getCampaignDiscount(cart);
    }


    @Override
    public Double getCouponDiscount(Long cartId) throws ServiceException {
        log.debug(String.format("Get Coupon Discount operation. CartId: %d", cartId));
        ShoppingCart cart = findById(cartId);
        return discountCalculator.getCouponDiscount(cart);
    }

    @Override
    public Double getDeliveryDiscount(Long cartId) throws ServiceException {
        log.debug(String.format("Get Coupon Discount operation. CartId: %d", cartId));
        ShoppingCart cart = findById(cartId);
        return deliveryCostCalculator.calculateFor(cart);
    }

    @Override
    public String print(Long cartId) throws ServiceException {
        log.debug(String.format("Print operation. CartId: %d", cartId));
        ShoppingCart cart = findById(cartId);
        Map<Category, Map<Product, Integer>> categoryMap = new HashMap<>();
        for (CartProduct cp : cart.getProductList()) {
            Product product = cp.getProduct();
            Map<Product, Integer> productQuantityMap = categoryMap.get(product.getCategory());
            if (productQuantityMap == null) {
                productQuantityMap = new HashMap<>();
            }

            Integer count = productQuantityMap.get(product);
            if (count == null) {
                count = 1;
            } else {
                count++;
            }

            productQuantityMap.put(product, count);

            categoryMap.put(product.getCategory(), productQuantityMap);
        }
        StringBuilder sb = new StringBuilder();
        for (Category c : categoryMap.keySet()) {
            sb.append(String.format("%s\n", c.getTitle()));
            Map<Product, Integer> productQuantityMap = categoryMap.get(c);
            for (Product p : productQuantityMap.keySet()) {
                Integer quantity = productQuantityMap.get(p);
                sb.append(String.format("\t%s\n\t\tQuantity: %d\n\t\tUnit Price: %f\n\t\tTotal Price: %f\n"
                        , p.getTitle(), quantity, p.getPrice(), quantity * p.getPrice()));
            }
        }
        sb.append(String.format("Total Amount: %f\n", discountCalculator.getTotalAmount(cart)));
        sb.append(String.format("Campaign Discount: %f\n", discountCalculator.getCampaignDiscount(cart)));
        sb.append(String.format("Coupon Discount: %f\n", discountCalculator.getCouponDiscount(cart)));
        sb.append(String.format("Total Amount After Discounts: %f\n", discountCalculator.getTotalAmountAfterDiscounts(cart)));
        sb.append(String.format("Delivery cost: %f\n", deliveryCostCalculator.calculateFor(cart)));


        log.debug(sb.toString());
        return sb.toString();
    }


}
