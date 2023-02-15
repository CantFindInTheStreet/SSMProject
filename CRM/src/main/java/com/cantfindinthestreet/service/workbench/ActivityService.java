package com.cantfindinthestreet.service.workbench;

import com.cantfindinthestreet.bean.workbench.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    int insertActivity(Activity activity);
    List<Activity> selectActivityByConditionForPage(Map<String,Object> map);
    int countActivityHowMany(Map<String,Object> map);
    int removeActivityByIds(String[] ids);
    Activity selectActivityById(String id);
    int updateActivityById(Activity activity);
    List<Activity> selectAllActivity();
    List<Activity> selectSomeActivityByIds(String[] ids);
    int insertActivityByExcel(List<Activity> activityList);
    Activity selectActivityForDetailById(String id);
    List<Activity> selectActivityForDetailByClueId(String id);
    List<Activity> selectActivityForDetailByNameAndClueId(Map<String,Object> map);
    List<Activity> selectActivityForDetailByIds(String[] ids);
    List<Activity> selectActivityForConvertByNameClueId(Map<String,Object> map);
}
