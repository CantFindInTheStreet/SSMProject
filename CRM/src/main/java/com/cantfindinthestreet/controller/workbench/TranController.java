package com.cantfindinthestreet.controller.workbench;

import com.cantfindinthestreet.bean.commons.GongJv;
import com.cantfindinthestreet.bean.commons.ReturnObject;
import com.cantfindinthestreet.bean.settings.DicValue;
import com.cantfindinthestreet.bean.settings.User;
import com.cantfindinthestreet.bean.workbench.Tran;
import com.cantfindinthestreet.bean.workbench.TranHistory;
import com.cantfindinthestreet.bean.workbench.TranRemark;
import com.cantfindinthestreet.service.settings.DicValueService;
import com.cantfindinthestreet.service.settings.UserService;
import com.cantfindinthestreet.service.workbench.CustomerService;
import com.cantfindinthestreet.service.workbench.TranHistoryService;
import com.cantfindinthestreet.service.workbench.TranRemarkService;
import com.cantfindinthestreet.service.workbench.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Controller
@RequestMapping("/workbench/transaction")
public class TranController {
    @Autowired
    private DicValueService dicValueService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TranService tranService;
    @Autowired
    private TranRemarkService tranRemarkService;
    @Autowired
    private TranHistoryService tranHistoryService;


    @RequestMapping("/index")
    public String index(HttpServletRequest request){
        List<DicValue> transactionType = dicValueService.selectDicValueByTypeCode("transactionType");
        List<DicValue> source = dicValueService.selectDicValueByTypeCode("source");
        List<DicValue> stage = dicValueService.selectDicValueByTypeCode("stage");
        request.setAttribute("stage",stage);
        request.setAttribute("transactionType",transactionType);
        request.setAttribute("source",source);
        return "workbench/transaction/index";
    }

    @RequestMapping("/save")
    public String save(HttpServletRequest request){
        List<User> users = userService.selectAllUser();
        List<DicValue> transactionType = dicValueService.selectDicValueByTypeCode("transactionType");
        List<DicValue> source = dicValueService.selectDicValueByTypeCode("source");
        List<DicValue> stage = dicValueService.selectDicValueByTypeCode("stage");
        request.setAttribute("stage",stage);
        request.setAttribute("transactionType",transactionType);
        request.setAttribute("source",source);
        request.setAttribute("users",users);
        return "workbench/transaction/save";
    }

    @RequestMapping("/getPossibilityByStage")
    @ResponseBody
    public Object getPossibilityByStage(String stageValue){
        ResourceBundle rb = ResourceBundle.getBundle("possibility");
        return rb.getString(stageValue);
    }

    @RequestMapping("/selectAllCustomerNameByName")
    @ResponseBody
    public Object selectAllCustomerNameByName(String customerName){
        List<String> strings = customerService.selectAllCustomerNameByName(customerName);
        return strings;
    }
    @RequestMapping("/saveCreateTran")
    @ResponseBody
    public Object saveCreateTran(@RequestParam Map<String,Object> map, HttpSession session){
        ReturnObject returnObject = new ReturnObject();
        map.put(GongJv.USERNAME,session.getAttribute(GongJv.USERNAME));
        try{
            tranService.saveCreateTran(map);
            returnObject.setCode(GongJv.CHENGGONG);
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(GongJv.SHIBAI);
            returnObject.setMessage("系统繁忙，请稍后再试！");
        }
        return returnObject;
    }
    @RequestMapping("/detailTran")
    public String detailTran(String tranId,HttpServletRequest request){
        Tran tran = tranService.selectTranForDetailById(tranId);
        List<TranRemark>  tranRemarkList= tranRemarkService.selectTranRemarkForDetailByTranId(tranId);
        List<TranHistory> tranHistoryList = tranHistoryService.selectTranHistoryForDetailByTranId(tranId);
        ResourceBundle rb = ResourceBundle.getBundle("possibility");
        String possibility = rb.getString(tran.getStage());
        tran.setPossibility(possibility);
        List<DicValue> stage = dicValueService.selectDicValueByTypeCode("stage");
        request.setAttribute("stage",stage);
        request.setAttribute("tran",tran);
        request.setAttribute("tranRemarkList",tranRemarkList);
        request.setAttribute("tranHistoryList",tranHistoryList);
        return "workbench/transaction/detail";
    }


}
