package com.sun.crowd.util;

import javax.servlet.http.HttpServletRequest;

public class CrowdUtil {

    /**
     * 判断当前请求是否为ajax请求
     * @param request 请求对象
     * @return
     *      true：当前为ajax请求
     *      false：当前不是ajax请求
     */
    public static boolean judgeRequestType(HttpServletRequest request){
        //1. 获取请求消息头
        String accept = request.getHeader("Accept");
        String xRequestHeader = request.getHeader("X-Request-With");

        return (accept != null && accept.contains("application/json"))
                ||
                (xRequestHeader !=null && xRequestHeader.contains("XMLHttpRequest"));
    }
}
