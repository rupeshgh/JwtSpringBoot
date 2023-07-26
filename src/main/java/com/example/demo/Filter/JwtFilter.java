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
    String Authorization=request.getHeader("Authorization");

    if(Authorization!=null && Authorization.startsWith("Bearer ")){

        try{
            String token=Authorization.substring(7);

            Algorithm algorithm=Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier= JWT.require(algorithm).build();

            DecodedJWT decodedJWT=verifier.verify(token);

            String username= decodedJWT.getSubject();
            String[] roles=decodedJWT.getClaim("roles").asArray(String.class);
            Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();


            Arrays.stream(roles).forEach(role->authorities.add(new SimpleGrantedAuthority(role)));

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(username,null,authorities);


            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            System.out.println("Token here:"+token);
            System.out.println("Username"+username);

            filterChain.doFilter(request,response);
        }catch (Exception e)
        {
            System.out.println(e.toString());
        }



    }


        filterChain.doFilter(request,response);
    }
}
