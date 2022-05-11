package com.ridingmate.api.service.common;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public InputStream getS3File(String filePath) {
        S3Object object = null;
        try {
            object = amazonS3.getObject(new GetObjectRequest(bucket, filePath));
        } catch (AmazonS3Exception e) {
            log.error("s3 파일 키가 존재하지 않습니다. key = {}", filePath);
        }
        return object == null ? null : object.getObjectContent();
    }

    public List<InputStream> getS3InputStreamFileList(String fileListPath) {
        ListObjectsV2Result result = amazonS3.listObjectsV2(new ListObjectsV2Request().withBucketName(bucket).withPrefix(fileListPath));
        List<InputStream> list = new ArrayList<>();
        result.getObjectSummaries().forEach(data -> {
            list.add(getS3File(data.getKey()));
        });
        return list;
    }

    public List<S3ObjectSummary> getS3ObjectFileList(String fileListPath) {
        ListObjectsV2Result result = amazonS3.listObjectsV2(new ListObjectsV2Request().withBucketName(bucket).withPrefix(fileListPath));
        return result.getObjectSummaries();
    }

    public String putS3File(String fileName, InputStream inputStream, String contentType) {
        if(inputStream != null) {
            try {
                ObjectMetadata meta = new ObjectMetadata();
                meta.setContentLength(inputStream.available());
                meta.setContentType(contentType);

                PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, inputStream, meta).withCannedAcl(CannedAccessControlList.PublicRead);
                putObjectRequest.getRequestClientOptions().setReadLimit(100000);

                PutObjectResult result = amazonS3.putObject(putObjectRequest);
                if(result != null) {
                    return fileName;
                }

            } catch (Exception e) {
                log.error("S3에 파일 등록 중 오류 발생 : {}", e.getMessage());
                return null;
            }
        }
        return null;
    }

    public void deleteS3File(String filePath) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, filePath);
        amazonS3.deleteObject(deleteObjectRequest);
    }
}
