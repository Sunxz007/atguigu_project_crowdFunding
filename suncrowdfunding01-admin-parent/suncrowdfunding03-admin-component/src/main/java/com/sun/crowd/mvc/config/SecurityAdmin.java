package com.sun.crowd.mvc.config;

import com.sun.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * 考虑user对象中仅仅包含账号和密码，为了获取到原始的Admin独享，专门创建这个类对User类进行扩展
 * @author sun
 */
public class SecurityAdmin extends User {

    /**
     * 原始的Admin对象，存储Admin对象的全部属性
     */
    private Admin originalAdmin;

    /**
     *
     * @param originalAdmin 传入的原始Admin对象
     * @param authorities 创建角色，权限信息的集合
     */
    public SecurityAdmin(Admin originalAdmin, List<GrantedAuthority> authorities) {
        // 调用父类构造器
        super(originalAdmin.getLogAcct(),originalAdmin.getUserPswd(),authorities);

        // 给本类的orginalAdmin 赋值
        this.originalAdmin = originalAdmin;
    }

    public Admin getOriginalAdmin() {
        return originalAdmin;
    }
}
