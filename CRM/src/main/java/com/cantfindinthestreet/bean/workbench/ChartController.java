package com.cantfindinthestreet.bean.workbench;

import com.cantfindinthestreet.service.workbench.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ChartController {

    @Autowired
    private TranService tranService;

    @RequestMapping("/workbench/chart/transaction/index.do")
    public String index(){
        return "workbench/chart/transaction/index";
    }

    @RequestMapping("/workbench/chart/transaction/selectCountOfTranGroupByStage")
    @ResponseBody
    public Object selectCountOfTranGroupByStage(){
        List<FunnelVO> funnelVOS = tranService.selectCountOfTranGroupByStage();
        return funnelVOS;
    }
}
