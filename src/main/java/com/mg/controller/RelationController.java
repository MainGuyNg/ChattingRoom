package com.mg.controller;

import com.alibaba.fastjson.JSON;
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

        //模糊查询可能会查到多个相同的User对象，所以用list封装
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

        //因为模糊查询和精确查询都公用一个页面，所以这里也干脆封进去list里面
        List<User> queryUserList = relationService.queryUserByAccountNumber(requestAccountNumber);
        if (queryUserList == null && queryUserList.size() == 0) {
            mvcObject = new MvcObject("查不到此用户：" + requestAccountNumber, "100");
        } else {
            resultMap.put("userList", queryUserList);
            mvcObject = new MvcObject("查找成功", "100", resultMap);
        }
        modelAndView = new ModelAndView("forward:../query_user_result.jsp", "mvcObject", JSON.toJSON(mvcObject));
        return modelAndView;
    }

    //根据好友ID删除好友
    @RequestMapping("/delete_friend")
    @ResponseBody
    public ModelAndView deleteFriendByFriendId(HttpServletRequest request) {
        MvcObject mvcObject = null;
        ModelAndView modelAndView = null;

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
        modelAndView = new ModelAndView("forward:../modify_result.jsp", "mvcObject", JSON.toJSON(mvcObject));
        return modelAndView;
    }

    //修改好友所在的分组
    @RequestMapping("/move_friend_to_other_list")
    @ResponseBody
    public ModelAndView moveFriendToOtherList(HttpServletRequest request) {
        MvcObject mvcObject = null;
        ModelAndView modelAndView = null;

        String requestFriendId = request.getParameter("friendId");
        String requestListId = request.getParameter("listId");
        HttpSession session = request.getSession();
        String requestUserId = (String) session.getAttribute("USERID");

        if (requestUserId != null && !("".equals(requestUserId))) {
            //三个参数依次是userId,friendId,listId
            int result = relationService.moveFriendToOtherList(requestUserId, requestFriendId, requestListId);
            if (result == 0) {
                mvcObject = new MvcObject("200", "更新失败");
            } else {
                mvcObject = new MvcObject("100", "更新" + result + "条记录");
            }
        } else {
            mvcObject = new MvcObject("系统异常", "202");
        }
        modelAndView = new ModelAndView("forward:../modify_result.jsp", "mvcObject", JSON.toJSON(mvcObject));
        return modelAndView;
    }


    /*
     * 修改好友信息的时候，会先从friend_list_result页面获取friendId
     * 然后会根据userId获取用户下拥有哪些分组，再把分组friendList和要修改的好友id封装到hashMap里面
     * 最后传到modify_friend.jsp里进行修改
     * */
    @RequestMapping("modify_friend")
    @ResponseBody
    public ModelAndView modifyFriend(HttpServletRequest request) {
        MvcObject mvcObject = null;
        ModelAndView modelAndView = null;
        Map<String, Object> resultMap = new HashMap<>();

        HttpSession session = request.getSession();
        String requestFriendId = request.getParameter("friendId");
        String requestUserId = (String) session.getAttribute("USERID");

        if (requestUserId != null && !("".equals(requestUserId))) {
            //根据userId获取该用户下的好友分组
            List<FriendList> friendList = friendListService.queryFriendListByUserId(requestUserId);
            if (friendList != null) {
                resultMap.put("friendList", friendList);
                resultMap.put("friendId", requestFriendId);
                mvcObject = new MvcObject("查询成功", "100", resultMap);
            } else {
                mvcObject = new MvcObject("查询数据为空，系统异常", "202");
            }
        } else {
            mvcObject = new MvcObject("系统异常", "202");
        }
        modelAndView = new ModelAndView("forward:../modify_friend.jsp", "mvcObject", JSON.toJSON(mvcObject));
        return modelAndView;
    }

    //根据userId查找用户拥有的好友分组
    @RequestMapping("/query_friend_list")
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
            //查询用户分组下的好友列表，list的泛型指定为Friend
            List<Friend> queryFriendList = relationService.queryFriendByListId(requestUserId, requestListId);
            if (queryFriendList != null) {
                //根据上面的好友列表里的friendId查找详细的用户信息（去除了密码），并且一次封装成一个新的list，list的泛型指定为User
                List<User> friendList = userService.queryUserListByQueryList(queryFriendList);
                resultMap.put("friendList", friendList);
                mvcObject = new MvcObject("查询成功，共" + queryFriendList.size() + "条数据", "100", resultMap);
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
     * 查看搜索用户的个人资料
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

    //修改好友备注
    @RequestMapping("/modify_friend_remark")
    @ResponseBody
    public ModelAndView modifyFriendRemark(HttpServletRequest request) {
        MvcObject mvcObject = null;
        ModelAndView modelAndView = null;

        String requestFriendRemark = request.getParameter("friendRemark");      //前端传入的好友备注
        String requestFriendId = request.getParameter("friendId");
        HttpSession session = request.getSession();
        String requestUserId = (String) session.getAttribute("USERID");

        if (requestUserId != null & !("".equals(requestUserId))) {
            Friend friend = new Friend();
            friend.setUserId(requestUserId);
            friend.setFriendId(requestFriendId);
            friend.setFriendRemark(requestFriendRemark);

            int result = relationService.modifyFriendRemark(friend);

            if (result == 0) {
                mvcObject = new MvcObject("更新失败", "200");
            } else {
                mvcObject = new MvcObject("更新成功", "100");
            }
        } else {
            mvcObject = new MvcObject("系统异常", "202");
        }
        modelAndView = new ModelAndView("forward:../modify_result.jsp", "mvcObject", JSON.toJSON(mvcObject));
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