package com.registrationservice.service;

import com.registrationservice.model.User;
import com.registrationservice.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    public User saveUser(User user) {
        System.out.println("inside save user");
        String password=passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setRoles("ROLE_USER");
        User newuser = userRepository.save(user);
        System.out.println("New User Saved");
        return newuser;
    }

    @Override
    public void removeSessionMessage() {
       HttpSession session = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
       session.removeAttribute("msg");
    }
}
