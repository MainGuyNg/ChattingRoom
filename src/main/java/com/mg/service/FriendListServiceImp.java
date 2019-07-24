package com.mg.service;

import com.mg.dao.FriendListMapper;
import com.mg.dao.FriendMapper;
import com.mg.model.FriendList;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("friendListService")
public class FriendListServiceImp implements FriendListService {

    @Resource
    FriendListMapper friendListMapper;
    @Resource
    FriendMapper friendMapper;

    @Override
    public Integer addDefaultFriendListWhenUserRegister(String userId) {
        FriendList friendList = new FriendList();
        friendList.setListId("0");
        friendList.setListName("我的好友");
        friendList.setUserId(userId);

        int result = friendListMapper.insertSelective(friendList);
        return result;
    }

    @Override
    public Integer addFriendList(FriendList record) {
        int result = friendListMapper.insertSelective(record);
        return result;
    }

    @Override
    public List queryFriendListByUserId(String userId) {
        List<FriendList> queryList = friendListMapper.queryFriendListByUserId(userId);
        if (queryList.size() != 0) {
            for (int i = 0; i < queryList.size(); i++) {
                //获取该下标的对象，并且设置查询到的分组好友数，并且重新给该下标元素赋值
                FriendList friendList = queryList.get(i);
                friendList.setFriendCount(friendMapper.queryFriendCountByListIdAndUserId(userId, friendList.getListId()));
                queryList.set(i, friendList);
            }
        }
        return queryList;
    }
}
