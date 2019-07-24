package com.mg.model;

public class FriendList {
    private Integer id;

    private String userId;

    private String listId;

    private String listName;

    //不打算往数据库添加这个字段，每次查询好友分组时去数据库查一边有多少数据就算了。。。
    private Integer friendCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId == null ? null : listId.trim();
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName == null ? null : listName.trim();
    }

    public Integer getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(Integer friendCount) {
        this.friendCount = friendCount;
    }

    @Override
    public String toString() {
        return "FriendList{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", listId='" + listId + '\'' +
                ", listName='" + listName + '\'' +
                ", friendCount=" + friendCount +
                '}';
    }
}