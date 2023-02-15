package com.cantfindinthestreet.service.workbench.impl;

import com.cantfindinthestreet.bean.workbench.ClueRemark;
import com.cantfindinthestreet.dao.workbench.ClueRemarkMapper;
import com.cantfindinthestreet.service.workbench.ClueRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClueRemarkServiceImpl implements ClueRemarkService {
    @Autowired
    private ClueRemarkMapper clueRemarkMapper;

    @Override
    public List<ClueRemark> selectClueRemarkForDetailByClueId(String id) {
        return clueRemarkMapper.selectClueRemarkForDetailByClueId(id);
    }

    @Override
    public List<ClueRemark> selectClueRemarkByClueId(String id) {
        return clueRemarkMapper.selectClueRemarkByClueId(id);
    }

    @Override
    public int deleteClueRemarkByClueId(String id) {
        return clueRemarkMapper.deleteClueRemarkByClueId(id);
    }
}
