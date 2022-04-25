package com.ridingmate.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RMC_USER")
@DynamicInsert
@DynamicUpdate
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseTime {

    //TODO : OAuth2 사용해서 어떤거 저장되는지 확인 해야함

    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    //고유번호
    @Column
    @NotNull
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

    //권한
    @Column(name = "role")
    @JsonIgnore
    @NotNull
    private String role;

    //TODO : 하나의 유저는 여러개의 바이크를 가질 수 있다.
    //TODO : 하나의 유저는 여러개의 중고거래 글을 쓸 수 있다.
    //TODO : 하나의 유저는 여러개의 댓글을 달 수 있다.

}
