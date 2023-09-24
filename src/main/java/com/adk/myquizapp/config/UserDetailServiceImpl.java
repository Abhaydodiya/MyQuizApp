package com.adk.myquizapp.config;

import com.adk.myquizapp.model.User;
import com.adk.myquizapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //fetch user from db
        User userByEmail = userRepository.getUserByEmail(username);
        if (userByEmail == null) {
            throw new UsernameNotFoundException("Could not found user!!");
        }
        return new CustomUserDetails(userByEmail);
    }

}
