package com.cantfindinthestreet.service.workbench;

import com.cantfindinthestreet.bean.workbench.TranHistory;

import java.util.List;

public interface TranHistoryService {
    int insertTranHistory(TranHistory tranHistory);

    List<TranHistory> selectTranHistoryForDetailByTranId(String id);
}
