package com.cantfindinthestreet.service.workbench.impl;

import com.cantfindinthestreet.bean.workbench.Activity;
import com.cantfindinthestreet.dao.workbench.ActivityMapper;
import com.cantfindinthestreet.service.workbench.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public int insertActivity(Activity activity) {
        return activityMapper.insertActivity(activity);
    }

    @Override
    public List<Activity> selectActivityByConditionForPage(Map<String, Object> map) {
        return activityMapper.selectActivityByConditionForPage(map);
    }

    @Override
    public int countActivityHowMany(Map<String, Object> map) {
        return activityMapper.countActivityHowMany(map);
    }

    @Override
    public int removeActivityByIds(String[] ids) {
        return activityMapper.removeActivityByIds(ids);
    }

    @Override
    public Activity selectActivityById(String id) {
        return activityMapper.selectActivityById(id);
    }

    @Override
    public int updateActivityById(Activity activity) {
        return activityMapper.updateActivityById(activity);
    }

    @Override
    public List<Activity> selectAllActivity() {
        return activityMapper.selectAllActivity();
    }

    @Override
    public List<Activity> selectSomeActivityByIds(String[] ids) {
        return activityMapper.selectSomeActivityByIds(ids);
    }

    @Override
    public int insertActivityByExcel(List<Activity> activityList) {
        return activityMapper.insertActivityByExcel(activityList);
    }

    @Override
    public Activity selectActivityForDetailById(String id) {
        return activityMapper.selectActivityForDetailById(id);
    }

    @Override
    public List<Activity> selectActivityForDetailByClueId(String id) {
        return activityMapper.selectActivityForDetailByClueId(id);
    }

    @Override
    public List<Activity> selectActivityForDetailByNameAndClueId(Map<String, Object> map) {
        return activityMapper.selectActivityForDetailByNameAndClueId(map);
    }

    @Override
    public List<Activity> selectActivityForDetailByIds(String[] ids) {
        return activityMapper.selectActivityForDetailByIds(ids);
    }

    @Override
    public List<Activity> selectActivityForConvertByNameClueId(Map<String, Object> map) {
        return activityMapper.selectActivityForConvertByNameClueId(map);
    }
}
