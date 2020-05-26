package com.sun.crowd.mvc.interceptor;

import com.sun.crowd.constant.CrowdConstant;
import com.sun.crowd.entity.Admin;
import com.sun.crowd.mvc.config.AccessForbiddenException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author sun
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 1. 通过request对象获取session对象
        HttpSession session = request.getSession();
        // 2. 尝试从session域中获取admin对象
        Admin admin = (Admin) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);
        // 3. 判断admin是否为空，如果为空转到登录界面
        if (admin == null) {
            // 如果为空则抛出异常
            throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
        }

        // 5. 如果admin对象不为null，则返回true放行
        return true;
    }
}
