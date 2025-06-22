package com.example.service;

import com.example.model.Driver;
import com.example.model.User;
import com.example.repository.DriverRepository;
import com.example.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    private DriverRepository driverRepository;
    private UserRepository userRepository;

    public CustomerUserDetailsService(DriverRepository driverRepository, UserRepository userRepository){
        this.driverRepository=driverRepository;
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();

        User user = userRepository.findByEmail(username);

        if(user!=null){
            return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
        }

        Driver driver = driverRepository.findByEmail(username);
        if(driver!=null){
            return new org.springframework.security.core.userdetails.User(driver.getEmail(),driver.getPassword(),authorities);
        }
        throw new UsernameNotFoundException("user not found with email: -"+ username);
    }
}
