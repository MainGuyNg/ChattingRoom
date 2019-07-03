package com.mg.controller;

import com.mg.core.MvcObject;
import com.mg.core.WebSocketHandler;
import com.mg.model.User;
import com.mg.service.UserService;
import com.mg.utils.FileUploadUtil;
import com.mg.utils.SystemCurrentTimeUtil;
import org.apache.commons.fileupload.FileItem;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @Bean//这个注解会从Spring容器拿出Bean
    public WebSocketHandler infoHandler() {
        return new WebSocketHandler();
    }

    @RequestMapping("/register")
    @ResponseBody
    public MvcObject register(User user) {
        MvcObject mvcObject = null;
        System.out.println(user.toString());
        System.out.println("----注册----");
        Map<String, Object> resultMap = new HashMap<>(16);
        int result = userService.register(user);
        resultMap.put("result", result);
        if (result == 1) {
            mvcObject = new MvcObject("注册成功", "200", resultMap);
        } else {
            mvcObject = new MvcObject("注册失败", "100", resultMap);
        }
        return mvcObject;
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MvcObject mvcObject = null;
        String requestAccountNumber = request.getParameter("accountNumber");
        String requestPassword = request.getParameter("password");
        User user = userService.login(requestAccountNumber);
        if (user != null) {
            if (requestPassword.equals(user.getPassword())) {
                System.out.println(this.getCurrentTime() + "  用户 " + requestAccountNumber + " 访问登录接口");
                int loginResult = userService.updateLoginTime(requestAccountNumber);
                if (loginResult != 0) {
                    HttpSession session = request.getSession();
                    session.setAttribute("ACCOUNTNUMBER", requestAccountNumber); //一般直接保存user实体
                    session.setAttribute("NICKNAME", user.getNickname());
                    session.setAttribute("HEADURL",user.getHeadUrl());
                    session.setMaxInactiveInterval(30 * 60);
                    mvcObject = new MvcObject("登录成功","200");
                    return "send";
                } else {
                    mvcObject = new MvcObject("系统异常","103");
                    return "redirect:../login.jsp";
                }
            } else {
                mvcObject = new MvcObject("密码错误","100");
                return "redirect:../login.jsp";
            }
        } else {
            mvcObject = new MvcObject("没有该用户！！","102");
            return "redirect:../login.jsp";
        }
    }

    @RequestMapping("/personal_info")
    public String showPersonalInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String requestAccountNumber = (String) session.getAttribute("ACCOUNTNUMBER");
        User user = userService.selectUserToShowPersonalInfo(requestAccountNumber);
        if (user != null) {
            Map<String, Object> map = new HashMap<>(16);
            map.put("user", user);
            request.setAttribute("userMap", map);
            return "personal";
        } else {
            System.out.println("showPersonalInfo()方法，查询数据为空！！");
            return "redirect:../send.jsp";
        }
    }

    @RequestMapping("/modify")
    @ResponseBody
    public MvcObject modifyPersonalInfo(User user, HttpServletRequest request) {
        MvcObject mvcObject = null;
        HttpSession session = request.getSession();
        String requestAccountNumber = (String) session.getAttribute("ACCOUNTNUMBER");
        System.out.println(requestAccountNumber + "  开始修改信息");
        user.setAccountNumber(requestAccountNumber);
        System.out.println("新用户信息为：  " + user.toString());
        int result = userService.modifyPersonalInfo(user);
        if (result == 0) {
            mvcObject = new MvcObject("更新个人资料失败", "100");
        } else if (result == 1) {
            session.setAttribute("NICKNAME", user.getNickname());
            mvcObject = new MvcObject("成功更新" + result + "条资料", "200");
        }
        return mvcObject;
    }

    @RequestMapping("/modify_password")
    public String modifyPersonalInfo(HttpServletRequest request) {
        MvcObject mvcObject = null;
        HttpSession session = request.getSession();
        String requestAccountNumber = (String) session.getAttribute("ACCOUNTNUMBER");

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        System.out.println(requestAccountNumber + "  开始修改密码，传入的旧密码为：" + oldPassword + "，传入的新密码为：" + newPassword);
        int result = userService.modifyPassword(requestAccountNumber, oldPassword, newPassword);
        if (result == 1) {
            System.out.println("修改成功");
            mvcObject = new MvcObject("修改密码成功", "100");
        } else if (result == 0) {
            System.out.println("修改失败");
            mvcObject = new MvcObject("修改密码失败", "200");
        } else if (result == 101) {
            System.out.println("查不到该用户");
            mvcObject = new MvcObject("查不到该用户", "101");
        } else if (result == 102) {
            System.out.println("密码不一致：" + oldPassword);
            mvcObject = new MvcObject("密码不一致", "102");
        }
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("resultObject", mvcObject);
        request.setAttribute("resultMap", resultMap);
        return "modify_result";
    }

    @RequestMapping("/uploadHeadIcon")
    @ResponseBody
    public MvcObject uploadHeadIcon(@RequestParam("headUrl") CommonsMultipartFile multipartFile, HttpServletRequest request) {
        MvcObject mvcObject = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;

        String sourcePath = request.getServletContext().getRealPath("/head_icon_img");
        FileItem fileItem = multipartFile.getFileItem();
        String fileName = fileItem.getName();
        HttpSession session = request.getSession();
        String requestAccountNumber = (String) session.getAttribute("ACCOUNTNUMBER");
        sourcePath = FileUploadUtil.getHeadIconPath(sourcePath, fileName, requestAccountNumber);
        try {
            inputStream = fileItem.getInputStream();
            outputStream = new FileOutputStream(new File(sourcePath), false);

            byte[] buf = new byte[5 * 1024];
            int len;
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf,0,len);
                outputStream.flush();
            }

            int result = userService.updateUserHeadIcon(requestAccountNumber,sourcePath);
            if(result==0){
                mvcObject = new MvcObject("上传失败","200");
            }else if(result==1) {
                mvcObject = new MvcObject("上传成功", "100");
            }
        } catch (IOException e) {
            mvcObject = new MvcObject("系统异常","202");
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                mvcObject = new MvcObject("系统异常","202");
            }

        }
        return mvcObject;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("ACCOUNTNUMBER");
        System.out.println(this.getCurrentTime() + "  用户 " + username + " 访问注销接口");
        session.invalidate();
        return "redirect:../login.jsp";
    }

    public static String getCurrentTime() {
        return SystemCurrentTimeUtil.getCurrentTime();
    }


}
