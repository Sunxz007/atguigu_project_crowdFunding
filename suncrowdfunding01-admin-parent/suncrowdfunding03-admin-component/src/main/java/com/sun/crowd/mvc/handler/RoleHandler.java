package com.sun.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.sun.crowd.entity.Role;
import com.sun.crowd.service.api.RoleService;
import com.sun.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author sun
 */
@Controller
public class RoleHandler {

    @Autowired
    private RoleService roleService;

    @ResponseBody
    @RequestMapping("/role/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "1") Integer pageSize,
            @RequestParam(value = "keyword",defaultValue = "") String keyword
    ){
        // 调用Service方法获取分页数据
        PageInfo<Role> pageInfo;
        try {
            pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);
            return ResultEntity.successWithData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping("/role/save.json")
    public ResultEntity<String> saveRole(Role role) {
        roleService.saveRole(role);
        return ResultEntity.successWithoutData();
    }

}
