package com.ridingmate.api.repository.fuel;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ridingmate.api.entity.FuelEntity;
import com.ridingmate.api.entity.QBikeEntity;
import com.ridingmate.api.entity.QFuelEntity;
import com.ridingmate.api.entity.UserEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.ridingmate.api.entity.QBikeEntity.bikeEntity;
import static com.ridingmate.api.entity.QFuelEntity.fuelEntity;

@RequiredArgsConstructor
public class FuelRepositoryImpl implements FuelCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<FuelEntity> fuelList(long bikeIdx, UserEntity user) {
        return queryFactory
                .select(fuelEntity).distinct()
                .from(fuelEntity)
                .join(fuelEntity.bike(), bikeEntity).fetchJoin()
                .where(fuelEntity.bike().idx.eq(bikeIdx), bikeEntity.user().eq(user), fuelEntity.resetNum.eq(0))
                .orderBy(fuelEntity.createAt.asc())
                .fetch();
    }

    @Override
    public long fuelReset(long bikeIdx, UserEntity user) {
        return queryFactory
                .update(fuelEntity)
                .set(fuelEntity.resetNum, 1)
                .where(fuelEntity.bike().idx.eq(bikeIdx))
                .execute();
    }
}
