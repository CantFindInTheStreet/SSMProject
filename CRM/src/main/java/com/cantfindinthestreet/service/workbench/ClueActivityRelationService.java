package com.cantfindinthestreet.service.workbench;

import com.cantfindinthestreet.bean.workbench.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationService {

    int insertClueActivityRelationByList(List<ClueActivityRelation> clueActivityRelations);

    int deleteClueActivityRelationByCludIdActivityId(ClueActivityRelation clueActivityRelation);

    List<ClueActivityRelation> selectClueActivityRelationByClueId(String id);

    int deleteClueActivityRelationByCludId(String id);
}
