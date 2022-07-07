package com.ridingmate.api.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.ridingmate.api.entity.NormalUserEntity;
import com.ridingmate.api.entity.SocialUserEntity;
import com.ridingmate.api.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPrincipal implements UserDetails, OAuth2User {

    private static final long serialVersionUID = 6230446929653359457L;

    private String userId;
    private String password;
    private String username;
    private String uuid;
    private String authority;
    private boolean enabled;

    private UserEntity user;

    private Map<String, Object> attributes;

    public UserPrincipal(String userId, String password, String uuid, String authority, UserEntity user) {
        this.userId = userId;
        this.password = password;
        this.uuid = uuid;
        this.authority = authority;
        this.user = user;
    }

    public UserPrincipal(String userId, String password, String authority) {
        this.userId = userId;
        this.password = password;
        this.authority = authority;
    }

    public UserPrincipal(String oAuth2Code, String authority, UserEntity user, Map<String, Object> attributes) {
        userId = oAuth2Code;
        this.authority = authority;
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<>();
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
                user.getUserId(),
                user.getPassword(),
                user.getUserUuid(),
                user.getRole().toString(),
                user
        );
    }

    public static UserPrincipal create(SocialUserEntity user, Map<String, Object> attributes) {
        return new UserPrincipal(user.getOAuth2Code(), user.getRole().toString(), user, attributes);
    }

    @Override
    public String getName() {
        return userId;
    }
}
