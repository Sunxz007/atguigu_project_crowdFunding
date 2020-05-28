package com.sun.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.sun.crowd.entity.Role;

import java.util.List;

/**
 * @author sun
 */
public interface RoleService {

    PageInfo<Role> getPageInfo(Integer pageNum ,Integer pageSize,String keyword);

    void saveRole(Role role);

    void updateRole(Role role);

    void removeRole(List<Integer> roleList);
}
