package com.mree.ecommerce.ws;

import com.mree.ecommerce.common.model.BaseInfo;
import com.mree.ecommerce.entity.BaseEntity;
import com.mree.ecommerce.exception.ServiceException;
import com.mree.ecommerce.service.IBaseService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public abstract class BaseController<E extends BaseEntity<I>, I extends BaseInfo, S extends IBaseService<E, I>> {

    public abstract S getService();

    @PostMapping
    public I create(@RequestBody I info) throws ServiceException {
        E e = getService().create(info);
        return e.toInfo();
    }

    @PutMapping
    public I update(@RequestBody I info) throws ServiceException {
        E e = getService().update(info);
        return e.toInfo();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws ServiceException {
        getService().delete(id);
    }

    @GetMapping("/{id}")
    public I findById(@PathVariable Long id) throws ServiceException {
        E e = getService().findById(id);
        return e.toInfo();
    }

    @GetMapping
    public List<I> findAll() throws ServiceException {
        List<E> all = getService().findAll();
        List<I> infoList = new ArrayList<>();
        all.stream().forEach(e -> infoList.add(e.toInfo()));
        return infoList;
    }


}
