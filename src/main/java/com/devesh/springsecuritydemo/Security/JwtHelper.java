package com.devesh.springsecuritydemo.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtHelper  {

    // requirement

    public static final long JWT_TOKEN_VLIDITY=5*60*60;

    //
    public String secret="cjhsdvajvcjavbikuvukdvbkdsjvkjdfhvajgviuhiefdbahjasl";

    //retrive username from jwt token
    public String getUsernameFromToken(String token)
    {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // retrive expiration Date from token
    public Date getExpirationDateFromToken(String token)
    {
        return getClaimFromToken(token,Claims::getExpiration);
    }


    // retrive data by claims
    public <T> T  getClaimFromToken(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims=getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retriveing any informatio from token we will need the secrate key
    public Claims getAllClaimsFromToken(String token)
    {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    // check is token expired
    public Boolean isTokenExpired(String token)
    {
        final  Date expiration=getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    //generateToken
    public String generateToken(UserDetails userDetails)
    {
        Map<String,Object> claims=new HashMap<>();
        return doGenerateToken(claims,userDetails.getUsername());
    }



// generate Tokent
    //
    private String doGenerateToken(Map<String,Object> claims,String subject)
    {
        return  Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VLIDITY))
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }


    // is valid token or not
    public  Boolean validateToken(String token,UserDetails userDetails)
    {
        final String username=getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())&&!isTokenExpired(token));
    }


}
