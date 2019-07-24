package com.mg.service;

import com.mg.model.FriendList;

import java.util.List;

public interface FriendListService {
    Integer addDefaultFriendListWhenUserRegister(String userId);

    Integer addFriendList(FriendList record);

    List queryFriendListByUserId(String userId);
}
