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
@Table(name = "JD_USER")
@DynamicInsert
@DynamicUpdate
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseTime {

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

}
