package com.mg.dao;

import com.mg.model.FriendList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FriendList record);

    int insertSelective(FriendList record);

    FriendList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FriendList record);

    int updateByPrimaryKey(FriendList record);

    List queryFriendListByUserId(@Param("userId") String userId);
}