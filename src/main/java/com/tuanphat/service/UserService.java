package com.tuanphat.service;

import com.tuanphat.entity.User;

public interface UserService {

    public User saveUser(User user);

    public boolean existEmailCheck(String email);

}
