package com.mg.controller;

import com.mg.core.MvcObject;
import com.mg.model.User;
import com.mg.service.UserService;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Controller("/user")
public class UserController {

    @Resource
    UserService userService;

    @RequestMapping("/register")
    @ResponseBody
    public MvcObject register(@RequestParam("user") User user) {
        MvcObject mvcObject = null;
        Map<String, Object> resultMap = new HashMap<>(16);
        int result = userService.register(user);
        resultMap.put("result", result);
        if (result == 1) {
            mvcObject = new MvcObject("注册成功", "100", resultMap);
        } else {
            mvcObject = new MvcObject("注册失败", "200", resultMap);
        }
        return mvcObject;
    }
}
