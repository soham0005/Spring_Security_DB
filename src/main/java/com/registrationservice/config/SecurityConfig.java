package com.registrationservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    public CustomAuthSuccessHandler customAuthSuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService getUserDetailsService(){
        return new CustomUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider getAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);

//        WITHOUT ROLE BASED AUTHORIZATION
//        http.authorizeHttpRequests(request->{
//            request.requestMatchers("/","/register","/signin","/saveUser").permitAll();
//            request.requestMatchers("/user/**").authenticated();
//        }).formLogin(form->{
//            form.loginPage("/signin").loginProcessingUrl("/userLogin")
//                    .defaultSuccessUrl("/user/profile").permitAll();
//        });

//        ROLE BASED AUTHORIZATION
        http.authorizeHttpRequests(request->{
            request.requestMatchers("/","/register","/signin","/saveUser").permitAll();
            request.requestMatchers("/user/**").hasRole("USER");
            request.requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated();
        }).formLogin(form->{
            form.loginPage("/signin").loginProcessingUrl("/userLogin")
                    .successHandler(customAuthSuccessHandler).permitAll();
        });
        return http.build();
    }
}
