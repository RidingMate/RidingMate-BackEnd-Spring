package com.ridingmate.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.NormalUserEntity;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * 일반 로그인시 사용
     */

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        NormalUserEntity normalUser = userRepository.findByUserId(userId).orElseThrow(() ->
                new CustomException(ResponseCode.NOT_FOUND_USER));
        return UserPrincipal.create(normalUser);
    }

}
