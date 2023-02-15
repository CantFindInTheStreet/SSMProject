package com.cantfindinthestreet.service.workbench.impl;

import com.cantfindinthestreet.bean.workbench.ContactsRemark;
import com.cantfindinthestreet.dao.workbench.ContactsRemarkMapper;
import com.cantfindinthestreet.service.workbench.ContactsRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactsRemarkServiceImpl implements ContactsRemarkService {
    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;

    @Override
    public int insertContactsRemarkByList(List<ContactsRemark> list) {
        return contactsRemarkMapper.insertContactsRemarkByList(list);
    }
}
