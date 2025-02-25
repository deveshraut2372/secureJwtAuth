package com.devesh.springsecuritydemo.Service.impl;

import com.devesh.springsecuritydemo.Model.User;
import com.devesh.springsecuritydemo.Service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {


    List<User> store=new ArrayList<>();


    public List<User> getUsers()
    {
        store.add(new User(UUID.randomUUID().toString(), "Devesh", "devesh@gmail.com"));
        store.add(new User(UUID.randomUUID().toString(), "Kunal", "kunal@gmail.com"));
        store.add(new User(UUID.randomUUID().toString(), "Ajit", "ajit@gmail.com"));
        System.out.println(" store ="+store);
        return this.store;
    }


}
