package com.mree.ecommerce.service.impl;

import com.mree.ecommerce.common.model.BaseInfo;
import com.mree.ecommerce.entity.BaseEntity;
import com.mree.ecommerce.exception.ServiceException;
import com.mree.ecommerce.repo.BaseRepository;
import com.mree.ecommerce.service.IBaseService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class BaseService<E extends BaseEntity, I extends BaseInfo> implements IBaseService<E, I> {

    public abstract BaseRepository<E> getRepo();

    public abstract E getEntityForSave(I info) throws ServiceException;

    public abstract E getEntityForUpdate(I info) throws ServiceException;

    public abstract void validateForCreate(I info) throws ServiceException;

    public abstract void validateForUpdate(I info) throws ServiceException;

    public abstract String getEntityName() throws ServiceException;

    @Override
    public E create(I info) throws ServiceException {
        log.debug("Create operation for " + getEntityName());
        validateForCreate(info);
        E entity = getEntityForSave(info);
        return getRepo().save(entity);
    }

    @Override
    public E update(I info) throws ServiceException {
        log.debug("Update operation for " + getEntityName());
        validateForUpdate(info);
        E entity = getEntityForUpdate(info);
        return getRepo().save(entity);
    }

    @Override
    public void delete(Long id) throws ServiceException {
        log.debug("Delete operation for " + getEntityName() + " . Id: " + id);
        getRepo().deleteById(id);
    }

    @Override
    public E findById(Long id) throws ServiceException {
        log.debug("FindById operation for " + getEntityName() + " . Id: " + id);
        Optional<E> opt = getRepo().findById(id);

        if (opt == null || opt.get() == null) {
            throw new ServiceException(getEntityName() + " cannot be found. Id: " + id);
        }

        return opt.get();
    }

    @Override
    public List<E> findAll() throws ServiceException {
        log.debug("FindAll operation for " + getEntityName());
        return getRepo().findAll();
    }
}
