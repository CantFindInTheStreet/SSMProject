package com.cantfindinthestreet.controller.workbench;

import com.cantfindinthestreet.bean.commons.GongJv;
import com.cantfindinthestreet.bean.commons.ReturnObject;
import com.cantfindinthestreet.bean.settings.User;
import com.cantfindinthestreet.bean.workbench.Activity;
import com.cantfindinthestreet.bean.workbench.ActivityRemark;
import com.cantfindinthestreet.service.settings.UserService;
import com.cantfindinthestreet.service.workbench.ActivityRemarkService;
import com.cantfindinthestreet.service.workbench.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("/workbench/activity")
public class ActivityController {
    @Autowired
    UserService userService;
    @Autowired
    ActivityService activityService;

    @Autowired
    ActivityRemarkService activityRemarkService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request){
        List<User> users = userService.selectAllUser();
        request.setAttribute("users",users);
        return "workbench/activity/index";
    }

    @RequestMapping("/new")
    @ResponseBody
    public Object newActivity(Activity activity,HttpServletRequest request){
        activity.setId(GongJv.getUUID());
        activity.setCreateTime(GongJv.DateGeShi1(new Date()));
        User user =(User)request.getSession().getAttribute(GongJv.USERNAME);
        activity.setCreateBy(user.getId());
        ReturnObject returnObject=new ReturnObject();
        try {
            int i = activityService.insertActivity(activity);
            if(i>0){
                returnObject.setCode(GongJv.CHENGGONG);
            }else{
                returnObject.setCode(GongJv.SHIBAI);
                returnObject.setMessage("系统忙，请稍后重试。。。");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(GongJv.SHIBAI);
            returnObject.setMessage("系统忙，请稍后重试。。。");
        }
        return returnObject;
    }

    @RequestMapping("/selectActivityByCondition")
    @ResponseBody
    public Object selectActivityByCondition(String name,String owner,String startDate,String endDate,
                                            Integer pageNo,Integer pageSize){
        Map<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        List<Activity> activityList = activityService.selectActivityByConditionForPage(map);
        int counts=activityService.countActivityHowMany(map);
        Map<String,Object> maps=new HashMap<>();
        maps.put("activityList",activityList);
        maps.put("counts",counts);
        return  maps;
    }

    @RequestMapping("/removeActivityByIds")
    @ResponseBody
    public Object removeActivityByIds(String[] id){
        ReturnObject returnObject=new ReturnObject();
        try {
            int i = activityService.removeActivityByIds(id);
            if(i>0){
                returnObject.setCode(GongJv.CHENGGONG);
            }else{
                returnObject.setCode(GongJv.SHIBAI);
                returnObject.setMessage("我绝对不会告诉你系统废了");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(GongJv.SHIBAI);
            returnObject.setMessage("我绝对不会告诉你系统废了");
        }
        return returnObject;
    }

    @RequestMapping("/selectActivityById")
    @ResponseBody
    public Object selectActivityById(String id){
        Activity activity = activityService.selectActivityById(id);
        return activity;
    }
    @RequestMapping("/updateActivityById")
    @ResponseBody
    public Object updateActivityById(Activity activity,HttpServletRequest request){
        activity.setEditTime(GongJv.DateGeShi1(new Date()));
        User user =(User) request.getSession().getAttribute(GongJv.USERNAME);
        activity.setEditBy(user.getId());
        ReturnObject returnObject=new ReturnObject();
        try {
            int i = activityService.updateActivityById(activity);
            if(i>0){
                returnObject.setCode(GongJv.CHENGGONG);
                returnObject.setMessage("修改成功！");
            }else{
                returnObject.setCode(GongJv.SHIBAI);
                returnObject.setMessage("系统废了！");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(GongJv.SHIBAI);
            returnObject.setMessage("系统废了！");
        }
        return returnObject;
    }
    @RequestMapping("/exportAllActivities")
    public void exportAllActivities(String news,String[] id,HttpServletResponse response) throws IOException {
        List<Activity> activityList=null;
        response.setContentType("application/octet-stream;charset=UTF-8");
        if("all".equals(news)){
            activityList = activityService.selectAllActivity();//查全部
            response.addHeader("Content-Disposition","attachment;filename=ActivityList.xls");
        }
        if("some".equals(news)){
            activityList = activityService.selectSomeActivityByIds(id);
            response.addHeader("Content-Disposition","attachment;filename=SomeActivityList.xls");
        }
        HSSFWorkbook workbook=new HSSFWorkbook();//创建文件
        HSSFSheet hssfSheet = workbook.createSheet("市场活动列表");//创建页
        HSSFRow row = hssfSheet.createRow(0);//创建行
        HSSFCell cell = row.createCell(0);//创建行中的列
        cell.setCellValue("ID");
        cell = row.createCell(1);
        cell.setCellValue("所有者");
        cell = row.createCell(2);
        cell.setCellValue("名称");
        cell = row.createCell(3);
        cell.setCellValue("开始日期");
        cell = row.createCell(4);
        cell.setCellValue("结束日期");
        cell = row.createCell(5);
        cell.setCellValue("成本");
        cell = row.createCell(6);
        cell.setCellValue("描述");
        cell = row.createCell(7);
        cell.setCellValue("创建时间");
        cell = row.createCell(8);
        cell.setCellValue("创建者");
        cell = row.createCell(9);
        cell.setCellValue("修改时间");
        cell = row.createCell(10);
        cell.setCellValue("修改者");
        if(activityList!=null && activityList.size()>0){
            Activity activity =null;
            for(int i=0;i<activityList.size();i++){
                activity=activityList.get(i);
                HSSFRow rows = hssfSheet.createRow(i+1);//创建行
                HSSFCell cells = rows.createCell(0);//创建行中的列
                cells.setCellValue(activity.getId());
                cells = rows.createCell(1);
                cells.setCellValue(activity.getOwner());
                cells = rows.createCell(2);
                cells.setCellValue(activity.getName());
                cells = rows.createCell(3);
                cells.setCellValue(activity.getStartDate());
                cells = rows.createCell(4);
                cells.setCellValue(activity.getEndDate());
                cells = rows.createCell(5);
                cells.setCellValue(activity.getCost());
                cells = rows.createCell(6);
                cells.setCellValue(activity.getDescription());
                cells = rows.createCell(7);
                cells.setCellValue(activity.getCreateTime());
                cells = rows.createCell(8);
                cells.setCellValue(activity.getCreateBy());
                cells = rows.createCell(9);
                cells.setCellValue(activity.getEditTime());
                cells = rows.createCell(10);
                cells.setCellValue(activity.getEditBy());
            }}
        /*OutputStream fileOutputStream = new FileOutputStream("D:\\Dev\\Code\\SSMProject\\Excel\\ActivityFile.xls");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();*/
        /*//下载
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition","attachment;filename=ActivityList.xls");*/
        OutputStream outputStream = response.getOutputStream();
       /* InputStream inputStream = new FileInputStream("D:\\Dev\\Code\\SSMProject\\Excel\\ActivityFile.xls");
        byte[] bytes=new byte[256];
        int read=-1;
        while((read=inputStream.read(bytes))!=-1){
            outputStream.write(bytes,0,read);
        }*/
        workbook.write(outputStream);

        workbook.close();
        outputStream.flush();
//        inputStream.close();
    }

    @RequestMapping("/importActivityByExcel")
    @ResponseBody
    public Object importActivityByExcel(MultipartFile multipartFile, HttpSession session){
        ReturnObject returnObject=new ReturnObject();
        try {
            //把excel文件写到磁盘目录中
            /*String originalFilename = multipartFile.getOriginalFilename();//文件名加后缀
            File file=new File("F:\\",originalFilename);//暂放到目录中
            multipartFile.transferTo(file);//把获取的文件放入指定目录*/

            //把放好的文件上传
//            InputStream in=new FileInputStream("F:\\"+originalFilename);
            InputStream in=multipartFile.getInputStream();
            HSSFWorkbook workbook = new HSSFWorkbook(in);//把上传的文件传入hssf
            HSSFSheet sheetAt = workbook.getSheetAt(0);
            HSSFRow row=null;
            HSSFCell cell=null;
            Activity activity=null;
            List<Activity> activityList=new ArrayList<>();
            User user=(User)session.getAttribute(GongJv.USERNAME);
            int xunhuan=sheetAt.getLastRowNum();
            for(int i=1;i<=xunhuan;i++){//最后一行的下标
                row=sheetAt.getRow(i);
                activity=new Activity();
                activity.setId(GongJv.getUUID());
                activity.setOwner(user.getId());
                activity.setCreateTime(GongJv.DateGeShi1(new Date()));
                activity.setCreateBy(user.getId());
                for (int j=0;j<row.getLastCellNum();j++){//最后一行的下标+1
                    cell=row.getCell(j);
                    String news=GongJv.cellToString(cell);
                    if(j==0){
                        activity.setName(news);
                    }else if(j==1){
                        activity.setStartDate(news);
                    }else if(j==2){
                        activity.setEndDate(news);
                    }else if(j==3){
                        activity.setCost(news);
                    }else if(j==4){
                        activity.setDescription(news);
                    }
                }
                activityList.add(activity);
            }

            int i = activityService.insertActivityByExcel(activityList);

            returnObject.setCode(GongJv.CHENGGONG);
            returnObject.setMessage("您已成功导入"+i+"条数据！");
            returnObject.setRetData(i);

            in.close();

        } catch (IOException e) {
            returnObject.setCode(GongJv.SHIBAI);
            returnObject.setMessage("导入失败！");
            e.printStackTrace();
        }
        return returnObject;
    }


    @RequestMapping("/detailActivity")
    public String detailActivity(String id,HttpServletRequest request){
        Activity activity = activityService.selectActivityForDetailById(id);
        List<ActivityRemark> activityRemarks = activityRemarkService.selectActivityRemarkForDetailByActivityId(id);
        request.setAttribute("activity",activity);
        request.setAttribute("activityRemarks",activityRemarks);

        System.out.println(activity);
        for(ActivityRemark remark:activityRemarks){
            System.out.println(remark);
        }

        return "workbench/activity/detail";
    }
}
