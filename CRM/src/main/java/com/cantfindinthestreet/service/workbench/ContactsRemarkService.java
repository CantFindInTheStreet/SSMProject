package com.cantfindinthestreet.service.workbench;

import com.cantfindinthestreet.bean.workbench.ContactsRemark;

import java.util.List;

public interface ContactsRemarkService {
    int insertContactsRemarkByList(List<ContactsRemark> list);
}
