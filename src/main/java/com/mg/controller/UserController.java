package com.mg.controller;

import com.alibaba.fastjson.JSON;
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
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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

    /*
     * 注册接口
     * */
    @RequestMapping("/register")
    @ResponseBody
    public MvcObject register(User user) {
        MvcObject mvcObject = null;
        //注册时，校验账号是否已存在
        int result = userService.register(user);
        if (result == 1) {
            mvcObject = new MvcObject("注册成功", "200");
        } else {
            mvcObject = new MvcObject("注册失败", "100");
        }
        return mvcObject;
    }

    /*
     * 登录方法，通过HttpServletRequest获取登录信息，post方法
     * */
    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        MvcObject mvcObject = null;
        Map<String, Object> resultMap = new HashMap<>(16);

        String requestAccountNumber = request.getParameter("accountNumber");
        String requestPassword = request.getParameter("password");

        User user = userService.login(requestAccountNumber);
        if (user != null) {
            if (requestPassword.equals(user.getPassword())) {
                System.out.println(this.getCurrentTime() + "  用户 " + user.getNickname() + " 访问登录接口");
                //仅更新用户的登录时间
                int loginResult = userService.updateLoginTime(requestAccountNumber);
                if (loginResult != 0) {
                    user.setPassword(null);
                    HttpSession session = request.getSession();
                    //把user实体类放进session里
                    session.setAttribute("USER_SESSION", user); //一般直接保存user实体
                    session.setMaxInactiveInterval(30 * 60);
                    mvcObject = new MvcObject("登录成功", "200", resultMap);
                    request.setAttribute("mvcObject", mvcObject);
                    return "redirect:../send.jsp";
                } else {
                    return "redirect:../login.jsp";
                }
            } else {
                return "redirect:../login.jsp";
            }
        } else {
            return "redirect:../login.jsp";
        }
    }

    /*
     * 个人中心页面
     * */
    @RequestMapping("/personal_info")
    public ModelAndView showPersonalInfo(HttpServletRequest request) {
        MvcObject mvcObject = null;
        ModelAndView modelAndView = null;
        Map<String, Object> resultMap = new HashMap<>();

        HttpSession session = request.getSession();
        User requestUser = (User) session.getAttribute("USER_SESSION");
        if (requestUser != null) {
            String requestAccountNumber = requestUser.getAccountNumber();

            if (requestAccountNumber != null && !("".equals(requestAccountNumber))) {
                //查询个人信息，并且封装到user类中
                User user = userService.selectUserToShowPersonalInfo(requestAccountNumber);
                if (user != null) {
                    resultMap.put("user", user);
                    mvcObject = new MvcObject("查询成功", "100", resultMap);
                } else {
                    mvcObject = new MvcObject("查询失败", "200");
                }
            } else {
                mvcObject = new MvcObject("系统异常", "202");
            }
        } else {
            mvcObject = new MvcObject("系统异常", "202");
        }
        modelAndView = new ModelAndView("forward:../personal.jsp", "mvcObject", mvcObject);
        return modelAndView;
    }

    /*
     * 根据session中的账号，修改个人信息
     * */
    @RequestMapping("/modify_personal_info")
    @ResponseBody
    public ModelAndView modifyPersonalInfo(User user, HttpServletRequest request) {
        System.out.println("---------------------------------");
        MvcObject mvcObject = null;
        ModelAndView modelAndView = null;

        HttpSession session = request.getSession();
        User requestUser = (User) session.getAttribute("USER_SESSION");
        if (requestUser != null) {
            String requestAccountNumber = requestUser.getAccountNumber();
            //这里获取一下前端修改的新的昵称，后面重新封装进去session
            String newNickname = user.getNickname();

            //把账号也封装到user类里面，因为修改个人资料时传入的参数是user类
            user.setAccountNumber(requestAccountNumber);
            int result = userService.modifyPersonalInfo(user);
            if (result == 0) {
                mvcObject = new MvcObject("更新个人资料失败", "100");
            } else if (result == 1) {
                //如果传入的昵称不为空，且成功更新个人资料，这里会重置session域中的user对象
                if (newNickname != null && !("".equals(newNickname))) {
                    requestUser.setNickname(newNickname);
                    session.setAttribute("USER_SESSION", user);
                }
                mvcObject = new MvcObject("成功更新" + result + "条资料", "200");
            }
        } else {
            mvcObject = new MvcObject("系统异常", "202");
        }
        modelAndView = new ModelAndView("forward:../modify_result.jsp", "mvcObject", JSON.toJSON(mvcObject));
        return modelAndView;
    }

    //修改密码
    @RequestMapping("/modify_password")
    public ModelAndView modifyPersonalInfo(HttpServletRequest request) {
        MvcObject mvcObject = null;
        ModelAndView modelAndView = null;

        HttpSession session = request.getSession();
        User requestUser = (User) session.getAttribute("USER_SESSION");
        if (requestUser != null) {
            String requestAccountNumber = requestUser.getAccountNumber();
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            if (newPassword != null & !("".equals(newPassword))) {
                //service层做了密码校验
                int result = userService.modifyPassword(requestAccountNumber, oldPassword, newPassword);
                if (result == 1) {
                    mvcObject = new MvcObject("修改密码成功", "100");
                } else if (result == 0) {
                    mvcObject = new MvcObject("修改密码失败", "200");
                } else if (result == 201) {
                    mvcObject = new MvcObject("查不到该用户", "201");
                } else if (result == 202) {
                    mvcObject = new MvcObject("密码不一致", "202");
                }
            } else {
                mvcObject = new MvcObject("新密码不能为空", "202");
            }
        } else {
            mvcObject = new MvcObject("系统异常", "202");
        }
        modelAndView = new ModelAndView("forward:../modify_result.jsp", "mvcObject", JSON.toJSON(mvcObject));
        return modelAndView;
    }

    //上传头像
    @RequestMapping("/upload_head_icon")
    @ResponseBody
    public ModelAndView uploadHeadIcon(@RequestParam("headUrl") CommonsMultipartFile
                                               multipartFile, HttpServletRequest request) {
        MvcObject mvcObject = null;
        ModelAndView modelAndView = null;

        HttpSession session = request.getSession();
        User requestUser = (User) session.getAttribute("USER_SESSION");
        if (requestUser != null) {
            //声明常量，head_icon_img是在该项目工程下的保存头像的文件夹，IO流也会往里面写文件
            final String RELATIVEPATH = "\\head_icon_img";

            //sourcePath大概就是F:\apache-tomcat-8.5.35\webapps\ChattingRoom\head_icon_img这个路径
            String sourcePath = request.getServletContext().getRealPath(RELATIVEPATH);
            FileItem fileItem = multipartFile.getFileItem();

            if (fileItem != null) {
                //获取文件名
                String fileName = fileItem.getName();
                if (fileName != null && !("".equals(fileName))) {
                    if (session != null) {
                        //这里将会使用账号名作为文件名保存，因为账号名在注册时会校验是否有相同的
                        String requestAccountNumber = requestUser.getAccountNumber();
                        if (requestAccountNumber != null && !("".equals(requestAccountNumber))) {
                            //工具类进行路径拼接
                            sourcePath = FileUploadUtil.getHeadIconPath(sourcePath, fileName, requestAccountNumber);
                            try {
                                //工具类进行文件读写，传入的参数是FileItem类的InputStream
                                FileUploadUtil.uploadHeadIcon(fileItem.getInputStream(), sourcePath);
                                //数据库表中存入头像的相对路径
                                //在调用该方法的时候，service层会对sorcePath进行相对应的处理，使其变成相对路径
                                int result = userService.updateUserHeadIcon(requestAccountNumber, sourcePath, RELATIVEPATH);
                                if (result == 0) {
                                    mvcObject = new MvcObject("上传失败", "200");
                                } else if (result == 1) {
                                    mvcObject = new MvcObject("上传成功", "100");
                                    //此处重置session域里的头像地址，放入的是相对路径
                                    requestUser.setHeadUrl(RELATIVEPATH + sourcePath.substring(sourcePath.lastIndexOf("\\")));
                                    session.setAttribute("USER_SESSION", requestUser);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                mvcObject = new MvcObject("系统异常", "202");
                            }
                        } else {
                            mvcObject = new MvcObject("系统异常", "202");
                        }
                    }
                } else {
                    mvcObject = new MvcObject("上传文件名为空", "204");
                }
            } else {
                mvcObject = new MvcObject("传入的文件非法", "204");
            }
        } else {
            mvcObject = new MvcObject("系统异常", "202");
        }
        modelAndView = new ModelAndView("forward:../modify_result.jsp", "mvcObject", JSON.toJSON(mvcObject));
        return modelAndView;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User requestUser = (User) session.getAttribute("USER_SESSION");
        if (requestUser != null) {
            System.out.println(this.getCurrentTime() + "  用户 " + requestUser.getNickname() + " 访问注销接口");
            session.invalidate();
        }
        return "redirect:../login.jsp";
    }

    public static String getCurrentTime() {
        return SystemCurrentTimeUtil.getCurrentTime();
    }
}
