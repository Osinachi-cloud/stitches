package com.stitches.service.serviceImpl;

import com.stitches.model.AppUser;
import com.stitches.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    private Logger log = LoggerFactory.getLogger(UserInfoUserDetailsService.class);
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> userInfo = repository.findAppUserByEmail(username);
        log.info(" email val {} :", userInfo);
        return userInfo
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

    }
}