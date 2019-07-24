package com.mg.service;


import com.mg.model.User;

public interface UserService {
    Integer register(User record);

    User login(String accountNumber);

    Integer modifyPersonalInfo(User record);

    Integer updateLoginTime(String accountNumber);

    User selectUserToShowPersonalInfo(String accountNumber);

    Integer modifyPassword(String accountNumber, String oldPassword, String newPassword);

    Integer updateUserHeadIcon(String accountNumber, String headUrl, String relativePath);
}
