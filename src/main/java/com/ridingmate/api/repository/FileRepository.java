package com.ridingmate.api.repository;

import com.ridingmate.api.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
