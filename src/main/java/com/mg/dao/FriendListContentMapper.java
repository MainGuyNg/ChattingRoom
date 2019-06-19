package com.mg.dao;

import com.mg.model.FriendListContent;

public interface FriendListContentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FriendListContent record);

    int insertSelective(FriendListContent record);

    FriendListContent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FriendListContent record);

    int updateByPrimaryKey(FriendListContent record);
}