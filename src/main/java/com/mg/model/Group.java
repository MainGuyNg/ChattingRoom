package com.mg.model;

import java.util.Date;

public class Group {
    private Integer id;

    private String groupId;

    private String groupName;

    private String groupDescription;

    private String groupHeadUrl;

    private Date createTime;

    private String createrUserId;

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription == null ? null : groupDescription.trim();
    }

    public String getGroupHeadUrl() {
        return groupHeadUrl;
    }

    public void setGroupHeadUrl(String groupHeadUrl) {
        this.groupHeadUrl = groupHeadUrl == null ? null : groupHeadUrl.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreaterUserId() {
        return createrUserId;
    }

    public void setCreaterUserId(String createrUserId) {
        this.createrUserId = createrUserId == null ? null : createrUserId.trim();
    }
}