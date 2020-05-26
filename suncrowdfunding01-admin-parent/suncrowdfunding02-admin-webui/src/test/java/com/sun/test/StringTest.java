package com.sun.test;

import com.sun.crowd.util.CrowdUtil;
import org.junit.Test;

public class StringTest {
    @Test
    public void testMD5(){
        String source = "123456";
        String encode = CrowdUtil.md5(source);
        System.out.println(encode);

    }
}
