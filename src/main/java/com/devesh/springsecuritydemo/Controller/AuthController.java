package com.devesh.springsecuritydemo.Controller;

import com.devesh.springsecuritydemo.Payload.JwtRequest;
import com.devesh.springsecuritydemo.Payload.JwtResponse;
import com.devesh.springsecuritydemo.Security.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private JwtHelper jwtHelper;


    private UserDetailsService userDetailsService;

    private AuthenticationManager manager;


    private Logger logger= LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request)
    {
        this.doAuthenticate(request.getEmail(),request.getPassword());

        UserDetails userDetails=userDetailsService.loadUserByUsername(request.getEmail());
        String token=jwtHelper.generateToken(userDetails);

//        JwtResponse response=JwtResponse.builder()
//                        .jwtToken(token)
//                        .username(userDetails.getUsername())
//                        .build();

        JwtResponse response=new JwtResponse();
        response.setJwtToken(token);
        response.setUsername(userDetails.getUsername());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    private void doAuthenticate(String email,String password)
    {

        UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(email,password);
        try
        {
            manager.authenticate(authentication);

        }catch (BadCredentialsException e)
        {
            e.printStackTrace();
            throw  new BadCredentialsException(" InValid UserName and Password !");
        }

    }


}
