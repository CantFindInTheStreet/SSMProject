package com.cantfindinthestreet.service.workbench;

import com.cantfindinthestreet.bean.workbench.ContactsActivityRelation;

import java.util.List;

public interface ContactsActivityRelationService {
    int insertContactsActivityRelationByList(List<ContactsActivityRelation> list);
}
