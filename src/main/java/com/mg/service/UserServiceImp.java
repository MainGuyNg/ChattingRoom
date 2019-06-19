package com.mg.service;

import com.mg.dao.UserMapper;
import com.mg.model.User;
import com.mg.utils.SystemCurrentTimeUtil;
import com.mg.utils.UuidUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImp implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public int register(User record) {
        record.setUserId(UuidUtil.getUserId());

        SystemCurrentTimeUtil.getCurrentDate();


        int result = userMapper.insert(record);
        return result;
    }
}
