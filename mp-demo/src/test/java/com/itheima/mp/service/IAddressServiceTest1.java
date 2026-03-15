package com.itheima.mp.service;

import com.itheima.mp.domain.po.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IAddressServiceTest1 {


    @Autowired
    private IAddressService addressService;

    @Test
    void testDelete(){
        Address address = addressService.getById(3L);
        System.out.println("address = " + address);
        addressService.removeById(3L);
        address = addressService.getById(3L);
        System.out.println("address = " + address);
    }

}