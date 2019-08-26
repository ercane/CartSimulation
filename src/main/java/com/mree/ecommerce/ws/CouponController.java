package com.mree.ecommerce.ws;

import com.mree.ecommerce.common.model.CouponInfo;
import com.mree.ecommerce.entity.Coupon;
import com.mree.ecommerce.service.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupon")
public class CouponController extends BaseController<Coupon, CouponInfo, ICouponService> {

    @Autowired
    private ICouponService couponService;

    @Override
    public ICouponService getService() {
        return couponService;
    }
}
