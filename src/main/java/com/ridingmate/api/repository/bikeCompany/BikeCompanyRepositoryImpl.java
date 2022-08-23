package com.ridingmate.api.repository.bikeCompany;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ridingmate.api.entity.BikeCompanyEntity;
import com.ridingmate.api.entity.QBikeCompanyEntity;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.ridingmate.api.entity.QBikeModelEntity.*;

@RequiredArgsConstructor
public class BikeCompanyRepositoryImpl implements BikeCompanyCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<BikeCompanyEntity> searchModel(String company) {
        BikeCompanyEntity bikeCompanyEntity = queryFactory
                .select(QBikeCompanyEntity.bikeCompanyEntity)
                .from(QBikeCompanyEntity.bikeCompanyEntity)
                .join(QBikeCompanyEntity.bikeCompanyEntity.bikeModelSet, bikeModelEntity).fetchJoin()
                .where(QBikeCompanyEntity.bikeCompanyEntity.company.eq(company))
                .fetchOne();
        return Optional.ofNullable(bikeCompanyEntity);
    }


}
