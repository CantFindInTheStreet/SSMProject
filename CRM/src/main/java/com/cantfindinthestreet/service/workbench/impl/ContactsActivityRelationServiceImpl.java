package com.cantfindinthestreet.service.workbench.impl;

import com.cantfindinthestreet.bean.workbench.ContactsActivityRelation;
import com.cantfindinthestreet.dao.workbench.ContactsActivityRelationMapper;
import com.cantfindinthestreet.service.workbench.ContactsActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactsActivityRelationServiceImpl implements ContactsActivityRelationService {
    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;

    @Override
    public int insertContactsActivityRelationByList(List<ContactsActivityRelation> list) {
        return contactsActivityRelationMapper.insertContactsActivityRelationByList(list);
    }
}
