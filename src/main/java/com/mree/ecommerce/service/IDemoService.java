package com.mree.ecommerce.service;

import com.mree.ecommerce.exception.ServiceException;

public interface IDemoService {
    String prepare() throws ServiceException;

    String demo(Long cartId) throws ServiceException;

}
