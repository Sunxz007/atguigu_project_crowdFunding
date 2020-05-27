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
}
