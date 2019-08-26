package com.mree.ecommerce.ws;

import com.mree.ecommerce.exception.ServiceException;
import com.mree.ecommerce.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private IDemoService demoService;

    @GetMapping("/prepare")
    public String prepare() throws ServiceException {
        return demoService.prepare();
    }

    @GetMapping("/demo/{cartId}")
    public String demo(@PathVariable Long cartId) throws ServiceException {
        return demoService.demo(cartId);
    }
}
