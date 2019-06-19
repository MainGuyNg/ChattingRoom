package com.mg.model;

import java.util.Date;

public class GroupUser {
    private Integer id;

    private String groupId;

    private String userId;

    private Integer userTypeInGroup;

    private Date joinTime;

    private String userNickname;

    private Integer speakAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getUserTypeInGroup() {
        return userTypeInGroup;
    }

    public void setUserTypeInGroup(Integer userTypeInGroup) {
        this.userTypeInGroup = userTypeInGroup;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname == null ? null : userNickname.trim();
    }

    public Integer getSpeakAmount() {
        return speakAmount;
    }

    public void setSpeakAmount(Integer speakAmount) {
        this.speakAmount = speakAmount;
    }
}