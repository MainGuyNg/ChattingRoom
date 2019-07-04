package com.mg.service;

import com.mg.dao.UserMapper;
import com.mg.model.User;
import com.mg.utils.SystemCurrentTimeUtil;
import com.mg.utils.UuidUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service("userService")
public class UserServiceImp implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public int register(User record) {
        record.setUserId(UuidUtil.getUserId());

        record.setCreateTime(new Date(SystemCurrentTimeUtil.getCurrentDate()));

        String thisAccountNumber = record.getAccountNumber();

        //查询用户是否已存在，如果已存在，queryUser!=null
        User queryUser = userMapper.selectUserByAccountNumber(thisAccountNumber);
        if(queryUser!=null){
            return 0;
        }else {
            int result=userMapper.insertSelective(record);
            return result;
        }
    }

    @Override
    public User login(String accountNumber) {
        User user = userMapper.selectUserByAccountNumber(accountNumber);
        return user;
    }

    @Override
    public int updateLoginTime(String accountNumber){
        User user = new User();
        user.setAccountNumber(accountNumber);
        user.setLoginTime(new Date(SystemCurrentTimeUtil.getCurrentDate()));
        return userMapper.updateLoginTimeByAccountNumber(user);
    }

    @Override
    public int modifyPersonalInfo(User record) {
        record.setModifyTime(new Date(SystemCurrentTimeUtil.getCurrentDate()));
        int result = userMapper.modifyPersonalInfoByAccountNumber(record);
        return result;
    }

    @Override
    public User selectUserToShowPersonalInfo(String accountNumber) {
        User user = userMapper.selectUserByAccountNumber(accountNumber);
        user.setPassword("");
        return user;
    }

    @Override
    //result   0：更新0条数据  1：更新一条数据  102：密码不一致  101：查不到用户数据
    public int modifyPassword(String accountNumber, String oldPassword, String newPassword) {
        User user = userMapper.selectUserByAccountNumber(accountNumber);
        int result = 0;
        if(user!=null){
            if(user.getPassword().equals(oldPassword)){
                user = new User();
                user.setAccountNumber(accountNumber);
                user.setPassword(newPassword);
                user.setModifyTime(new Date(SystemCurrentTimeUtil.getCurrentDate()));
                result = userMapper.modifyPassword(user);
                return result;
            }else {
                result = 102;
                return result;
            }
        }else {
            result = 101;
            return result;
        }
    }

    @Override
    public int updateUserHeadIcon(String accountNumber, String headUrl) {
        headUrl = headUrl.substring(headUrl.lastIndexOf("\\head_icon_img"));
        User user = new User();
        user.setAccountNumber(accountNumber);
        user.setHeadUrl(headUrl);
        user.setModifyTime(new Date(SystemCurrentTimeUtil.getCurrentDate()));
        int result = userMapper.updateUserHeadIcon(user);
        return result;
    }
}
