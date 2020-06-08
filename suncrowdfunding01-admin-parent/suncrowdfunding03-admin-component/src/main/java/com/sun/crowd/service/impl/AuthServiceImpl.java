package com.sun.crowd.service.impl;

import com.sun.crowd.mapper.AuthMapper;
import com.sun.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;
}
