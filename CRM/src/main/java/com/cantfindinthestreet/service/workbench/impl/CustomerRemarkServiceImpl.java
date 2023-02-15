package com.cantfindinthestreet.service.workbench.impl;

import com.cantfindinthestreet.bean.workbench.CustomerRemark;
import com.cantfindinthestreet.dao.workbench.ClueRemarkMapper;
import com.cantfindinthestreet.dao.workbench.CustomerRemarkMapper;
import com.cantfindinthestreet.service.workbench.CustomerRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerRemarkServiceImpl implements CustomerRemarkService {
    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;

    @Override
    public int insertCustomerRemarkByList(List<CustomerRemark> list) {
        return customerRemarkMapper.insertCustomerRemarkByList(list);
    }
}
