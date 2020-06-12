package com.sun.crowd.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * SpringSecurity配置类
 * @author sun
 */
@Configuration
// 开启web权限控制
@EnableWebSecurity
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 启用自定义的从数据库获取用户名方式认证登录权限
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //放行首页，登录页和静态资源
        http.authorizeRequests()
                .antMatchers("/admin/to/login/page.html","/bootstrap/**","/fonts/**","/img/**","/jquery/**","/layer/**","/script/**","crowd/**","/css/**","/ztree/**","/WEB-INF/**")
                .permitAll()
                .anyRequest()
                .authenticated()
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
