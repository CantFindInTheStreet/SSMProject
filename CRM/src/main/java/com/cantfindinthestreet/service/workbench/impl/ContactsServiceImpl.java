package com.cantfindinthestreet.service.workbench.impl;


import com.cantfindinthestreet.bean.workbench.Contacts;
import com.cantfindinthestreet.dao.workbench.ContactsMapper;
import com.cantfindinthestreet.service.workbench.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactsServiceImpl implements ContactsService {
    @Autowired
    private ContactsMapper contactsMapper;


    @Override
    public int insertContacts(Contacts contacts) {
        return contactsMapper.insertContacts(contacts);
    }
}
