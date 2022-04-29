package com.ridingmate.api.security;

import com.ridingmate.api.entity.NormalUserEntity;
import com.ridingmate.api.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
public class UserPrincipal implements UserDetails {

    private long idx;
    private String phoneNumber;
    private String password;
    private String username;
    private String uuid;
    private String authority;
    private boolean enabled;

    public UserPrincipal(long idx, String phoneNumber, String password, String uuid, String authority) {
        this.idx = idx;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.uuid = uuid;
        this.authority = authority;
    }

    public UserPrincipal(long idx, String phoneNumber, String password, String authority) {
        this.idx = idx;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.authority = authority;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new SimpleGrantedAuthority(authority));
        return auth;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserPrincipal create(NormalUserEntity user) {
        return new UserPrincipal(
                user.getIdx(),
                user.getPhoneNumber(),
                user.getPassword(),
                user.getUuid(),
                user.getRole().toString()
        );
    }
}
