package com.ridingmate.api.service.common;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.ridingmate.api.consts.ResponseCode;
import com.ridingmate.api.exception.CustomException;
import com.ridingmate.api.payload.common.FileResult;
import com.ridingmate.api.util.FileNameGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final AmazonS3 amazonS3;

    private final FileNameGenerator fileNameGenerator;

    @Value("${cloud.aws.cloud_front.file_url_format}")
    private String fileUrlFormat;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public FileResult putS3File(MultipartFile file, String userName){
        String fileName = fileNameGenerator.generateFileName(file, userName);
        ObjectMetadata objectMetadata = generateObjectMetaData(file);

        try {
            amazonS3.putObject(bucket, fileName, file.getInputStream(), objectMetadata);
            return new FileResult(fileName, String.format(fileUrlFormat, fileName));
        } catch (IOException e) {
            throw new CustomException(ResponseCode.DONT_SAVE_S3_FILE);
        }
    }

    public List<FileResult> putS3FileList(List<MultipartFile> multipartFiles, String userName){
        return multipartFiles.stream()
                .map(file -> putS3File(file, userName))
                .collect(Collectors.toList());
    }

    private ObjectMetadata generateObjectMetaData(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        return objectMetadata;
    }



    public void deleteS3File(String filePath) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, filePath);
        amazonS3.deleteObject(deleteObjectRequest);
    }



    //아래부터 다시 수정해야함

//    public InputStream getS3File(String filePath) {
//        S3Object object = null;
//        try {
//            object = amazonS3.getObject(new GetObjectRequest(bucket, filePath));
//        } catch (AmazonS3Exception e) {
//            log.error("s3 파일 키가 존재하지 않습니다. key = {}", filePath);
//        }
//        return object == null ? null : object.getObjectContent();
//    }
//
//    public List<InputStream> getS3InputStreamFileList(String fileListPath) {
//        ListObjectsV2Result result = amazonS3.listObjectsV2(new ListObjectsV2Request().withBucketName(bucket).withPrefix(fileListPath));
//        List<InputStream> list = new ArrayList<>();
//        result.getObjectSummaries().forEach(data -> {
//            list.add(getS3File(data.getKey()));
//        });
//        return list;
//    }
//
//    public List<S3ObjectSummary> getS3ObjectFileList(String fileListPath) {
//        ListObjectsV2Result result = amazonS3.listObjectsV2(new ListObjectsV2Request().withBucketName(bucket).withPrefix(fileListPath));
//        return result.getObjectSummaries();
//    }
}
