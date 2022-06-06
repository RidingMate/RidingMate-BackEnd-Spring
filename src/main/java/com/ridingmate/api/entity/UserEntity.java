package com.ridingmate.api.entity;

import com.ridingmate.api.entity.value.UserRole;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "RMC_USER")
@DiscriminatorColumn(name = "user_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class UserEntity extends BaseTime {

    /**
     * 유저 공통 엔티티
     */

    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "user_uuid")
    private String userUuid;

    @Column(name = "nickname")
    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<BikeEntity> bike = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<TradeBoardEntity> tradePosts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CommentEntity> comments = new ArrayList<>();

    //TODO : 이용약관 체크 있어야할듯


    // 비즈니스 로직
    public void createUserEntity(String nickname, UserRole role) {
        this.userUuid = UUID.randomUUID().toString();
        this.nickname = nickname;
        this.role = role;
    }
}
