package com.mree.ecommerce.service.impl;

import com.mree.ecommerce.common.model.CampaignInfo;
import com.mree.ecommerce.entity.Campaign;
import com.mree.ecommerce.entity.Category;
import com.mree.ecommerce.exception.ServiceException;
import com.mree.ecommerce.repo.BaseRepository;
import com.mree.ecommerce.repo.CampaignRepository;
import com.mree.ecommerce.service.ICampaignService;
import com.mree.ecommerce.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CampaignService extends BaseService<Campaign, CampaignInfo> implements ICampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private ICategoryService categoryService;

    @Override
    public BaseRepository<Campaign> getRepo() {
        return campaignRepository;
    }

    @Override
    public Campaign getEntityForSave(CampaignInfo info) throws ServiceException {
        Campaign campaign = new Campaign();
        campaign.fromInfo(info);
        if (info.getCategory() != null && info.getCategory().getId() != null) {
            Category category = categoryService.findById(info.getCategory().getId());
            campaign.setCategory(category);
        }
        return campaign;
    }

    @Override
    public Campaign getEntityForUpdate(CampaignInfo info) throws ServiceException {
        Campaign campaign = new Campaign();
        campaign.fromInfo(info);
        if (info.getCategory() != null && info.getCategory().getId() != null) {
            Category category = categoryService.findById(info.getCategory().getId());
            campaign.setCategory(category);
        }
        return campaign;
    }

    @Override
    public void validateForCreate(CampaignInfo info) throws ServiceException {
        checkFields(info);
    }

    @Override
    public void validateForUpdate(CampaignInfo info) throws ServiceException {
        if (Objects.isNull(info.getId())) {
            throw new ServiceException("Id cannot be null!");
        }
        checkFields(info);
    }

    private void checkFields(CampaignInfo info) throws ServiceException {
        if (Objects.isNull(info.getCount())) {
            throw new ServiceException("Product count cannot be null!");
        }

        if (Objects.isNull(info.getValue())) {
            throw new ServiceException("Discount value cannot be null!");
        }

        if (Objects.isNull(info.getCategory()) || Objects.isNull(info.getCategory().getId())) {
            throw new ServiceException("Discount category cannot be null!");
        }

        if (Objects.isNull(info.getType())) {
            throw new ServiceException("Discount type cannot be null!");
        }

    }

    @Override
    public String getEntityName() throws ServiceException {
        return Campaign.class.getSimpleName();
    }
}
