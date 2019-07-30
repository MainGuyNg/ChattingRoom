package com.mg.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mg.core.MvcObject;
import com.mg.model.Friend;
import com.mg.model.FriendList;
import com.mg.model.User;
import com.mg.service.FriendListService;
import com.mg.service.RelationService;
import com.mg.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/relation")
public class RelationController {

    @Resource
    RelationService relationService;
    @Resource
    FriendListService friendListService;
    @Resource
    UserService userService;

    //根据昵称模糊查找对象
    @RequestMapping("/query_user_by_nickname")
    @ResponseBody
    public ModelAndView queryUserByNickname(HttpServletRequest request) {
        ModelAndView modelAndView = null;
        MvcObject mvcObject = null;
        Map<String, Object> resultMap = new HashMap<>();

        String requestNickname = request.getParameter("nickname");
        List<User> resultList = relationService.queryUserByNickname(requestNickname);

        int listSize = resultList.size();
        if (listSize == 0) {
            mvcObject = new MvcObject("查不到此用户", "100");
        } else {
            resultMap.put("userList", resultList);
            mvcObject = new MvcObject("查找成功，共" + listSize + "条数据", "100", resultMap);
        }
        modelAndView = new ModelAndView("forward:../query_user_result.jsp", "mvcObject", JSON.toJSON(mvcObject));
        return modelAndView;
    }

    //通过注册账号精确查找对象
    @RequestMapping("/query_user_by_accountNumber")
    @ResponseBody
    public ModelAndView queryUserByAccountNumber(HttpServletRequest request) {
        ModelAndView modelAndView = null;
        MvcObject mvcObject = null;
        Map<String, Object> resultMap = new HashMap<>();

        String requestAccountNumber = request.getParameter("accountNumber");
        User queryUser = relationService.queryUserByAccountNumber(requestAccountNumber);
        if (queryUser == null) {
            mvcObject = new MvcObject("查不到此用户：" + requestAccountNumber, "100");
        } else {
            List<User> list = new ArrayList<>();
            list.add(queryUser);
            resultMap.put("userList", list);
            mvcObject = new MvcObject("查找成功", "100", resultMap);
        }
        modelAndView = new ModelAndView("forward:../query_user_result.jsp", "mvcObject", JSON.toJSON(mvcObject));
        return modelAndView;
    }

    //根据好友ID删除好友
    @RequestMapping("/delete_friend")
    @ResponseBody
    public MvcObject deleteFriendByFriendId(HttpServletRequest request) {
        MvcObject mvcObject = null;

        String requestFriendId = request.getParameter("friendId");
        HttpSession session = request.getSession();
        String requestUserId = (String) session.getAttribute("USERID");
        if (requestUserId != null && !("".equals(requestUserId))) {
            int result = relationService.deleteFriendByUserIdAndFriendId(requestUserId, requestFriendId);
            if (result == 0) {
                mvcObject = new MvcObject("200", "删除失败");
            } else {
                mvcObject = new MvcObject("100", "删除" + result + "条记录");
            }
        } else {
            mvcObject = new MvcObject("系统异常", "202");
        }
        return mvcObject;
    }

    //修改好友所在的分组
    @RequestMapping("/move_friend_to_other_list")
    @ResponseBody
    public MvcObject moveFriendToOtherList(HttpServletRequest request) {
        MvcObject mvcObject = null;

        //我也不知道前端到时会怎么写，这里先从request里获取，以后写好再改    2019年7月22日 16:36:21
        String requestFriendId = request.getParameter("friendId");
        String requestListId = request.getParameter("listId");
        HttpSession session = request.getSession();
        String requestUserId = (String) session.getAttribute("USERID");
        if (requestUserId != null && !("".equals(requestUserId))) {
            int result = relationService.moveFriendToOtherList(requestUserId, requestFriendId, requestListId);
            if (result == 0) {
                mvcObject = new MvcObject("200", "更新失败");
            } else {
                mvcObject = new MvcObject("100", "更新" + result + "条记录");
            }
        } else {
            mvcObject = new MvcObject("系统异常", "202");
        }
        return mvcObject;
    }

    //根据userId查找用户拥有的好友分组
    @RequestMapping("query_friend_list")
    @ResponseBody
    public ModelAndView queryFriendListByUserId(HttpServletRequest request) {
        MvcObject mvcObject = null;
        Map<String, Object> resultMap = new HashMap<>();
        ModelAndView modelAndView = null;

        HttpSession session = request.getSession();
        String requestUserId = (String) session.getAttribute("USERID");
        if (requestUserId != null && !("".equals(requestUserId))) {
            List<FriendList> friendList = friendListService.queryFriendListByUserId(requestUserId);
            if (friendList.size() != 0) {
                resultMap.put("friendList", friendList);
                mvcObject = new MvcObject("查找分组成功", "100", resultMap);
            } else {
                mvcObject = new MvcObject("查找分组失败，查无不到该用户的相关数据", "200");
            }
        } else {
            mvcObject = new MvcObject("系统异常", "202");
        }
        modelAndView = new ModelAndView("forward:../my_friend_list.jsp", "mvcObject", JSON.toJSON(mvcObject));
        return modelAndView;
    }

    //根据分组id和userId查看指定分组下的好友
    @RequestMapping("/query_friend")
    @ResponseBody
    public ModelAndView queryFriendByListId(HttpServletRequest request) {
        MvcObject mvcObject = null;
        Map<String, Object> resultMap = new HashMap<>();
        ModelAndView modelAndView = null;

        String requestListId = request.getParameter("listId");
        HttpSession session = request.getSession();
        String requestUserId = (String) session.getAttribute("USERID");
        if (requestUserId != null && !("".equals(requestUserId))) {
            List<Friend> list = relationService.queryFriendByListId(requestUserId, requestListId);
            if (list.size() != 0) {
                List<User> userList = userService.queryUserListByQueryList(list);
                resultMap.put("userList",userList);
                mvcObject = new MvcObject("查询成功，共" + list.size() + "条数据", "100", resultMap);
            } else {
                mvcObject = new MvcObject("查询失败", "200");
            }
        } else {
            mvcObject = new MvcObject("系统异常", "202");
        }
        modelAndView = new ModelAndView("forward:../friend_list_result.jsp", "mvcObject", JSON.toJSON(mvcObject));
        return modelAndView;
    }

    /*
     * 展示搜索用户的个人资料
     * */
    @RequestMapping("/personal_info")
    @ResponseBody
    public ModelAndView showPersonalInfo(HttpServletRequest request) {
        MvcObject mvcObject = null;
        ModelAndView modelAndView = null;
        Map<String, Object> resultMap = new HashMap<>(16);

        String requestUserId = request.getParameter("userId");
        if (requestUserId != null && !("".equals(requestUserId))) {
            //查询个人信息，并且封装到user类中
            User user = userService.queryUserInfoByUserId(requestUserId);
            if (user != null) {
                resultMap.put("user", user);
                mvcObject = new MvcObject("查询成功", "100", resultMap);
            } else {
                mvcObject = new MvcObject("查询失败", "200");
            }
        } else {
            mvcObject = new MvcObject("系统异常", "202");
        }
        modelAndView = new ModelAndView("forward:../query_friend_info.jsp", "mvcObject", JSON.toJSON(mvcObject));
        return modelAndView;
    }


//    @RequestMapping("/addFriend")
//    @ResponseBody
//    public MvcObject addFriendByNickname(HttpServletRequest request) {
//        MvcObject mvcObject = null;
//
//        HttpSession session = request.getSession();
//        String accountNumber = (String) session.getAttribute("ACCOUNTNUMBER");
//        String friendNickname = request.getParameter("friendNickname");
//        final String DEFAULT_FRIEND_LIST_ID = "0";  //默认好友列表为0
//
//        //先用好友昵称进行查询
//        User queryUser = relationService.queryUserByNickname(friendNickname);
//
//        if (queryUser == null) {
//            //查不到此用户，实际上是正常查询成功了，所以代码用202
//            mvcObject = new MvcObject("查找不到此用户", "202");
//        } else {
//            //再查询用户的userId
//            User thisUser = relationService.queryUserByNickname(accountNumber);
//            System.out.println("user:" + thisUser.getNickname() + "...." + "queryUser:" + queryUser.getNickname());
//
//            Friend friend = new Friend();
//            friend.setUserId(thisUser.getUserId());
//            friend.setFriendId(queryUser.getUserId());
//            friend.setListId(DEFAULT_FRIEND_LIST_ID);
//            Boolean flag = relationService.queryRelationStatus(friend);
//            if (flag == false) {
//                int result = relationService.addFriendByUserId(friend);
//                switch (result) {
//                    case 0:
//                        mvcObject = new MvcObject("增加好友失败", "100");
//                        break;
//                    case 1:
//                        mvcObject = new MvcObject("增加好友成功", "200");
//                        break;
//                }
//            } else {
//                mvcObject = new MvcObject("已添加该好友", "202");
//            }
//        }
//        return mvcObject;
//    }


}