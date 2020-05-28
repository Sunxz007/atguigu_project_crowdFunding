package com.sun.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.crowd.entity.Role;
import com.sun.crowd.entity.RoleExample;
import com.sun.crowd.mapper.RoleMapper;
import com.sun.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sun
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void saveRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void removeRole(List<Integer> roleList) {
        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(roleList);
        roleMapper.deleteByExample(example);
    }

    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {

        // 1. 开启分页功能
        PageHelper.startPage(pageNum, pageSize);

        // 2. 执行查询
        List<Role> roleList = roleMapper.selectRoleByKeyword(keyword);

        // 3. 封装为PageInfo对象返回
        return new PageInfo<>(roleList);
    }
}
