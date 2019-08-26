package com.mree.ecommerce.service;

import com.mree.ecommerce.common.model.BaseInfo;
import com.mree.ecommerce.entity.BaseEntity;
import com.mree.ecommerce.exception.ServiceException;

import java.util.List;

public interface IBaseService<E extends BaseEntity, I extends BaseInfo> {

    E create(I info) throws ServiceException;

    E update(I info) throws ServiceException;

    void delete(Long id) throws ServiceException;

    E findById(Long id) throws ServiceException;

    List<E> findAll() throws ServiceException;

}
