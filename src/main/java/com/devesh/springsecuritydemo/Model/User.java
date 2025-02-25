package com.devesh.springsecuritydemo.Model;

import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {

    private String userId;

    private String name;

    private String email;


    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public User() {
    }
}
