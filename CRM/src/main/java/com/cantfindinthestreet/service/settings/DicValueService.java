package com.cantfindinthestreet.service.settings;

import com.cantfindinthestreet.bean.settings.DicValue;

import java.util.List;

public interface DicValueService {
    List<DicValue> selectDicValueByTypeCode(String typeCode);

}
