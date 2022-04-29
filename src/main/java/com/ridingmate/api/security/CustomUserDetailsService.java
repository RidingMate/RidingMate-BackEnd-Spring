package com.ridingmate.api.security;

import com.ridingmate.api.entity.NormalUserEntity;
import com.ridingmate.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
                new NullPointerException("해당 유저를 찾지 못하였습니다."));

        return UserPrincipal.create(normalUser);
    }

}
