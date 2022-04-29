package com.ridingmate.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "RMC_USER")
@DynamicInsert
@DynamicUpdate
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NormalUserEntity extends UserEntity {

    /**
     * 일반 유저 엔티티
     * 아이디, 비밀번호로 로그인하는 유저
     */


    // TODO : 인증 로직 완성후 제거 -> UserEntity에서 공통으로 관리
    @Column(name = "uuid")
    private String uuid;

    //핸드폰 번호
    @Column(name = "phone_number")
    @NotNull
    private String phoneNumber;

    //생일
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    //비밀번호
    @Column(name = "password")
    @JsonIgnore
    @NotNull
    private String password;


}
