package com.sun.crowd.service.api;


import com.sun.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

public interface AuthService {
    List<Auth> getAll();

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    void saveRoleRelationship(Map<String, List<Integer>> map);

    /**
     * 根据Admin的id获取拥有的权限名
     */
    List<String> getAssignedAuthNameByAdminId(Integer adminId);
}
