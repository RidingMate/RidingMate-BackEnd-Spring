package com.ridingmate.api.repository.bike;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ridingmate.api.entity.*;
import com.ridingmate.api.entity.value.BikeRole;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.ridingmate.api.entity.QBikeEntity.bikeEntity;
import static com.ridingmate.api.entity.QFileEntity.fileEntity;
import static com.ridingmate.api.entity.QUserEntity.userEntity;

@RequiredArgsConstructor
public class BikeRepositoryImpl implements BikeCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BikeEntity> myBikeList(UserEntity user) {
        return queryFactory
                .select(bikeEntity)
                .from(bikeEntity)
                .leftJoin(bikeEntity.fileEntity(), fileEntity).fetchJoin()
                .where(bikeEntity.user().eq(user))
                .orderBy(bikeEntity.bikeRole.desc())
                .fetch();
    }

    @Override
    public Optional<BikeEntity> myBikeDetail(long bikeIdx, UserEntity user) {
        BikeEntity bikeEntity = queryFactory
                .select(QBikeEntity.bikeEntity)
                .from(QBikeEntity.bikeEntity)
                .leftJoin(QBikeEntity.bikeEntity.fileEntity(), fileEntity).fetchJoin()
                .where(QBikeEntity.bikeEntity.idx.eq(bikeIdx), QBikeEntity.bikeEntity.user().eq(user))
                .fetchOne();

        return Optional.ofNullable(bikeEntity);
    }

}
