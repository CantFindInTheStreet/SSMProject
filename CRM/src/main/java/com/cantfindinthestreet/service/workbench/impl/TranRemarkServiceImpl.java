package com.cantfindinthestreet.service.workbench.impl;

import com.cantfindinthestreet.bean.workbench.TranRemark;
import com.cantfindinthestreet.dao.workbench.TranRemarkMapper;
import com.cantfindinthestreet.service.workbench.TranRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TranRemarkServiceImpl implements TranRemarkService {
    @Autowired
    private TranRemarkMapper tranRemarkMapper;

    @Override
    public int insertTranRemarkByList(List<TranRemark> list) {
        return tranRemarkMapper.insertTranRemarkByList(list);
    }

    @Override
    public List<TranRemark> selectTranRemarkForDetailByTranId(String id) {
        return tranRemarkMapper.selectTranRemarkForDetailByTranId(id);
    }
}
