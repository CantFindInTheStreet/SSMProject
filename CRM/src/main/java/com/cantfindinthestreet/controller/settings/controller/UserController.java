package com.cantfindinthestreet.controller.settings.controller;

import com.cantfindinthestreet.bean.commons.GongJv;
import com.cantfindinthestreet.bean.commons.ReturnObject;
import com.cantfindinthestreet.bean.settings.User;
import com.cantfindinthestreet.service.settings.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/settings/qx/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/tologin")
    public String toLogin(){
        return "settings/qx/user/login";
    }

    @RequestMapping("/exitlogin")
    public String exitLogin(HttpServletRequest request,HttpServletResponse response){
        Cookie cookie1=new Cookie("loginAct","loginAct");
        Cookie cookie2=new Cookie("loginPwd","loginPwd");
        cookie1.setMaxAge(0);
        cookie2.setMaxAge(0);
        response.addCookie(cookie1);
        response.addCookie(cookie2);

        request.getSession().invalidate();//销毁session

        return "redirect:/";
    }

    @RequestMapping("/login")
    @ResponseBody
    public Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map=new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        User user=userService.selectUserBynp(map);
        ReturnObject returnObject=new ReturnObject();
        returnObject.setCode(GongJv.SHIBAI);//先制登录失败  成功时制成功
        if(user==null){
            //用户密码错误
            returnObject.setMessage("账号or密码错误");
        }else{
            String date= GongJv.DateGeShi1(new Date());
            if(date.compareTo(user.getExpireTime())>0){
                //过期
                returnObject.setMessage("账号已过期");
            }else if("0".equals(user.getLockState())){
                //被锁
                returnObject.setMessage("账号被锁定");
            }else if(!user.getAllowIps().contains(request.getRemoteAddr())){
                //陌生Ip
                returnObject.setMessage("不支持异地登录");
            }else{
                //登录成功
                returnObject.setCode(GongJv.CHENGGONG);
                request.getSession().setAttribute(GongJv.USERNAME,user);
                if("true".equals(isRemPwd)){
                    Cookie cookie1=new Cookie("loginAct",loginAct);
                    Cookie cookie2=new Cookie("loginPwd",loginPwd);
                    cookie1.setMaxAge(60);
                    cookie2.setMaxAge(60);
                    response.addCookie(cookie1);
                    response.addCookie(cookie2);
                }else{
                    Cookie cookie1=new Cookie("loginAct","loginAct");
                    Cookie cookie2=new Cookie("loginPwd","loginPwd");
                    cookie1.setMaxAge(0);
                    cookie2.setMaxAge(0);
                    response.addCookie(cookie1);
                    response.addCookie(cookie2);
                }
            }
        }
        return returnObject;
    }

}
