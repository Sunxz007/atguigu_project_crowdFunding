package com.sun.crowd.mvc.handler;

import com.sun.crowd.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MenuHandler {

    @Autowired
    private MenuService menuService;
}
