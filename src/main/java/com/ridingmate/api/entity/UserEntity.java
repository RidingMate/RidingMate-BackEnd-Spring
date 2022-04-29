package com.ridingmate.api.entity;

import com.ridingmate.api.entity.value.UserRole;
import lombok.Getter;

import javax.persistence.*;

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

    //TODO : 하나의 유저는 여러개의 바이크를 가질 수 있다.
    //TODO : 하나의 유저는 여러개의 중고거래 글을 쓸 수 있다.
    //TODO : 하나의 유저는 여러개의 댓글을 달 수 있다.

}
