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
    private String userId;
    private String password;
    private String username;
    private String uuid;
    private String authority;
    private boolean enabled;

    private UserEntity user;

    public UserPrincipal(long idx, String userId, String password, String uuid, String authority, UserEntity user) {
        this.idx = idx;
        this.userId = userId;
        this.password = password;
        this.uuid = uuid;
        this.authority = authority;
        this.user = user;
    }

    public UserPrincipal(long idx, String userId, String password, String authority) {
        this.idx = idx;
        this.userId = userId;
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
        return userId;
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
                user.getUserId(),
                user.getPassword(),
                user.getUserUuid(),
                user.getRole().toString(),
                user
        );
    }
}
