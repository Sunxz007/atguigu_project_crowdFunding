package com.sun.crowd.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * SpringSecurity配置类
 * @author sun
 */
@Configuration
// 开启web权限控制
@EnableWebSecurity
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {




}
