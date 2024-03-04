package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService implements UserDetailsService {

    @Autowired
    private UserRepository appUserRepo;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> userList = appUserRepo.findByEmail(username);
        if(userList == null || userList.size() < 1){
            throw new UsernameNotFoundException("User does not exist");
        }
        return userList.get(0);
    }
}
