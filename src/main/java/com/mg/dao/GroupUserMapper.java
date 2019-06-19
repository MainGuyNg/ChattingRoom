package com.mg.dao;

import com.mg.model.GroupUser;

public interface GroupUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GroupUser record);

    int insertSelective(GroupUser record);

    GroupUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupUser record);

    int updateByPrimaryKey(GroupUser record);
}