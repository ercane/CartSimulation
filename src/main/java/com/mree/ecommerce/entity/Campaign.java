package com.mree.ecommerce.entity;

import com.mree.ecommerce.common.model.CampaignInfo;
import com.mree.ecommerce.common.ref.DiscountType;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Objects;

@Data
@Entity
public class Campaign extends BaseEntity<CampaignInfo> {

    @OneToOne
    private Category category;
    private Double value;
    private Integer count;
    private DiscountType type;

    public Campaign() {
    }

    @Override
    public CampaignInfo toInfo() {
        CampaignInfo c = new CampaignInfo();
        BeanUtils.copyProperties(this, c);
        if (Objects.nonNull(getCategory())) {
            c.setCategory(getCategory().toInfo());
        }
        return c;
    }

    @Override
    public void fromInfo(CampaignInfo info) {
        BeanUtils.copyProperties(info, this);
    }
}
