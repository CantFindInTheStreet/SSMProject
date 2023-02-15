package com.cantfindinthestreet.service.workbench;


import com.cantfindinthestreet.bean.workbench.CustomerRemark;

import java.util.List;

public interface CustomerRemarkService {
    int insertCustomerRemarkByList(List<CustomerRemark> list);
}
