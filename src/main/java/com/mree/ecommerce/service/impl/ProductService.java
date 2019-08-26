package com.mree.ecommerce.service.impl;

import com.mree.ecommerce.common.model.ProductInfo;
import com.mree.ecommerce.entity.Category;
import com.mree.ecommerce.entity.Product;
import com.mree.ecommerce.exception.ServiceException;
import com.mree.ecommerce.repo.BaseRepository;
import com.mree.ecommerce.repo.ProductRepository;
import com.mree.ecommerce.service.ICategoryService;
import com.mree.ecommerce.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ProductService extends BaseService<Product, ProductInfo> implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ICategoryService categoryService;

    @Override
    public BaseRepository<Product> getRepo() {
        return productRepository;
    }

    @Override
    public Product getEntityForSave(ProductInfo info) throws ServiceException {
        Product product = new Product();
        product.fromInfo(info);

        if (info.getCategory() != null && info.getCategory().getId() != null) {
            Category category = categoryService.findById(info.getCategory().getId());
            product.setCategory(category);
        }
        return product;
    }

    @Override
    public Product getEntityForUpdate(ProductInfo info) throws ServiceException {
        Product product = new Product();
        product.fromInfo(info);

        if (info.getCategory() != null && info.getCategory().getId() != null) {
            Category category = categoryService.findById(info.getCategory().getId());
            product.setCategory(category);
        }

        return product;
    }

    @Override
    public void validateForCreate(ProductInfo info) throws ServiceException {
        checkFields(info);
    }

    @Override
    public void validateForUpdate(ProductInfo info) throws ServiceException {
        if (Objects.isNull(info.getId())) {
            throw new ServiceException("Id cannot be null!");
        }

        checkFields(info);
    }

    private void checkFields(ProductInfo info) throws ServiceException {
        if (Objects.isNull(info.getTitle())) {
            throw new ServiceException("Title cannot be null!");
        }

        if (Objects.isNull(info.getPrice())) {
            throw new ServiceException("Price cannot be null!");
        }

        if (Objects.isNull(info.getCategory()) || Objects.isNull(info.getCategory().getId())) {
            throw new ServiceException("Category cannot be null!");
        }
    }

    @Override
    public String getEntityName() throws ServiceException {
        return Product.class.getSimpleName();
    }
}
