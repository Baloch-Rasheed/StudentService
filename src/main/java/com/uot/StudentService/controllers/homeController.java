package com.uot.StudentService.controllers;

import com.uot.StudentService.services.AuthService;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class homeController {
    public homeController(AuthService service) {
        this.service = service;
    }

    AuthService service;

    // Authentication mapping for a user
    @GetMapping("/")
    public String index(){
        if(service.isUserLoggedIn()){
            return "you have successfully logged in";
        }
        return "please login for service to use";
    }

    @GetMapping("/login")
    public String login(@PathParam(value = "userName") String userName, @PathParam(value = "password") String password){
        if(!service.isUserLoggedIn()){
            service.login(userName, password);
            return "Successfully LoggedIn";
        }
        return "A user already logged in";
    }

    @GetMapping("/logout")
    public String logout(){
        if(service.isUserLoggedIn()){
            service.logOut();
            return "user logged out";
        }
        return "user isn't logged in";
    }

}
