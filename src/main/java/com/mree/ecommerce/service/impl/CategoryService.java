package com.mree.ecommerce.service.impl;

import com.mree.ecommerce.common.model.CategoryInfo;
import com.mree.ecommerce.entity.Category;
import com.mree.ecommerce.exception.ServiceException;
import com.mree.ecommerce.repo.BaseRepository;
import com.mree.ecommerce.repo.CategoryRepository;
import com.mree.ecommerce.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CategoryService extends BaseService<Category, CategoryInfo> implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public BaseRepository<Category> getRepo() {
        return categoryRepository;
    }

    @Override
    public Category getEntityForSave(CategoryInfo info) throws ServiceException {
        Category category = new Category();
        category.fromInfo(info);
        if (info.getParentCategory() != null && info.getParentCategory().getId() != null) {
            Category parent = findById(info.getParentCategory().getId());
            category.setParentCategory(parent);
        }
        return category;
    }

    @Override
    public Category getEntityForUpdate(CategoryInfo info) throws ServiceException {
        Category category = new Category();
        category.fromInfo(info);
        if (info.getParentCategory() != null && info.getParentCategory().getId() != null) {
            Category parent = findById(info.getParentCategory().getId());
            category.setParentCategory(parent);
        }
        return category;
    }

    @Override
    public void validateForCreate(CategoryInfo info) throws ServiceException {
        checkFields(info);
    }

    @Override
    public void validateForUpdate(CategoryInfo info) throws ServiceException {
        if (Objects.isNull(info.getId())) {
            throw new ServiceException("Id cannot be null!");
        }

        checkFields(info);
    }

    private void checkFields(CategoryInfo info) throws ServiceException {
        if (Objects.isNull(info.getTitle())) {
            throw new ServiceException("Title cannot be null!");
        }
    }

    @Override
    public String getEntityName() throws ServiceException {
        return Category.class.getSimpleName();
    }
}
