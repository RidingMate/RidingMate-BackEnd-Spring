package com.ridingmate.api.service.common;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.FileEntity;
import com.ridingmate.api.entity.UserEntity;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.common.FileResult;
import com.ridingmate.api.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final AwsS3Service awsS3Service;
    private final FileRepository fileRepository;

    //파일 업로드
    @Transactional
    public FileEntity uploadFile(MultipartFile multipartFile, UserEntity user) throws Exception {
        FileResult fileResult = awsS3Service.putS3File(multipartFile, user.getUserUuid());
        FileEntity fileEntity = new FileEntity().createEntity(fileResult);
        fileRepository.save(fileEntity);

        return fileEntity;
    }

    //다중파일 업로드
    @Transactional
    public List<FileEntity> uploadMultipleFile(List<MultipartFile> multipartFile, UserEntity user) throws Exception {
        List<FileResult> fileResults = awsS3Service.putS3FileList(multipartFile, user.getUserUuid());
        List<FileEntity> fileEntityList = new ArrayList<>();
        fileResults.forEach(data->{
            FileEntity fileEntity = new FileEntity().createEntity(data);
            fileRepository.save(fileEntity);
            fileEntityList.add(fileEntity);
        });


        return fileEntityList;
    }

    @Transactional
    public void deleteFileEntity(FileEntity fileEntity){
        awsS3Service.deleteS3File(fileEntity.getOriginalName());
        fileRepository.delete(fileEntity);
    }

    @Transactional
    public void deleteMultipleFileEntity(List<FileEntity> files){
        files.forEach(file->{
            deleteFileEntity(file);
        });
    }

    @Transactional
    public void deleteFile(String fileName){
        FileEntity fileEntity = fileRepository.findByOriginalName(fileName).orElseThrow(()->
                new CustomException(ResponseCode.NOT_FOUND_FILE)
        );
        awsS3Service.deleteS3File(fileName);
        fileRepository.delete(fileEntity);
    }

    @Transactional
    public void deleteMultipleFile(List<FileEntity> files){
        files.forEach(file->{
            deleteFile(file.getOriginalName());
        });
    }

}
