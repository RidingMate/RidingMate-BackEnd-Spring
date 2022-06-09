package com.ridingmate.api.service.common;

import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.entity.FileEntity;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.repository.FileRepository;
import com.ridingmate.api.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final AwsS3Service awsS3Service;
    private final FileRepository fileRepository;

    //파일 업로드
//    public FileEntity uploadFile(MultipartFile multipartFile, String test) throws Exception {
//        String originalFileName = multipartFile.getOriginalFilename();
//        String fileExt = FileUtil.getFileExt(originalFileName);
//        String storeFileName = "";
////        if(fileName == null){
////            storeFileName = FileUtil.getFileName(fileExt);
////        }else{
////            if(fileExt.equals("undefined") || fileExt.equals("null")){
////                storeFileName = fileName+".jpeg";
////            }else{
////                storeFileName = fileName+"."+fileExt;
////            }
////        }
//
////        String filePath = FileUtil.getFileFolder() + listUuid + "/";
////        String folderLocation = filePath.substring(0, filePath.length() - 1);
//        String folderLocation = "test";
//
//        //S3에 저장
////        String location = awsS3Service.upload(multipartFile, "test");
//        if(location == null) {
//            throw new CustomException(ResponseCode.DONT_SAVE_S3_FILE);
//        }
//
//
//        FileEntity fileEntity = new FileEntity().createEntity(
//                FileUtil.getFileCode(),
//                originalFileName,
//                storeFileName,
//                folderLocation,
//                location,
//                fileExt,
//                multipartFile.getSize()
//        );
//
//        fileRepository.save(fileEntity);
//
//
//        return fileEntity;
//    }

    //다중파일 업로드
//    public String uploadMultipleFile(List<MultipartFile> multipartFile) throws Exception {
//
//        String listUuid = UUID.randomUUID().toString();
//        // s3에 저장
//        for(MultipartFile m : multipartFile) {
//            uploadFile(m, listUuid,"");
//        }
//
//        return listUuid;
//    }

}
