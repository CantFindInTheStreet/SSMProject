package com.cantfindinthestreet.controller.workbench;

import com.cantfindinthestreet.bean.commons.GongJv;
import com.cantfindinthestreet.bean.commons.ReturnObject;
import com.cantfindinthestreet.bean.settings.DicValue;
import com.cantfindinthestreet.bean.settings.User;
import com.cantfindinthestreet.bean.workbench.Activity;
import com.cantfindinthestreet.bean.workbench.Clue;
import com.cantfindinthestreet.bean.workbench.ClueActivityRelation;
import com.cantfindinthestreet.bean.workbench.ClueRemark;
import com.cantfindinthestreet.service.settings.DicValueService;
import com.cantfindinthestreet.service.settings.UserService;
import com.cantfindinthestreet.service.workbench.ActivityService;
import com.cantfindinthestreet.service.workbench.ClueActivityRelationService;
import com.cantfindinthestreet.service.workbench.ClueRemarkService;
import com.cantfindinthestreet.service.workbench.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/workbench/clue")
public class ClueController {
    @Autowired
    private UserService userService;
    @Autowired
    private DicValueService dicValueService;
    @Autowired
    private ClueService clueService;
    @Autowired
    private ClueRemarkService clueRemarkService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ClueActivityRelationService clueActivityRelationService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request){
        List<User> userList = userService.selectAllUser();
        List<DicValue> appellationList = dicValueService.selectDicValueByTypeCode("appellation");
        List<DicValue> clueStateList = dicValueService.selectDicValueByTypeCode("clueState");
        List<DicValue> sourceList = dicValueService.selectDicValueByTypeCode("source");
        request.setAttribute("userList",userList);
        request.setAttribute("appellationList",appellationList);
        request.setAttribute("clueStateList",clueStateList);
        request.setAttribute("sourceList",sourceList);
        return "workbench/clue/index";
    }

    @RequestMapping("/insertClue")
    @ResponseBody
    public Object insertClue(Clue clue, HttpSession session){
        ReturnObject returnObject = new ReturnObject();
        clue.setId(GongJv.getUUID());
        clue.setCreateTime(GongJv.DateGeShi1(new Date()));
        clue.setCreateBy(((User)session.getAttribute(GongJv.USERNAME)).getId());
        System.out.println(clue);
        try{
            int i = clueService.insertClue(clue);
            if(i==1){
                returnObject.setCode(GongJv.CHENGGONG);
                returnObject.setMessage("创建成功！");
            }else{
                returnObject.setCode(GongJv.SHIBAI);
                returnObject.setMessage("系统繁忙！");
            }

        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(GongJv.SHIBAI);
            returnObject.setMessage("系统繁忙！");
        }
        return returnObject;
    }
    @RequestMapping("/detailClue")
    public String detailClue(String id,HttpServletRequest request){
        Clue clue = clueService.selectClueForDetailById(id);
        List<ClueRemark> clueRemarks = clueRemarkService.selectClueRemarkForDetailByClueId(id);
        List<Activity> activityList = activityService.selectActivityForDetailByClueId(id);
        request.setAttribute("clue",clue);
        request.setAttribute("clueRemarks",clueRemarks);
        request.setAttribute("activityList",activityList);
        return "workbench/clue/detail";
    }
    @RequestMapping("/selectActivityForDetailByNameAndClueId")
    @ResponseBody
    public Object selectActivityForDetailByNameAndClueId(String activityName,String clueId){
        Map<String,Object> map=new HashMap<>();
        map.put("activityName",activityName);
        map.put("clueId",clueId);
        List<Activity> activityList = activityService.selectActivityForDetailByNameAndClueId(map);
        return activityList;
    }
    @RequestMapping("/saveBund")
    @ResponseBody
    public Object saveBund(String[] activityId,String clueId){
        ClueActivityRelation clueActivityRelation=null;
        List<ClueActivityRelation> list=new ArrayList<>();
        for(String id:activityId){
            clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setId(GongJv.getUUID());
            clueActivityRelation.setActivityId(id);
            clueActivityRelation.setClueId(clueId);
            list.add(clueActivityRelation);
        }
        ReturnObject returnObject = new ReturnObject();
        try{
            int i = clueActivityRelationService.insertClueActivityRelationByList(list);
            if(i>0){
                returnObject.setCode(GongJv.CHENGGONG);
                List<Activity> activityList = activityService.selectActivityForDetailByIds(activityId);
                returnObject.setRetData(activityList);
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
    @RequestMapping("/saveUnbund")
    @ResponseBody
    public Object saveUnbund(ClueActivityRelation clueActivityRelation){
        ReturnObject returnObject = new ReturnObject();
        try{
            int i = clueActivityRelationService.deleteClueActivityRelationByCludIdActivityId(clueActivityRelation);
            if(i>0){
                returnObject.setCode(GongJv.CHENGGONG);
                returnObject.setMessage("解除关联成功！");
            }else{
                returnObject.setCode(GongJv.SHIBAI);
                returnObject.setMessage("系统繁忙，请稍后再试！");
            }
        }catch (Exception e){
            returnObject.setCode(GongJv.SHIBAI);
            returnObject.setMessage("系统繁忙，请稍后再试！");
        }
        return  returnObject;
    }

    @RequestMapping("/toConvert")
    public String toConvert(String id,HttpServletRequest request){
        Clue clue = clueService.selectClueForDetailById(id);
        List<DicValue> stage = dicValueService.selectDicValueByTypeCode("stage");
        request.setAttribute("clue",clue);
        request.setAttribute("stage",stage);
        return "workbench/clue/convert";
    }

    @RequestMapping("/selectActivityForConvertByNameClueId")
    @ResponseBody
    public Object selectActivityForConvertByNameClueId(String activityName,String clueId){
        Map<String,Object> map=new HashMap<>();
        map.put("activityName",activityName);
        map.put("clueId",clueId);
        List<Activity> activityList = activityService.selectActivityForConvertByNameClueId(map);
        return activityList;
    }

    @RequestMapping("/convertClue")
    @ResponseBody
    public Object convertClue(String clueId,String money,String name,String expectedDate,
                              String stage,String activityId,String isCreateTran,HttpSession session){
        Map<String,Object> map=new HashMap<>();
        map.put("clueId",clueId);
        map.put("money",money);
        map.put("name",name);
        map.put("expectedDate",expectedDate);
        map.put("stage",stage);
        map.put("activityId",activityId);
        map.put("isCreateTran",isCreateTran);
        map.put(GongJv.USERNAME,session.getAttribute(GongJv.USERNAME));
        ReturnObject returnObject = new ReturnObject();
        try{
            clueService.savaConvertClue(map);
            returnObject.setCode(GongJv.CHENGGONG);
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(GongJv.SHIBAI);
            returnObject.setMessage("系统繁忙，请稍后再试!");
        }
        return  returnObject;
    }
}
