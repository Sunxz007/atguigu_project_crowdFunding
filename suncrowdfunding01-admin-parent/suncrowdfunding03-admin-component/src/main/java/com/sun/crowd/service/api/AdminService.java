package com.sun.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.sun.crowd.entity.Admin;

import java.util.List;

public interface AdminService {

    void saveAdmin(Admin admin);

    List<Admin> getAll();

    /**
     * 根据用户名和密码获取登录名
     * @param loginAcct 登录账号
     * @param userPswd 登录密码
     * @return 登录用户信息
     */
    Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    /**
     * 根据id获取admin信息
     * @param id adminId
     * @return admin信息
     */
    Admin getAdminById(Integer id);

    /**
     * 根据关键词进行分页查询
     * @param keyword 关键词
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @return PageInfo 信息
     */
    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 删除admin数据
     * @param adminId admin的id
     */
    void remove(Integer adminId);

    /**
     * 更新admin信息
     * @param admin 需要更新的admin信息
     */
    void updateAdmin(Admin admin);

    /**
     * 保存admin对象所包含的角色
     * @param adminId
     * @param roleIdList
     */
    void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);

    /**
     * 根据logAcct 获取admin
     */
    Admin getAdminByLoginAcct(String logAcct);

}
