package com.mg.test;

import com.alibaba.fastjson.JSON;
import com.mg.BaseTest;
import com.mg.core.MvcObject;
import com.mg.model.User;
import com.mg.service.UserService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

public class UserControllerTest extends BaseTest {
    @Resource
    UserService userService;

    User user = new User("bns4597", "MainGuy", "MAINGUY4597");

    @Test
    public void test() {
        MvcObject mvcObject = null;
        Map<String, Object> resultMap = new HashMap<>(16);
        int result = userService.register(user);
        resultMap.put("result", result);
        if (result == 1) {
            mvcObject = new MvcObject("注册成功", "100", resultMap);
        } else {
            mvcObject = new MvcObject("注册失败", "200", resultMap);
        }
        String jsonStr = JSON.toJSONString(mvcObject);
        System.out.println(jsonStr);
    }
}
