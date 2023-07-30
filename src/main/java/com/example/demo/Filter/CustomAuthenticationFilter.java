package com.example.demo.Filter;//package com.example.JwtFinal.Filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor

//By default, UsernamePassword AuthenticationFilter creates endpoint /login and method post to get login credentials
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

private  AuthenticationManager authenticationManager;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
       //extract username and password from request param or form data

        String username=request.getParameter("username");
        String password=request.getParameter("password");

        //create authentication token and authenticate using authentication manager
        UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(username,password);
         Authentication authentication= authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

       //on successfull authentication generate access token and refresh token and send it to client
        UserDetails user= (UserDetails) authResult.getPrincipal();


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


        System.out.println("Access_token:::"+access_token);
        System.out.println("Refresh_token:::"+refresh_token);


        response.setHeader("access_token",access_token);
        response.setHeader("refresh_token",refresh_token);


    }
}
