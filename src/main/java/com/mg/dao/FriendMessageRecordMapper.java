package com.mg.dao;

import com.mg.model.FriendMessageRecord;

public interface FriendMessageRecordMapper {
    int deleteByPrimaryKey(Integer msgId);

    int insert(FriendMessageRecord record);

    int insertSelective(FriendMessageRecord record);

    FriendMessageRecord selectByPrimaryKey(Integer msgId);

    int updateByPrimaryKeySelective(FriendMessageRecord record);

    int updateByPrimaryKey(FriendMessageRecord record);
}