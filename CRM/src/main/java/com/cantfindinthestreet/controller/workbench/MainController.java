package com.cantfindinthestreet.controller.workbench;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/workbench/main/index")
    public String main(){
        return "workbench/main/index";
    }
}
