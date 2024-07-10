package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserServices userServices;


    @GetMapping("/health-check")
    public String healthcheck(){
        return "ok";
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        userServices.saveNewUser(user);
    }
}
