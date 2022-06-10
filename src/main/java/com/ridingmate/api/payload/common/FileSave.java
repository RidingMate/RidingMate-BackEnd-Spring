package com.ridingmate.api.payload.common;

import com.ridingmate.api.entity.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileSave {
    private List<FileResult> fileResultList;
    private List<FileEntity> fileEntityList;

    public FileSave create(List<FileResult> fileResults, List<FileEntity> fileEntityList){
        this.fileResultList = fileResults;
        this.fileEntityList = fileEntityList;

        return this;
    }
}
