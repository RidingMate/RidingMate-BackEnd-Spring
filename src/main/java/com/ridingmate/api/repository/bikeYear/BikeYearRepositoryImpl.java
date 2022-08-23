package com.ridingmate.api.repository.bikeYear;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ridingmate.api.entity.BikeYearEntity;
import com.ridingmate.api.entity.QBikeYearEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.ridingmate.api.entity.QBikeYearEntity.bikeYearEntity;

@RequiredArgsConstructor
public class BikeYearRepositoryImpl implements BikeYearCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BikeYearEntity> searchYear(String company, String model) {
        return queryFactory
                .select(bikeYearEntity)
                .from(bikeYearEntity)
                .where(
                        bikeYearEntity.bikeModel().model.eq(model),
                        bikeYearEntity.bikeModel().bikeCompany().company.eq(company)
                )
                .orderBy(bikeYearEntity.year.asc())
                .fetch();
    }
}
