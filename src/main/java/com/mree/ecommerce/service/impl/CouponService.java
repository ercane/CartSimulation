package com.mree.ecommerce.service.impl;

import com.mree.ecommerce.common.model.CouponInfo;
import com.mree.ecommerce.entity.Coupon;
import com.mree.ecommerce.exception.ServiceException;
import com.mree.ecommerce.repo.BaseRepository;
import com.mree.ecommerce.repo.CouponRepository;
import com.mree.ecommerce.service.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CouponService extends BaseService<Coupon, CouponInfo> implements ICouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public BaseRepository<Coupon> getRepo() {
        return couponRepository;
    }

    @Override
    public Coupon getEntityForSave(CouponInfo info) {
        Coupon coupon = new Coupon();
        coupon.fromInfo(info);
        return coupon;
    }

    @Override
    public Coupon getEntityForUpdate(CouponInfo info) {
        Coupon coupon = new Coupon();
        coupon.fromInfo(info);
        return coupon;
    }

    @Override
    public void validateForCreate(CouponInfo info) throws ServiceException {
        checkFields(info);
    }

    @Override
    public void validateForUpdate(CouponInfo info) throws ServiceException {
        if (Objects.isNull(info.getId())) {
            throw new ServiceException("Id cannot be null!");
        }

        checkFields(info);
    }

    private void checkFields(CouponInfo info) throws ServiceException {
        if (Objects.isNull(info.getDiscount())) {
            throw new ServiceException("Discount cannot be null!");
        }

        if (Objects.isNull(info.getMinLimit())) {
            throw new ServiceException("Discount limit cannot be null!");
        }

        if (Objects.isNull(info.getType())) {
            throw new ServiceException("Discount type cannot be null!");
        }
    }

    @Override
    public String getEntityName() throws ServiceException {
        return Coupon.class.getSimpleName();
    }
}
