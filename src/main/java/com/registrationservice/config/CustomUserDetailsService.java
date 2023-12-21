package com.registrationservice.config;

import com.registrationservice.model.User;
import com.registrationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user =  userRepository.findByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException("User with that email does'nt Exist");
        }
        else{
            return new CustomUser(user);
        }
    }
}
