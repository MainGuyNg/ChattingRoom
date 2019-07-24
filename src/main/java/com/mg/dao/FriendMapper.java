package com.mg.dao;

import com.mg.model.Friend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Friend record);

    int insertSelective(Friend record);

    Friend selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Friend record);

    int updateByPrimaryKey(Friend record);

    Friend queryRelationStatus(Friend record);

    int deleteFriendByUserIdAndFriendId(@Param("userId") String userId, @Param("friendId") String friendId);

    int modifyListIdByUserIdAndFriendId(@Param("userId") String userId, @Param("friendId") String friendId, @Param("listId") String listId);

    List queryFriendByListId(@Param("userId") String userId, @Param("listId") String listId);

    int queryFriendCountByListIdAndUserId(@Param("userId") String userId, @Param("listId") String listId);
}