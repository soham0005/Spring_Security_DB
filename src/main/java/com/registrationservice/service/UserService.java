package com.registrationservice.service;

import com.registrationservice.model.User;

public interface UserService {
    public User saveUser(User user);
    public void removeSessionMessage();
}
