package com.cantfindinthestreet.service.settings.impl;

import com.cantfindinthestreet.bean.settings.DicValue;
import com.cantfindinthestreet.dao.settings.DicValueMapper;
import com.cantfindinthestreet.service.settings.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.List;

@Service
public class DicValueServiceImpl implements DicValueService {
    @Autowired
    private DicValueMapper dicValueMapper;

    @Override
    public List<DicValue> selectDicValueByTypeCode(String typeCode) {
        return dicValueMapper.selectDicValueByTypeCode(typeCode);
    }
}
