package com.cantfindinthestreet.service.workbench.impl;

import com.cantfindinthestreet.bean.workbench.TranHistory;
import com.cantfindinthestreet.dao.workbench.TranHistoryMapper;
import com.cantfindinthestreet.service.workbench.TranHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranHistoryServiceImpl implements TranHistoryService {
    @Autowired
    private TranHistoryMapper tranHistoryMapper;

    @Override
    public int insertTranHistory(TranHistory tranHistory) {
        return tranHistoryMapper.insertTranHistory(tranHistory);
    }

    @Override
    public List<TranHistory> selectTranHistoryForDetailByTranId(String id) {
        return tranHistoryMapper.selectTranHistoryForDetailByTranId(id);
    }
}
