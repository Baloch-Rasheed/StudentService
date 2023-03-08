package com.uot.StudentService.services;

import com.uot.StudentService.models.User;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private User user;

    public boolean isUserLoggedIn(){
        return user != null;
    }

    public void login(String userName, String password){
        user = new User();
        user.setUserName(userName);
        user.setPassword(password);
    }

    public void logOut(){
        user = null;
    }
}
