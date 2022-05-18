package com.ridingmate.api.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "RMC_LOCATION")
@NoArgsConstructor
public class LocationEntity {

    // 지역코드
    @Id
    @Column(name = "location_code")
    private String locationCode;

    @Column(name = "name")
    private String name;

    // 상위코드
    @Column(name = "upper_location_code")
    private String upperLocationCode;

    // 도광역시 저장용 생성자
    public LocationEntity(String locationCode, String name) {
        this.locationCode = locationCode;
        this.name = name;
    }

    // 시군구 저장
    public LocationEntity(String locationCode, String upperLocationCode, String name) {
        this.locationCode = locationCode;
        this.upperLocationCode = upperLocationCode;
        this.name = name;
    }
}
