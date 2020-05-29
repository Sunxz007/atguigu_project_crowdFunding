package com.sun.crowd.service.impl;

import com.sun.crowd.mapper.MenuMapper;
import com.sun.crowd.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
}
