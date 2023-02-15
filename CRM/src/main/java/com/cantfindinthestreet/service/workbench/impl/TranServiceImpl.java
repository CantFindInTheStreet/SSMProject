package com.cantfindinthestreet.service.workbench.impl;

import com.cantfindinthestreet.bean.commons.GongJv;
import com.cantfindinthestreet.bean.settings.User;
import com.cantfindinthestreet.bean.workbench.Customer;
import com.cantfindinthestreet.bean.workbench.FunnelVO;
import com.cantfindinthestreet.bean.workbench.Tran;
import com.cantfindinthestreet.bean.workbench.TranHistory;
import com.cantfindinthestreet.dao.workbench.CustomerMapper;
import com.cantfindinthestreet.dao.workbench.TranHistoryMapper;
import com.cantfindinthestreet.dao.workbench.TranMapper;
import com.cantfindinthestreet.service.workbench.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TranServiceImpl implements TranService {
    @Autowired
    private TranMapper tranMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private TranHistoryMapper tranHistoryMapper;


    @Override
    public int insertTran(Tran tran) {
        return tranMapper.insertTran(tran);
    }

    @Override
    public void saveCreateTran(Map<String, Object> map) {
        String customerName =(String) map.get("customerName");
        User user=(User) map.get(GongJv.USERNAME);
        Customer customer = customerMapper.selectCustomerByName(customerName);
        if(customer==null){
            customer=new Customer();
            customer.setOwner(user.getId());
            customer.setName(customerName);
            customer.setId(GongJv.getUUID());
            customer.setCreateTime(GongJv.DateGeShi1(new Date()));
            customer.setCreateBy(user.getId());
            customerMapper.insertCustomer(customer);
        }
        Tran tran=new Tran();
        tran.setStage((String) map.get("stage"));
        tran.setOwner((String) map.get("owner"));
        tran.setNextContactTime((String) map.get("nextContactTime"));
        tran.setName((String) map.get("name"));
        tran.setMoney((String) map.get("money"));
        tran.setId(GongJv.getUUID());
        tran.setExpectedDate((String) map.get("expectedDate"));
        tran.setCustomerId(customer.getId());
        tran.setCreateTime(GongJv.DateGeShi1(new Date()));
        tran.setCreateBy(user.getId());
        tran.setContactsId((String) map.get("contactsId"));
        tran.setContactSummary((String) map.get("contactSummary"));
        tran.setActivityId((String) map.get("activityId"));
        tran.setDescription((String) map.get("description"));
        tran.setSource((String) map.get("source"));
        tran.setType((String) map.get("type"));
        tranMapper.insertTran(tran);
        TranHistory tranHistory = new TranHistory();
        tranHistory.setCreateBy(user.getId());
        tranHistory.setCreateTime(GongJv.DateGeShi1(new Date()));
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setId(GongJv.getUUID());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setStage(tran.getStage());
        tranHistory.setTranId(tran.getId());
        tranHistoryMapper.insertTranHistory(tranHistory);
    }

    @Override
    public Tran selectTranForDetailById(String id) {
        return tranMapper.selectTranForDetailById(id);
    }

    @Override
    public List<FunnelVO> selectCountOfTranGroupByStage() {
        return tranMapper.selectCountOfTranGroupByStage();
    }


}
