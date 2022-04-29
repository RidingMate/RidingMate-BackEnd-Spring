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
@DiscriminatorValue("NORMAL")
public class NormalUserEntity extends UserEntity {

    /**
     * 일반 유저 엔티티
     * 아이디, 비밀번호로 로그인하는 유저
     */

    //아이디
    @Column(name = "user_id")
    @NotNull
    private String userId;

    //비밀번호
    @Column(name = "password")
    @JsonIgnore
    @NotNull
    private String password;

    //생일
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

}
