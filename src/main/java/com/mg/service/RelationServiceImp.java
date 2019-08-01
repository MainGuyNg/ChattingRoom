package com.mg.service;

import com.mg.dao.FriendMapper;
import com.mg.dao.UserMapper;
import com.mg.model.Friend;
import com.mg.model.User;
import com.mg.utils.SystemCurrentTimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("relationService")
public class RelationServiceImp implements RelationService {

    @Resource
    UserMapper userMapper;
    @Resource
    FriendMapper friendMapper;

    @Override
    public List queryUserByNickname(String nickname) {
        List list = userMapper.queryUserByNickname(nickname);
        return list;
    }

    @Override
    public Integer addFriendByUserId(Friend record) {
        Date date = new Date(SystemCurrentTimeUtil.getCurrentDate());
        record.setAddTime(date);
        int result = friendMapper.insertSelective(record);
        if (result != 0) {
            String changeToUserId = record.getFriendId();
            String changeToFriendId = record.getUserId();
            record = new Friend();
            record.setUserId(changeToUserId);
            record.setFriendId(changeToFriendId);
            record.setAddTime(date);
            record.setListId("0");
            queryRelationStatus(record);
            result = friendMapper.insertSelective(record);
        }
        return result;
    }

    @Override
    public Boolean queryRelationStatus(Friend record) {
        Friend friend = friendMapper.queryRelationStatus(record);
        if (friend == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public User queryUserByAccountNumber(String accountNumber) {
        User user = userMapper.queryUserByAccountNumber(accountNumber);
        return user;
    }

    @Override
    public Integer deleteFriendByUserIdAndFriendId(String userId, String friendId) {
        Integer result = friendMapper.deleteFriendByUserIdAndFriendId(userId, friendId);
        return result;
    }

    @Override
    public Integer moveFriendToOtherList(String userId, String friendId, String listId) {
        Integer result = friendMapper.modifyListIdByUserIdAndFriendId(userId, friendId, listId);
        return result;
    }

    @Override
    public List queryFriendByListId(String userId, String listId) {
        List<Friend> list = friendMapper.queryFriendByListId(userId,listId);
        return list;
    }

    @Override
    public Integer modifyFriendRemark(Friend record) {
        Integer result = friendMapper.modifyFriendRemark(record);
        return result;
    }
}
