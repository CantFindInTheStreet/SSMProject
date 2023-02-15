package com.cantfindinthestreet.controller.workbench;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WorkBenchIndexController {
    @RequestMapping("/workbench/index")
    public String index(){
        return "workbench/index";
    }
}
