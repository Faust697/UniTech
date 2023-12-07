package com.unitech.UniTech.service;
import com.unitech.UniTech.model.UserEntity;
import com.unitech.UniTech.repository.UserRepo;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepository;

    public CustomUserDetailsService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

   
   
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByPincode(username);
        		
        System.out.println("User "+username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return User.builder()
            .username(user.get().getUsername())
            .password(user.get().getPassword())
            .build();
    }
  

}
