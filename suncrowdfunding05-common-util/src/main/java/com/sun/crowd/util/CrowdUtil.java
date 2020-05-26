package com.sun.crowd.util;

import com.sun.crowd.constant.CrowdConstant;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 项目通用工具类
 * @author sun
 */
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

    /**
     * 对明文字符串进行MD5加密
     * @param source 传入明文字符串
     * @return 加密结果
     */
    public static String md5(String source){

        // 1. 判断source是否有效
        if (source == null || source.length()==0) {
            // 如果不是有效字符串，抛出异常
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }

        try {
            // 2. 创建MessageDigest对象
            String algorithm = "md5";

            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            // 3. 获取文明字符串的对应字节数组
            byte[] input = source.getBytes();

            // 4. 执行加密
            byte[] output = messageDigest.digest(input);

            // 5. 创建BigInteger对象
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, output);

            // 7.按16进制将bigInteger的值转为字符串
            int radix = 16;

            return bigInteger.toString(radix);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
