package com.registrationservice.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        Set<String> roles =  AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        System.out.println("ROLES are-->> "+roles);

        if(roles.contains("ROLE_ADMIN")){
            System.out.println("ADMIN ROLE HAIII");
            response.sendRedirect("/admin/profile");
        }
        else if(roles.contains("ROLE_USER")){
            System.out.println("USERR ROLE HAIII");
            response.sendRedirect("/user/profile");
        }
    }
}
