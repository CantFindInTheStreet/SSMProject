package com.cantfindinthestreet.service.workbench.impl;

import com.cantfindinthestreet.bean.workbench.ClueActivityRelation;
import com.cantfindinthestreet.dao.workbench.ClueActivityRelationMapper;
import com.cantfindinthestreet.service.workbench.ClueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {
    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;

    @Override
    public int insertClueActivityRelationByList(List<ClueActivityRelation> clueActivityRelations) {
        return clueActivityRelationMapper.insertClueActivityRelationByList(clueActivityRelations);
    }

    @Override
    public int deleteClueActivityRelationByCludIdActivityId(ClueActivityRelation clueActivityRelation) {
        return clueActivityRelationMapper.deleteClueActivityRelationByCludIdActivityId(clueActivityRelation);
    }

    @Override
    public List<ClueActivityRelation> selectClueActivityRelationByClueId(String id) {
        return clueActivityRelationMapper.selectClueActivityRelationByClueId(id);
    }

    @Override
    public int deleteClueActivityRelationByCludId(String id) {
        return clueActivityRelationMapper.deleteClueActivityRelationByCludId(id);
    }
}
