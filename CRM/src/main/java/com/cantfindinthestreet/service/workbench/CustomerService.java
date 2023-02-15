package com.cantfindinthestreet.service.workbench;

import com.cantfindinthestreet.bean.workbench.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    List<String> selectAllCustomerNameByName(String customerName);

    Customer selectCustomerByName(String name);
}
