package com.uot.StudentService.controllers.api;

import com.uot.StudentService.models.User;
import com.uot.StudentService.services.AuthService;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/auth", produces= {"application/json", "text/xml"})
@SessionAttributes("AuthService")
public class homeController {
    public homeController(AuthService service) {
        this.service = service;
        dummyUsers();
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
    public String login(@RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password){

        if(!service.isUserLoggedIn()){
            if(service.login(userName, password) != null){
                return "Successfully LoggedIn";
            }
            return "username or password is wrong";
        }
        return "A user already logged in";
    }

    @GetMapping("/register")
    public String register(@PathParam(value = "userName") String userName, @PathParam(value = "password") String password){
        if(!service.isUserLoggedIn()){
            final User newUser = service.register(userName, password);
            if(newUser != null){
                service.login(newUser.getUserName(),newUser.getPassword());
                return "A new user registered and signed in";
            }
            return "username already exists.. to continue please login... or use a new username";
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

    private void dummyUsers(){
        service.register("Baloch","123456");
        service.register("Imdad","123456");
    }
}
