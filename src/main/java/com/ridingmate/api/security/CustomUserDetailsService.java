package com.ridingmate.api.security;

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

//        // 유저계정
//        if(userRepository.existsByPhoneNumber(phoneNumber)) {
//            UserEntity user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() ->
//                    new UsernameNotFoundException("This phoneNumber is not exist : " + phoneNumber)
//            );
//            return UserPrincipal.create(user);
//        }
//
//        // 관리자계정
//        if(adminUserRepository.existsByAdminId(phoneNumber)) {
//            AdminUserEntity admin = adminUserRepository.findByAdminId(phoneNumber).orElseThrow(() ->
//                new UsernameNotFoundException("This ID is not exist in Admin Table : " + phoneNumber)
//            );
//            return UserPrincipal.adminCreate(admin);
//        }
//
//        throw new UsernameNotFoundException("This ID is Not exist in User, Admin Table : " + phoneNumber);

        return null;
    }

}
