package com.ridingmate.api.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.ridingmate.api.entity.value.UserRole;

import lombok.Getter;

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

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    /**
     * 이용약관 체크여부
     */
    @Column(name = "is_check_terms", length = 1)
    private String isCheckTerms;

    /**
     * 이용약관 체크 날짜
     */
    @Column(name = "check_terms_time")
    @CreatedDate
    private LocalDate checkTermsTime;

    @OneToMany(mappedBy = "user")
    private List<BikeEntity> bike = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<TradeBoardEntity> tradePosts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<BookmarkEntity> bookmarks = new ArrayList<>();


    // 비즈니스 로직
    public void createUserEntity(String nickname, UserRole role) {
        userUuid = UUID.randomUUID().toString();
        this.nickname = nickname;
        this.role = role;
    }

    /**
     * 유저 기본값 지정
     */
    public void createDefaultInfo() {
        userUuid = UUID.randomUUID().toString();
        role = UserRole.ROLE_USER;
    }
}
