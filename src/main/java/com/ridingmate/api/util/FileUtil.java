package com.ridingmate.api.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class FileUtil {

    public static String getFileName(String fileExt) {
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")).toString()
                + RandomStringUtils.randomNumeric(6) + "." + fileExt;
        return fileName;
    }

    public static String getFileExt(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("잘못된 형식의 파일입니다. : " + fileName);
        }
    }

    public static String getFileFolder() {
        return new StringBuilder()
                .append("upload/")
                .toString();
    }

    public static String getFileCode() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_")).toString()
                + RandomStringUtils.randomNumeric(8) + "_"
                + UUID.randomUUID();
    }

    public static String getZipFolderName() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")).toString() + File.separator;
    }

    public static String getPdfFileName() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")).toString()+ UUID.randomUUID() + "_PDF.pdf";
    }

    public static String getFileCodeToUrl(String fileCode) {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String domain = req.getRequestURL().toString().replace(req.getRequestURI(), "") + "/v1/common/file/originalImg?fileCode=";
        return domain + fileCode;
    }

    public static String getTempFileCodeToUrl(String fileCode) {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String domain = req.getRequestURL().toString().replace(req.getRequestURI(), "") + "/v1/common/file/originalTempImg?fileCode=";
        return domain + fileCode;
    }
}
