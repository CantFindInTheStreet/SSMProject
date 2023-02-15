package com.cantfindinthestreet.controller.workbench;

import com.cantfindinthestreet.bean.commons.GongJv;
import com.cantfindinthestreet.bean.commons.ReturnObject;
import com.cantfindinthestreet.bean.settings.User;
import com.cantfindinthestreet.bean.workbench.ActivityRemark;
import com.cantfindinthestreet.service.workbench.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class ActivityRemarkController {
    @Autowired
    private ActivityRemarkService activityRemarkService;


    @RequestMapping("/workbench/activity/createActivityRemark")
    @ResponseBody
    public Object createActivityRemark(ActivityRemark remark, HttpSession session){
        ReturnObject returnObject = new ReturnObject();
        remark.setId(GongJv.getUUID());
        remark.setCreateTime(GongJv.DateGeShi1(new Date()));
        remark.setCreateBy(((User)session.getAttribute(GongJv.USERNAME)).getId());
        remark.setEditFlag(GongJv.FALSECHANGE);
        try{
            int i=activityRemarkService.insertActivityRemark(remark);
            if(i==1){
                returnObject.setCode(GongJv.CHENGGONG);
                returnObject.setMessage("保存成功");
                returnObject.setRetData(remark);
            }else{
                returnObject.setCode(GongJv.SHIBAI);
                returnObject.setMessage("保存失败");
            }

        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(GongJv.SHIBAI);
            returnObject.setMessage("保存失败");
        }

        return returnObject;
    }

    @RequestMapping("/workbench/activity/deleteActivityRemarkById")
    @ResponseBody
    public Object deleteActivityRemarkById(String id){
        System.out.println(id);
        ReturnObject returnObject=new ReturnObject();
        try{
            int i = activityRemarkService.deleteActivityRemarkById(id);
            if(i==1){
                returnObject.setCode(GongJv.CHENGGONG);
                returnObject.setMessage("删除成功！");
            }else{
                returnObject.setCode(GongJv.SHIBAI);
                returnObject.setMessage("删除失败，请稍后再试！");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(GongJv.SHIBAI);
            returnObject.setMessage("删除失败，请稍后再试！");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/updateActivityRemark")
    @ResponseBody
    public Object updateActivityRemark(ActivityRemark remark,HttpSession session){
        ReturnObject returnObject = new ReturnObject();
        remark.setEditBy(((User)session.getAttribute(GongJv.USERNAME)).getId());
        remark.setEditTime(GongJv.DateGeShi1(new Date()));
        remark.setEditFlag(GongJv.TRUECHANGE);
        try{
            int i = activityRemarkService.updateActivityRemark(remark);
            if(i==1){
                returnObject.setCode(GongJv.CHENGGONG);
                returnObject.setMessage("修改成功！");
                returnObject.setRetData(remark);
            }else{
                returnObject.setCode(GongJv.SHIBAI);
                returnObject.setMessage("系统繁忙，请稍后再试！");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(GongJv.SHIBAI);
            returnObject.setMessage("系统繁忙，请稍后再试！");
        }
        return returnObject;
    }

}
