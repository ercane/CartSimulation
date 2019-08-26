package com.mree.ecommerce.ws;

import com.mree.ecommerce.common.model.CategoryInfo;
import com.mree.ecommerce.entity.Category;
import com.mree.ecommerce.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController<Category, CategoryInfo, ICategoryService> {

    @Autowired
    private ICategoryService categoryService;

    @Override
    public ICategoryService getService() {
        return categoryService;
    }
}
