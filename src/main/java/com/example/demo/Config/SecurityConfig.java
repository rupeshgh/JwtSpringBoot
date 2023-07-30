package com.example.demo.Config;






import com.example.demo.Filter.CustomAuthenticationFilter;
import com.example.demo.Filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity()
@Configuration
public class SecurityConfig {


private AuthenticationManager authenticationManager;
    @Bean
    PasswordEncoder passwordEncoder (){
        return NoOpPasswordEncoder.getInstance();
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {

        this.authenticationManager=authenticationConfiguration.getAuthenticationManager();
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/login/**").permitAll();
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/auth/**").permitAll();
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/user/**").hasAuthority("ROLE_USER");
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN");
                    authorizationManagerRequestMatcherRegistry.anyRequest().authenticated();
                })

                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(new CustomAuthenticationFilter(authenticationManager))
                .addFilterBefore(new JwtFilter(),UsernamePasswordAuthenticationFilter.class)
                .httpBasic(AbstractHttpConfigurer::disable)

        ;

        return httpSecurity.build();
    }

}
