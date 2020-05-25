package com.sun.crowd.mvc.handler;

import com.sun.crowd.entity.Admin;
import com.sun.crowd.service.api.AdminService;
import com.sun.crowd.util.CrowdUtil;
import com.sun.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TestHandler {

    @Autowired
    private AdminService adminService;

    private Logger logger = LoggerFactory.getLogger(TestHandler.class);

    @RequestMapping("/test/ssm.html")
    public String testSsm(Model model, HttpServletRequest request) {
        List<Admin> admins = adminService.getAll();
        model.addAttribute("admins", admins);
        String a = null;
        System.out.println(5/0);
        return "target";
    }

    @RequestMapping("/send/ajaxTest.json")
    @ResponseBody
    public ResultEntity<Admin> testReceive(@RequestBody Admin admin, HttpServletRequest request) {
        logger.info(admin.toString());
        logger.info("judgeResult:" + CrowdUtil.judgeRequestType(request));
        String a = null;
        System.out.println(a.length());
        return ResultEntity.successWithData(admin);
    }

}
