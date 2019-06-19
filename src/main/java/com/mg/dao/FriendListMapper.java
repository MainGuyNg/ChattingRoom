package com.mg.dao;

import com.mg.model.FriendList;

public interface FriendListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FriendList record);

    int insertSelective(FriendList record);

    FriendList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FriendList record);

    int updateByPrimaryKey(FriendList record);
}