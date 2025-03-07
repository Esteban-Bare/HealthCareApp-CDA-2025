package com.test.wepapp.service;

import com.test.wepapp.dto.UserLogDto;
import com.test.wepapp.dto.UserToLogDto;
import com.test.wepapp.service.client.MsPatientFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private MsPatientFeignClient msPatientFeignClient;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserToLogDto user = msPatientFeignClient.findByEmail(new UserLogDto(email)).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new User(user.getEmail(), user.getPassword(),  Collections.singleton(() -> "ROLE_" + user.getRole().toUpperCase()));
    }
}
