package com.mg.service;

import com.mg.model.Friend;
import com.mg.model.User;

import java.util.List;

public interface RelationService {
    List queryUserByNickname(String nickname);

    Integer addFriendByUserId(Friend record);

    Boolean queryRelationStatus(Friend record);

    User queryUserByAccountNumber(String accountNumber);

    Integer deleteFriendByUserIdAndFriendId(String userId, String friendId);

    Integer moveFriendToOtherList(String userId, String friendId, String listId);

    List queryFriendByListId(String userId, String listId);

    Integer modifyFriendRemark(Friend record);

}
