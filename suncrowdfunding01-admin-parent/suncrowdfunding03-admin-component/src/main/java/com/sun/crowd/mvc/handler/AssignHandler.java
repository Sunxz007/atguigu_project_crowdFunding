package com.sun.crowd.mvc.handler;

import com.sun.crowd.entity.Auth;
import com.sun.crowd.entity.Role;
import com.sun.crowd.service.api.AdminService;
import com.sun.crowd.service.api.AuthService;
import com.sun.crowd.service.api.RoleService;
import com.sun.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class AssignHandler {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @RequestMapping("/assign/to/assgin/role/page.html")
    public String toAssignRolePage(
            @RequestParam("adminId") Integer id,
            ModelMap modelMap
    ) {
        // 1. 查询已分配角色
        List<Role> assignedRole = roleService.getAssignedRole(id);
        //2. 查询未分配角色
        List<Role> unAssignedRole = roleService.getUnAssignedRole(id);
        //3. 存入模型
        modelMap.addAttribute("assignedRole", assignedRole);
        modelMap.addAttribute("unAssignedRole", unAssignedRole);
        return "assign-role";
    }

    @RequestMapping("/assign/do/role/assign.html")
    public String saveAdminRoleRelationship(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList
    ) {
        // 允许页面上的所有已分配角色再提交表单，所以可以不提供roleList
        adminService.saveAdminRoleRelationship(adminId, roleIdList);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword" + keyword;
    }

    @RequestMapping("/assign/get/all/auth.json")
    @ResponseBody
    public ResultEntity<List<Auth>> getAllAuth() {
        List<Auth> authList = authService.getAll();
        return ResultEntity.successWithData(authList);
    }

    @ResponseBody
    @RequestMapping("/assign/get/assigned/auth/id.json")
    public ResultEntity<List<Integer>> getAssignAuthIdByRoleId(@RequestParam("roleId") Integer roleId) {
        List<Integer> authList = authService.getAssignedAuthIdByRoleId(roleId);
        return ResultEntity.successWithData(authList);
    }

    @RequestMapping("/assign/do/role/assign/auth.json")
    @ResponseBody
    public ResultEntity<String> saveRoleAuthRelationship(@RequestBody Map<String, List<Integer>> map) {
        authService.saveRoleRelationship(map);
        return ResultEntity.successWithoutData();
    }
}
