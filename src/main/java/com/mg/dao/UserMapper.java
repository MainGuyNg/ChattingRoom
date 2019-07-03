package com.mg.dao;

import com.mg.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

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

    int modifyPassword(@Param("accountNumber") String accountNumber, @Param("newPassword") String newPassword);

    int updateUserHeadIcon(@Param("accountNumber") String accountNumber,@Param("headUrl") String headUrl);
}