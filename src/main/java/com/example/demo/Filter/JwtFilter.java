package com.example.demo.Filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class JwtFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Extract Authorization header
        String Authorization=request.getHeader("Authorization");
    if(Authorization!=null && Authorization.startsWith("Bearer ")){

        //if starts with Bearer its a Jwt Token
        //Get token and decode the token
        try{
            String token=Authorization.substring(7);

            Algorithm algorithm=Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier= JWT.require(algorithm).build();

            DecodedJWT decodedJWT=verifier.verify(token);

            //extracting username and roles from token
            //claim called roles was created during token generation
            String username= decodedJWT.getSubject();
            String[] roles=decodedJWT.getClaim("roles").asArray(String.class);

            Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
            Arrays.stream(roles).forEach(role->authorities.add(new SimpleGrantedAuthority(role)));


            //authenticate the user
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(username,null,authorities);
             SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


        }catch (Exception e)
        {
            //Can result in different types of  exception
            e.printStackTrace();
        }



    }


        filterChain.doFilter(request,response);
    }
}
