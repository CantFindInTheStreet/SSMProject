package com.cantfindinthestreet.service.workbench;

import com.cantfindinthestreet.bean.workbench.ClueRemark;

import java.util.List;

public interface ClueRemarkService {
    List<ClueRemark> selectClueRemarkForDetailByClueId(String id);

    List<ClueRemark> selectClueRemarkByClueId(String id);

    int deleteClueRemarkByClueId(String id);
}
