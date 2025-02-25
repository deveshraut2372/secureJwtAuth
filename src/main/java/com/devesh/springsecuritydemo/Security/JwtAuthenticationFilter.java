package com.devesh.springsecuritydemo.Security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Principal;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    Logger logger= LoggerFactory.getLogger(JwtAuthenticationFilter.class);


// any request call the this method should be call
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            String requestHeader=request.getHeader("Authorization");
//         Authorization = Bearer bdsjbhcjasbcjashvdjsahvca
            logger.info(" Tokent ="+requestHeader);

            String username=null;
            String token=null;

            if(requestHeader!=null && requestHeader.startsWith("Bearer") )
            {
                token=requestHeader.substring(7);
                try
                {
                    username=jwtHelper.getUsernameFromToken(token);
                }catch (IllegalArgumentException e)
                {
                    logger.info(" Ilegal Argument While fetching the Username !!");
                    e.printStackTrace();
                }catch (ExpiredJwtException e)
                {
                    logger.info(" Given Jwt Token Expired !!");
                    e.printStackTrace();
                }catch (MalformedJwtException e)
                {
                    logger.info(" Some changed has done in token !! Invalid Token ");
                    e.printStackTrace();
                }catch (Exception e)
                {
                    logger.info("  Exception is found ="+e.getMessage());
                    e.printStackTrace();
                }
            }else{
                logger.info(" Invalid Header Value !");
            }

            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
            {
                //fetch User details from username
                UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
                Boolean validateToken=jwtHelper.validateToken(token,userDetails);

                if(validateToken)
                {
                    UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }else {
                    logger.info("  Validation fails !!");
                }
            }
            filterChain.doFilter(request,response);
    }
}
