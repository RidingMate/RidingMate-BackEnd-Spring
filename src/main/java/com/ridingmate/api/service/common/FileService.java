package com.ridingmate.api.service.common;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.FileEntity;
import com.ridingmate.api.entity.UserEntity;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.common.FileResult;
import com.ridingmate.api.payload.common.FileSave;
import com.ridingmate.api.repository.FileRepository;
import com.ridingmate.api.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final AwsS3Service awsS3Service;
    private final FileRepository fileRepository;

    //파일 업로드
    @Transactional
    public FileResult uploadFile(MultipartFile multipartFile, UserEntity user) throws Exception {
        FileResult fileResult = awsS3Service.putS3File(multipartFile, user.getUserUuid());
        FileEntity fileEntity = new FileEntity().createEntity(fileResult);
        fileRepository.save(fileEntity);

        return fileResult;
    }

    //다중파일 업로드
    @Transactional
    public FileSave uploadMultipleFile(List<MultipartFile> multipartFile, UserEntity user) throws Exception {
        FileSave fileSave = new FileSave();
        List<FileResult> fileResults = awsS3Service.putS3FileList(multipartFile, user.getUserUuid());
        List<FileEntity> fileEntityList = new ArrayList<>();
        fileResults.forEach(data->{
            FileEntity fileEntity = new FileEntity().createEntity(data);
            fileRepository.save(fileEntity);
            fileEntityList.add(fileEntity);
        });


        return fileSave.create(fileResults, fileEntityList);
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
            awsS3Service.deleteS3File(file.getOriginalName());
        });
    }

}
