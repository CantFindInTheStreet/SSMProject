package com.cantfindinthestreet.service.workbench;

import com.cantfindinthestreet.bean.workbench.Clue;

import java.util.Map;

public interface ClueService {
    int insertClue(Clue clue);

    Clue selectClueForDetailById(String id);

    Clue selectClueById(String id);

    void savaConvertClue(Map<String,Object> map);

    int deleteClueById(String id);
}
