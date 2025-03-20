package com.test.wepapp.service;

import com.test.wepapp.dto.UserDto;
import com.test.wepapp.dto.UserLogDto;
import com.test.wepapp.service.client.MsPatientFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    @Autowired
    private MsPatientFeignClient msPatientFeignClient;

    public UserDto getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return msPatientFeignClient.findByEmail(new UserLogDto(user.getUsername())).orElse(null);
    }
}
