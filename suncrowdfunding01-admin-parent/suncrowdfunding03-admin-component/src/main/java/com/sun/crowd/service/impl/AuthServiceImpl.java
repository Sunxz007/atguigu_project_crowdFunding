package com.sun.crowd.service.impl;

import com.sun.crowd.entity.Auth;
import com.sun.crowd.entity.AuthExample;
import com.sun.crowd.mapper.AuthMapper;
import com.sun.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAll() {
        return authMapper.selectByExample(new AuthExample());
    }

    @Override
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
        return authMapper.selectAssignedAuthIdByRoleId(roleId);
    }

    @Override
    public void saveRoleRelationship(Map<String,List<Integer>> map) {
        // 1. 获取roleId
        List<Integer> roleIdList = map.get("roleId");
        Integer roleId = roleIdList.get(0);

        //2. 删除旧roleId关联关系
        authMapper.deleteOldRelationship(roleId);

        // 3. 获取authLsit
        List<Integer> authIdList = map.get("authIdArray");
        //4. 判断是否为空
        if (authIdList != null && authIdList.size() > 0) {
            authMapper.insertNewRelationship(roleId,authIdList);
        }

    }
}
