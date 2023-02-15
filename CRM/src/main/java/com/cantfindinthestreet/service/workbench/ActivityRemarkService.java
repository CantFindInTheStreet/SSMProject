package com.cantfindinthestreet.service.workbench;

import com.cantfindinthestreet.bean.workbench.ActivityRemark;
import com.cantfindinthestreet.dao.workbench.ActivityRemarkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ActivityRemarkService {

    List<ActivityRemark> selectActivityRemarkForDetailByActivityId(String id);

    int insertActivityRemark(ActivityRemark activityRemark);

    int deleteActivityRemarkById(String id);

    int updateActivityRemark(ActivityRemark remark);
}
