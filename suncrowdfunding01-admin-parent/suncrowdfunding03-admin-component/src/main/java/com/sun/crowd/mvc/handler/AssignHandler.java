package com.sun.crowd.mvc.handler;

import com.sun.crowd.entity.Role;
import com.sun.crowd.service.api.AdminService;
import com.sun.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AssignHandler {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/assign/to/assgin/role/page.html")
    public String toAssignRolePage(
            @RequestParam("adminId") Integer id,
            ModelMap modelMap
    ){
        // 1. 查询已分配角色
        List<Role> assignedRole = roleService.getAssignedRole(id);
        //2. 查询未分配角色
        List<Role> unAssignedRole = roleService.getUnAssignedRole(id);
        //3. 存入模型
        modelMap.addAttribute("assignedRole", assignedRole);
        modelMap.addAttribute("unAssignedRole", unAssignedRole);
        return "assign-role";
    }
}
