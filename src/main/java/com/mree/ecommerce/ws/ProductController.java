package com.mree.ecommerce.ws;

import com.mree.ecommerce.common.model.ProductInfo;
import com.mree.ecommerce.entity.Product;
import com.mree.ecommerce.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController extends BaseController<Product, ProductInfo, IProductService> {

    @Autowired
    private IProductService productService;

    @Override
    public IProductService getService() {
        return productService;
    }
}
