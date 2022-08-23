package com.ridingmate.api.repository.bikeModel;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ridingmate.api.entity.BikeCompanyEntity;
import com.ridingmate.api.entity.BikeModelEntity;
import com.ridingmate.api.entity.QBikeModelEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.ridingmate.api.entity.QBikeModelEntity.*;

@RequiredArgsConstructor
public class BikeModelRepositoryImpl implements BikeModelCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BikeModelEntity> searchModel(String company) {
        return queryFactory
                .select(bikeModelEntity)
                .from(bikeModelEntity)
                .where(bikeModelEntity.bikeCompany().company.eq(company))
                .orderBy(bikeModelEntity.model.asc())
                .fetch();
    }
}
