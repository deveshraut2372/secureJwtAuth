package com.devesh.springsecuritydemo.Controller;

import com.devesh.springsecuritydemo.Model.User;
import com.devesh.springsecuritydemo.Service.UserService;
import com.devesh.springsecuritydemo.Service.impl.UserServiceImpl;
import com.devesh.springsecuritydemo.abcd;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("home")
public class HomeController {



    Logger log= LoggerFactory.getLogger(HomeController.class);


    @Autowired
    private UserService userService;

    @Autowired
    private UserServiceImpl userServiceimpl;


    @GetMapping("/user")
    public String getUser()
    {
        System.out.println("  User Is here ");
        return " user is here";
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers()
    {
        List<User> userList=new ArrayList<>();
//        userList.add(new User(UUID.randomUUID().toString(),"Devesh","devesh@gmail.com"));

//        userList=userServiceimpl.getUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/current-user")
    public String getLoggedInUser(Principal principal)
    {
        return principal.getName();
    }


















}
