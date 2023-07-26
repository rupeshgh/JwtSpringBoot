package com.example.demo.Filter;//package com.example.JwtFinal.Filter;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.stream.Collectors;
//
//@AllArgsConstructor
//@NoArgsConstructor
//public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//private  AuthenticationManager authenticationManager;
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        String username=request.getParameter("username");
//        String password=request.getParameter("password");
//        System.out.println(username + password);
//
//
//
//
//
//        System.out.println("here: before ");
//
//        UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(username,password);
//
//         Authentication authentication= authenticationManager.authenticate(token);
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        System.out.println("here: "+ authentication.getPrincipal());
//        return authentication;
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        UserDetails user= (UserDetails) authResult.getPrincipal();
//        System.out.println("on success auth"+user.getUsername()+ user.getPassword());
//        Algorithm algorithm= Algorithm.HMAC256("secret".getBytes());
//
//
//
//        String access_token= JWT.create().withSubject(user.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis()+10*500*1000))
//                        .withIssuer((request.getRequestURI().toString()))
//                                .withClaim("roles",  user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//                                        .sign(algorithm);
//
//
//        String refresh_token= JWT.create().withSubject(user.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis()+30*50*1000))
//                .withIssuer((request.getRequestURI().toString()))
//                .withClaim("roles",  user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//                .sign(algorithm);
//
//
//
//        response.setHeader("access_token",access_token);
//        response.setHeader("refresh_token",refresh_token);
//
//
//
//
//    }
//}
