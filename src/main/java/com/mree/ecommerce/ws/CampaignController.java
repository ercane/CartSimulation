package com.mree.ecommerce.ws;

import com.mree.ecommerce.common.model.CampaignInfo;
import com.mree.ecommerce.entity.Campaign;
import com.mree.ecommerce.service.ICampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/campaign")
public class CampaignController extends BaseController<Campaign, CampaignInfo, ICampaignService> {

    @Autowired
    private ICampaignService campaignService;

    @Override
    public ICampaignService getService() {
        return campaignService;
    }
}
