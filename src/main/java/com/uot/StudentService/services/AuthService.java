package com.uot.StudentService.services;

import com.uot.StudentService.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AuthService {
    private List<User> users = new ArrayList<>();
    private User user;

    public boolean isUserLoggedIn(){
        return user != null;
    }

    public User login(String userName, String password){
        final User _user = getUser(userName);
        if(_user != null && _user.getPassword().equals(password)){
            this.user = _user;
            return _user;
        }
        return null;
    }

    public User register(String userName, String password) {
        final User _user = getUser(userName);
        if (_user == null ) {
            final User u = new User();
            u.setPassword(password);
            u.setUserName(userName);
            users.add(u);
            return u;
        }
        return null;
    }

    private User getUser(String userName){
        for(User u:users){
            if(u.getUserName().equals(userName)){
                return u;
            }
        }
        return null;
    }

    public void logOut(){
        user = null;
    }
}
