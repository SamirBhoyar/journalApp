package org.journalApp.config;
//++ Third version on code: v3 ++//
//This is User which has journal relation application, which use Mongodb for storage of User and Journal data.
//Here we are applying the Spring Security with authentication and authorization as per User role.


import org.journalApp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests()

//                      (request ->request(
//                        .requestMatchers("/public/**")
//                        .requestMatchers("/journal/**", "/user/**").authenticated()
//                        .requestMatchers("/admin/**")
//                        .hasRole("ADMIN")
                        .antMatchers("/userJournals/**","/user/**").authenticated()  //when user get authenticate it detail store in SecurityContextHolder
                        .antMatchers("/admin/**").hasRole("Admin")  //authorize only those user which has admin role
                        .anyRequest().permitAll()
                        .and()
                        .httpBasic(Customizer.withDefaults())
                        .csrf(AbstractHttpConfigurer::disable) //if you enable it: then this will not happen Cross-site request forgery (also known as CSRF) is a web security vulnerability that allows an attacker to induce users to perform actions that they do not intend to perform.
                        .build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        //auth.userDetailsService(userDetailsService) :-fetch all the detail of user which let service know which User have admin role
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
