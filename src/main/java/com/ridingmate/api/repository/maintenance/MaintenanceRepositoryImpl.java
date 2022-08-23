package com.ridingmate.api.repository.maintenance;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ridingmate.api.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.ridingmate.api.entity.QBikeEntity.bikeEntity;
import static com.ridingmate.api.entity.QFileEntity.fileEntity;
import static com.ridingmate.api.entity.QMaintenanceEntity.maintenanceEntity;

@RequiredArgsConstructor
public class MaintenanceRepositoryImpl implements MaintenanceCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MaintenanceEntity> list(Long bikeIdx, UserEntity user, LocalDate start, LocalDate end) {
        return queryFactory
                .select(maintenanceEntity)
                .distinct()
                .from(maintenanceEntity)
                .leftJoin(maintenanceEntity.fileEntities, fileEntity).fetchJoin()
                .join(maintenanceEntity.bike(), bikeEntity).fetchJoin()
                .where(maintenanceEntity.bike().idx.eq(bikeIdx), maintenanceEntity.dateOfMaintenance.between(start, end))
                .fetch();
    }

    @Override
    public Optional<MaintenanceEntity> maintenanceDetail(Long bikeIdx, Long maintenanceIdx, UserEntity user) {
        MaintenanceEntity maintenanceEntity = queryFactory
                .select(QMaintenanceEntity.maintenanceEntity)
                .from(QMaintenanceEntity.maintenanceEntity)
                .leftJoin(QMaintenanceEntity.maintenanceEntity.fileEntities, fileEntity).fetchJoin()
                .where(
                        QMaintenanceEntity.maintenanceEntity.idx.eq(maintenanceIdx),
                        QMaintenanceEntity.maintenanceEntity.bike().idx.eq(bikeIdx),
                        QMaintenanceEntity.maintenanceEntity.bike().user().eq(user)
                )
                .fetchOne();
        return Optional.ofNullable(maintenanceEntity);
    }

}
