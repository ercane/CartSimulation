package com.mree.ecommerce.entity;

import com.mree.ecommerce.common.model.CategoryInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Data
@Entity
public class Category extends BaseEntity<CategoryInfo> {
    private String title;

    @OneToOne
    private Category parentCategory;

    public Category() {
    }


    @Override
    public CategoryInfo toInfo() {
        CategoryInfo i = new CategoryInfo();
        BeanUtils.copyProperties(this, i);
        if (getParentCategory() != null) {
            i.setParentCategory(getParentCategory().toInfo());
        }
        return i;
    }

    @Override
    public void fromInfo(CategoryInfo info) {
        BeanUtils.copyProperties(info, this);
    }

    @Override
    public boolean equals(Object obj) {
        try {
            Category p = (Category) obj;
            return getId().longValue() == p.getId().longValue();
        } catch (Exception e) {
            return false;
        }
    }
}
