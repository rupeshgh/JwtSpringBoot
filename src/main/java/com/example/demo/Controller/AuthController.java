package com.example.demo.Controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.example.demo.Config.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/")
    public void authenticate(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletResponse response, HttpServletRequest request){

        UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(username,password);
        Authentication authentication=authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails user= (UserDetails) authentication.getPrincipal();

        if(authenticationManager==null){
            System.out.println("Null authmang");
        }


                Algorithm algorithm= Algorithm.HMAC256("secret".getBytes());



        String access_token= JWT.create().withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+10*500*1000))
                        .withIssuer((request.getRequestURI().toString()))
                                .withClaim("roles",  user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                                        .sign(algorithm);


        String refresh_token= JWT.create().withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+30*50*1000))
                .withIssuer((request.getRequestURI().toString()))
                .withClaim("roles",  user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);



        response.setHeader("access_token",access_token);
        response.setHeader("refresh_token",refresh_token);
















        System.out.println("User:"+(CustomUserDetails)authentication.getPrincipal());
    }

}

