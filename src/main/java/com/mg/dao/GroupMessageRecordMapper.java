package com.mg.dao;

import com.mg.model.GroupMessageRecord;

public interface GroupMessageRecordMapper {
    int deleteByPrimaryKey(Integer msgId);

    int insert(GroupMessageRecord record);

    int insertSelective(GroupMessageRecord record);

    GroupMessageRecord selectByPrimaryKey(Integer msgId);

    int updateByPrimaryKeySelective(GroupMessageRecord record);

    int updateByPrimaryKey(GroupMessageRecord record);
}