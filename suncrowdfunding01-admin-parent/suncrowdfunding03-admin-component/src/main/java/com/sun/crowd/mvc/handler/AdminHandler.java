package com.sun.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.sun.crowd.constant.CrowdConstant;
import com.sun.crowd.entity.Admin;
import com.sun.crowd.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;

/**
 * @author sun
 */
@Controller
public class AdminHandler {

    @Autowired
    private AdminService adminService;

    @RequestMapping("admin/update")
    public String update(
            Admin admin,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword
    ){
        adminService.updateAdmin(admin);

        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }


    @RequestMapping("admin/to/edit/page.html")
    public String toEditPage(
            @RequestParam("adminId") Integer adminId,
            ModelMap modelMap
    ) {

        // 1. 根据id查询admin
        Admin admin=adminService.getAdminById(adminId);
        // 2. 将admin对象存入模型
        modelMap.addAttribute("admin", admin);

        return "admin-edit";

    }

    @RequestMapping("/admin/save.html")
    public String save(Admin admin) {

        adminService.saveAdmin(admin);

        return "redirect:/admin/get/page.html?pageNum"+Integer.MAX_VALUE;
    }

    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String remove(
            @PathVariable("adminId") Integer adminId,
            @PathVariable("pageNum") String pageNum,
            @PathVariable("keyword") String keyword
    ){

        //执行删除
        adminService.remove(adminId);
        //页面跳转：回到分页页面
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(
            // 使用@RequestParam 注解的defaultValue 属性 ，指定默认值，在请求中没有携带对应参数的使用默认值
            // keyword默认值使用空字符串，和sql语句配合实现两种情况适配
            @RequestParam(value = "keyword",defaultValue = "") String keyword,
            // pageNum默认值使用1，默认去第一页
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            // pageSize默认值使用5
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
            ModelMap modelMap
    ) {
        //调用Service 方法获取pageinfo对象
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);

        //将PageInfo对象存入模型
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        return "admin-page";
    }

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
