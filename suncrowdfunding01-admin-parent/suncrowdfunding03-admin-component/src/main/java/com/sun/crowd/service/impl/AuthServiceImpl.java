package com.sun.crowd.service.impl;

import com.sun.crowd.entity.Auth;
import com.sun.crowd.entity.AuthExample;
import com.sun.crowd.mapper.AuthMapper;
import com.sun.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAll() {
        return authMapper.selectByExample(new AuthExample());
    }

}
