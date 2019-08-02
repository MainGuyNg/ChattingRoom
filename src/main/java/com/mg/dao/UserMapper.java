package com.mg.dao;

import com.mg.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectUserByAccountNumber(String accountNumber);

    int updateLoginTimeByAccountNumber(User record);

    int modifyPersonalInfoByAccountNumber(User record);

    int modifyPassword(User record);

    int updateUserHeadIcon(User record);

    List queryUserByNickname(String nickname);

    //这个方法在查询时不封装password字段
    List queryUserByAccountNumber(String AccountNumber);

    //这个方法在查询时不封装password字段
    User queryUserByUserId(String userId);

}