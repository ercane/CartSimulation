package com.mree.ecommerce.util;

import com.mree.ecommerce.entity.CartProduct;
import com.mree.ecommerce.entity.Product;
import com.mree.ecommerce.entity.ShoppingCart;
import com.mree.ecommerce.exception.ServiceException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Data
@Component
public class DeliveryCostCalculator {


    @Value("${ecommerce.costPerDelivery}")
    private Double costPerDelivery;

    @Value("${ecommerce.costPerProduct}")
    private Double costPerProduct;

    @Value("${ecommerce.fixedCost}")
    private Double fixedCost;

    public DeliveryCostCalculator() {
    }

    public DeliveryCostCalculator(Double costPerDelivery, Double costPerProduct, Double fixedCost) {
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
        this.fixedCost = fixedCost;
    }

    public Double calculateFor(ShoppingCart cart) throws ServiceException {
        Set<Long> categorySet = new HashSet<>();
        Set<Long> productSet = new HashSet<>();

        for (CartProduct cp : cart.getProductList()) {
            Product product = cp.getProduct();
            categorySet.add(product.getCategory().getId());
            productSet.add(product.getId());
        }

        return (costPerDelivery * categorySet.size()) + (costPerProduct * productSet.size()) + fixedCost;
    }

}
