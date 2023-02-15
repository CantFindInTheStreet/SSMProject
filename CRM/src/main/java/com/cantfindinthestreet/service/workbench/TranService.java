package com.cantfindinthestreet.service.workbench;

import com.cantfindinthestreet.bean.workbench.FunnelVO;
import com.cantfindinthestreet.bean.workbench.Tran;

import java.util.List;
import java.util.Map;

public interface TranService {
    int insertTran(Tran tran);

    void saveCreateTran(Map<String,Object> map);

    Tran selectTranForDetailById(String id);

    List<FunnelVO> selectCountOfTranGroupByStage();
}
