package com.sun.crowd.mvc.config;

import com.google.gson.Gson;
import com.sun.crowd.constant.CrowdConstant;
import com.sun.crowd.exception.LoginFailedException;
import com.sun.crowd.util.CrowdUtil;
import com.sun.crowd.util.ResultEntity;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ControllerAdvice 表示当前类是一个基于注解的异常处理类
 *
 * @author sun
 */
@ControllerAdvice
public class CrowdExceptionResolver {

    @ExceptionHandler(value = LoginAcctAlreadyInUseForUpdateException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseForUpdateException(
            LoginAcctAlreadyInUseForUpdateException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        //返回到错误信息页面，从而返回编辑表单中
        return commonResolve("system-error", exception, response, request);
    }

    @ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseException(
            LoginAcctAlreadyInUseException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        return commonResolve("admin-add", exception, response, request);
    }

    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolveLoginException(
            LoginFailedException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        return commonResolve("admin-login", exception, response, request);
    }

    /**
     * {@link ExceptionHandler 将一个具体的异常和一个方法关联起来}
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView resolverNullPotinterExcepiton(
            NullPointerException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        return commonResolve("system-error", exception, response, request);
    }

    /**
     * 公共的异常返回方法
     * @param viweName 实际错误要去的视图
     * @param exception 实际捕获的异常类型
     * @param response  response对象
     * @param request request对象
     */
    private ModelAndView commonResolve(
            String viweName,
            Exception exception,
            HttpServletResponse response,
            HttpServletRequest request
    ) throws IOException {
        // 判断当前请求
        boolean judgeResult = CrowdUtil.judgeRequestType(request);
        // 如果为ajax请求
        if (judgeResult) {
            //1. 创建ResultEntity对象
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
            // 2. 创建Gson对象
            Gson gson = new Gson();
            //3. 将ResultEntity对象转为json字符串
            String json = gson.toJson(resultEntity);
            //4. 将json字符串作为响应体返回给浏览器
            response.getWriter().write(json);
            return null;
        }
        //不是ajax请求则直接创建modelAndView对象
        ModelAndView modelAndView = new ModelAndView();
        //将exception对象存入模型
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION, exception);
        //设置对应的视图名称
        modelAndView.setViewName(viweName);
        return modelAndView;
    }
}
