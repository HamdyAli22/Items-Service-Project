package com.item.service;

import com.item.model.UserAccount;

public interface UserService {

	boolean registerUser(UserAccount user);
    UserAccount login(String username, String password);
    String validateUser(UserAccount user,boolean forLogin);
    UserAccount getUserByUsername(String username);

}
