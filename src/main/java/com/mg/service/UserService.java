package com.mg.service;


import com.mg.model.User;

public interface UserService {
    int register(User record);
    User login(String accountNumber);
    int modifyPersonalInfo(User record);
    int updateLoginTime(String accountNumber);
    User selectUserToShowPersonalInfo(String accountNumber);
    int modifyPassword(String accountNumber,String oldPassword,String newPassword);
}
