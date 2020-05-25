package com.sun.crowd.mvc.handler;

import com.sun.crowd.entity.Admin;
import com.sun.crowd.service.api.AdminService;
import com.sun.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestHandler {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/test/ssm.html")
    public String testSsm(Model model) {
        List<Admin> admins = adminService.getAll();
        model.addAttribute("admins", admins);
        return "target";
    }

    @RequestMapping("/send/ajaxTest.json")
    @ResponseBody
    public ResultEntity<Admin> testReceive(@RequestBody Admin admin) {
        System.out.println("admin"+admin);
        return ResultEntity.successWithData(admin);
    }

}
