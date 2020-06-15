package com.sun.crowd.mvc.config;

import com.sun.crowd.constant.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.util.AntPathMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * SpringSecurity配置类
 * @author sun
 */
@Configuration
// 开启web权限控制
@EnableWebSecurity
// 启用全局方法权限控制功能，并且设置prePostEnable=true，保证@PreAuthority，@PostAuthority，@PreFilter，@PostFilter生效
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 启用自定义的从数据库获取用户名方式认证登录权限
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //放行首页，登录页和静态资源
        http.authorizeRequests()
                .antMatchers("/admin/to/login/page.html","/bootstrap/**","/fonts/**","/img/**","/jquery/**","/layer/**","/script/**","crowd/**","/css/**","/ztree/**","/WEB-INF/**")
                    .permitAll()
                // 针对访问页面设定角色
                .antMatchers("/admin/get/page.html")
                    //要求有经理角色或user:get 权限
                    .access("hasRole('经理') or hasAuthority('user:get')")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler((HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e)-> {
                    httpServletRequest.setAttribute("exception",new Exception(CrowdConstant.MESSAGE_ACCESS_DENIED));
                    httpServletRequest.getRequestDispatcher("/WEB-INF/system-error.jsp").forward(httpServletRequest,httpServletResponse);
                })
                .and()
                // 关闭跨域请求
                .csrf().disable()
                // 开启表单登录也
                .formLogin()
                //登录页面
                .loginPage("/admin/to/login/page.html")
                //登录请求的地址
                .loginProcessingUrl("/security/do/login.html")
                // 指定登录成功后的前往地址
                .defaultSuccessUrl("/admin/to/main/page.html")
                // 用户名的请求参数名
                .usernameParameter("loginAcct")
                // 密码的请求参数名
                .passwordParameter("userPswd")
                .and()
         // 开启退出登录功能
        .logout()
            // 开启退出登录地址
            .logoutUrl("/security/do/logout.html")
            // 指定退出登录后的地址
            .logoutSuccessUrl("/admin/to/login/page.html")
                ;
    }
}
