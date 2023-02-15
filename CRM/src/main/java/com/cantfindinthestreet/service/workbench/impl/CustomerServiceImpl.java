package com.cantfindinthestreet.service.workbench.impl;

import com.cantfindinthestreet.bean.commons.GongJv;
import com.cantfindinthestreet.bean.workbench.Customer;
import com.cantfindinthestreet.dao.workbench.CustomerMapper;
import com.cantfindinthestreet.service.workbench.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;


    @Override
    public List<String> selectAllCustomerNameByName(String customerName) {
        return customerMapper.selectAllCustomerNameByName(customerName);
    }

    @Override
    public Customer selectCustomerByName(String name) {
        return customerMapper.selectCustomerByName(name);
    }
}
