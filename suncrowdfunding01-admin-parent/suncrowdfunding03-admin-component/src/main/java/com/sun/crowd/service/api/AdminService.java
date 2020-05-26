package com.sun.crowd.service.api;

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
}
