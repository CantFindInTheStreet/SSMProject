package com.cantfindinthestreet.service.workbench.impl;

import com.cantfindinthestreet.bean.commons.GongJv;
import com.cantfindinthestreet.bean.settings.User;
import com.cantfindinthestreet.bean.workbench.*;
import com.cantfindinthestreet.dao.workbench.*;
import com.cantfindinthestreet.service.workbench.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    private ClueMapper clueMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ContactsMapper contactsMapper;
    @Autowired
    private ClueRemarkMapper clueRemarkMapper;
    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;
    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;
    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;
    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;
    @Autowired
    private TranMapper tranMapper;
    @Autowired
    private TranRemarkMapper tranRemarkMapper;

    @Override
    public int insertClue(Clue clue) {
        return clueMapper.insertClue(clue);
    }

    @Override
    public Clue selectClueForDetailById(String id) {
        return clueMapper.selectClueForDetailById(id);
    }

    @Override
    public Clue selectClueById(String id) {
        return clueMapper.selectClueById(id);
    }

    @Override
    public void savaConvertClue(Map<String, Object> map) {
        String clueId =(String) map.get("clueId");
        User user=(User) map.get(GongJv.USERNAME);
        String isCreateTran =(String)map.get("isCreateTran");
        Clue clue = clueMapper.selectClueById(clueId);
        Customer customer = new Customer();
        customer.setAddress(clue.getAddress());
        customer.setContactSummary(clue.getContactSummary());
        customer.setId(GongJv.getUUID());
        customer.setCreateTime(GongJv.DateGeShi1(new Date()));
        customer.setDescription(clue.getDescription());
        customer.setCreateBy(user.getId());
        customer.setName(clue.getCompany());
        customer.setNextContactTime(clue.getNextContactTime());
        customer.setOwner(user.getId());
        customer.setPhone(clue.getPhone());
        customer.setWebsite(clue.getWebsite());
        int i = customerMapper.insertCustomer(customer);
        Contacts contacts=new Contacts();
        contacts.setAddress(clue.getAddress());
        contacts.setAppellation(clue.getAppellation());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setCreateBy(user.getId());
        contacts.setCreateTime(GongJv.DateGeShi1(new Date()));
        contacts.setCustomerId(clue.getId());
        contacts.setDescription(clue.getDescription());
        contacts.setEmail(clue.getEmail());
        contacts.setFullname(clue.getFullname());
        contacts.setId(GongJv.getUUID());
        contacts.setJob(clue.getJob());
        contacts.setMphone(clue.getMphone());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setOwner(user.getId());
        contacts.setSource(clue.getSource());
        int i1 = contactsMapper.insertContacts(contacts);
        List<ClueRemark> clueRemarks = clueRemarkMapper.selectClueRemarkByClueId(clueId);
        if(clueRemarks!=null&&clueRemarks.size()>0){
            CustomerRemark customerRemark=null;
            List<CustomerRemark> list=new ArrayList<>();
            ContactsRemark contactsRemark=null;
            List<ContactsRemark> contactsRemarkList=new ArrayList<>();
            for(ClueRemark c:clueRemarks){
                customerRemark = new CustomerRemark();
                customerRemark.setId(GongJv.getUUID());
                customerRemark.setNoteContent(c.getNoteContent());
                customerRemark.setCreateBy(c.getCreateBy());
                customerRemark.setCreateTime(c.getCreateTime());
                customerRemark.setEditBy(c.getEditBy());
                customerRemark.setEditTime(c.getEditTime());
                customerRemark.setEditFlag(c.getEditFlag());
                customerRemark.setCustomerId(customer.getId());
                list.add(customerRemark);
                //------------------------------
                contactsRemark=new ContactsRemark();
                contactsRemark.setContactsId(customer.getId());
                contactsRemark.setCreateBy(c.getCreateBy());
                contactsRemark.setCreateTime(c.getCreateTime());
                contactsRemark.setEditBy(c.getEditBy());
                contactsRemark.setEditTime(c.getEditTime());
                contactsRemark.setEditFlag(c.getEditFlag());
                contactsRemark.setId(GongJv.getUUID());
                contactsRemark.setNoteContent(c.getNoteContent());
                contactsRemarkList.add(contactsRemark);
            }
            int i2 = customerRemarkMapper.insertCustomerRemarkByList(list);
            int i3 = contactsRemarkMapper.insertContactsRemarkByList(contactsRemarkList);
        }
        List<ClueActivityRelation> clueActivityRelations = clueActivityRelationMapper.selectClueActivityRelationByClueId(clueId);
        if(clueActivityRelations!=null&&clueActivityRelations.size()>0){
            ContactsActivityRelation c1=null;
            List<ContactsActivityRelation> coar=new ArrayList<>();
            for(ClueActivityRelation car:clueActivityRelations){
                c1=new ContactsActivityRelation();
                c1.setId(GongJv.getUUID());
                c1.setActivityId(car.getActivityId());
                c1.setContactsId(contacts.getId());
                coar.add(c1);
            }
            int i4 = contactsActivityRelationMapper.insertContactsActivityRelationByList(coar);
        }
        if("true".equals(isCreateTran)){
            Tran tran = new Tran();
            tran.setActivityId((String)map.get("activityId"));
            tran.setContactsId(contacts.getId());
            tran.setCreateBy(user.getId());
            tran.setCreateTime(GongJv.DateGeShi1(new Date()));
            tran.setCustomerId(customer.getId());
            tran.setExpectedDate((String) map.get("expectedDate"));
            tran.setId(GongJv.getUUID());
            tran.setMoney((String)map.get("money"));
            tran.setName((String)map.get("name"));
            tran.setOwner(user.getId());
            tran.setStage((String)map.get("stage"));
            int i5 = tranMapper.insertTran(tran);
            //---------------------------------------------
            if(clueRemarks!=null&&clueRemarks.size()>0){
                TranRemark tranRemark=null;
                List<TranRemark> list=new ArrayList<>();
                for(ClueRemark c:clueRemarks){
                    tranRemark=new TranRemark();
                    tranRemark.setCreateBy(c.getCreateBy());
                    tranRemark.setCreateTime(c.getCreateTime());
                    tranRemark.setEditBy(c.getEditBy());
                    tranRemark.setEditTime(c.getEditTime());
                    tranRemark.setEditFlag(c.getEditFlag());
                    tranRemark.setId(GongJv.getUUID());
                    tranRemark.setNoteContent(c.getNoteContent());
                    tranRemark.setTranId(tran.getId());
                    list.add(tranRemark);
                }
                int i6 = tranRemarkMapper.insertTranRemarkByList(list);
            }
            int i7 = clueRemarkMapper.deleteClueRemarkByClueId(clueId);
            int i8 = clueActivityRelationMapper.deleteClueActivityRelationByCludId(clueId);
            int i9 = clueMapper.deleteClueById(clueId);
        }


    }

    @Override
    public int deleteClueById(String id) {
        return clueMapper.deleteClueById(id);
    }
}
