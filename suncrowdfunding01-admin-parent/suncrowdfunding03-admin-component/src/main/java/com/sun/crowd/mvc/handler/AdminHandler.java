package com.sun.crowd.mvc.handler;

import com.sun.crowd.constant.CrowdConstant;
import com.sun.crowd.entity.Admin;
import com.sun.crowd.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author sun
 */
@Controller
public class AdminHandler {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin/do/login.html")
    public String doLogin(
            @RequestParam("loginAcct") String loginAcct,
            @RequestParam("userPswd") String userPswd,
            HttpSession session
    ) {
        //调用Service方法执行登录
        //这个方法如果能返回admin对象说明成功，如果账号、密码不正确，抛出异常
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);

        // 将登录成功返回的admin对象存入session域中
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
        //防止页面留在表单请求路径，刷新后重复提交表单，重定向到另一个地址，更改浏览器地址
        return "redirect:/admin/to/main/page.html";
    }

    @RequestMapping("/admin/do/loginout.html")
    public String doLogout(HttpSession session) {
        // 强制session失效
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }
}
