package com.devesh.springsecuritydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class Myconfg {


//    spring security condif
    @Bean
    public UserDetailsService userDetailsService()
    {
        UserDetails userDetails=User.builder()
                .username("Devesh")
                .password(passwordEncoder().encode("Devesh"))
                .roles("USER")
                .build();

        UserDetails userDetails1=User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(userDetails1,userDetails);
    }

//    password encodeing
    @Bean
    public  PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

//    authentication
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception
//    {
//        return builder.getAuthenticationManager();
//    }


}
