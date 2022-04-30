package com.ridingmate.api.service.common;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.UserEntity;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.repository.UserRepository;
import com.ridingmate.api.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity getUserEntityByUuid(String userUuid) {
        return userRepository.findByUserUuid(userUuid).orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_USER));
    }

    public UserEntity getUserEntityByAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        UserEntity userEntity = userRepository.findByUserUuid(principal.getUuid()).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_USER)
        );
        return userEntity;
    }

}
