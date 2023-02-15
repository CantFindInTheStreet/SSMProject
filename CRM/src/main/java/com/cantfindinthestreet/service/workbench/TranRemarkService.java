package com.cantfindinthestreet.service.workbench;

import com.cantfindinthestreet.bean.workbench.TranRemark;

import java.util.List;

public interface TranRemarkService {
    int insertTranRemarkByList(List<TranRemark> list);

    List<TranRemark> selectTranRemarkForDetailByTranId(String id);
}
