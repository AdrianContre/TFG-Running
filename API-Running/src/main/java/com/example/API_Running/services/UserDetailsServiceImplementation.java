package com.example.API_Running.services;

import com.example.API_Running.models.User;
import com.example.API_Running.models.UserDetailsImplementation;
import com.example.API_Running.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsServiceImplementation implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> loadedUser = userRepository.findByUsername(username);
        if (!loadedUser.isPresent()) {
            throw new UsernameNotFoundException("user not found");
        }
        else{
            User u = loadedUser.get();
            return new UserDetailsImplementation(u);
        }
    }
}
