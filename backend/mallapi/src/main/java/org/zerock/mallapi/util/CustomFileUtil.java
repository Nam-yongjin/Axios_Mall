package org.zerock.mallapi.util;

import java.io.File;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;

import lombok.extern.log4j.Log4j2;

import net.coobird.thumbnailator.Thumbnails;

// 파일 데이터의 입출력
// 프로그램이 시작되면 upload라는 이름의 폴더를 체크해서 자동으로 생성하도록 
// @PostConstruct를 이용하고, 파일 업로드는 saveFiles()로 작성
@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {

    @Value("${org.zerock.upload.path}")
    private String uploadPath;

    @PostConstruct
    public void init() {
        File tempFolder = new File(uploadPath);
        if (tempFolder.exists() == false) {
            tempFolder.mkdir();
        }
        uploadPath = tempFolder.getAbsolutePath();
        log.info("------------------------------");
        log.info(uploadPath);
    }

    public List<String> saveFiles(List<MultipartFile> files) throws RuntimeException {
        if (files == null || files.size() == 0) {
            return null;
        }
        List<String> uploadNames = new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            String savedName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();

            Path savePath = Paths.get(uploadPath, savedName);
            try {
                Files.copy(multipartFile.getInputStream(), savePath);

                String contentType = multipartFile.getContentType();

                // 이미지 여부 확인
                if (contentType != null && contentType.startsWith("image")) {
                    // 썸네일
                    Path thumbnailPath = Paths.get(uploadPath, "s_" + savedName);

                    Thumbnails.of(savePath.toFile())
                            .size(200, 200)
                            .toFile(thumbnailPath.toFile());
                }

                uploadNames.add(savedName);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return uploadNames;
    }

    // 업로드된 파일은 GET방식으로 호출해서 브라우저에서 볼 수 있어야 한다.
    // ‘/api/products/view/파일이름’ 경로에서 파일을 볼 수 있게 구성한다.
    // UUID값이 적용된 파일명이 길기에 간단히 테스트하기 위해서 default.jpeg 이미지 파일 하나를 upload 폴더에 추가한다.
    public ResponseEntity<Resource> getFile(String fileName) {

        Resource resource = new FileSystemResource(uploadPath+ File.separator + fileName);

        if(!resource.exists()) {
            resource = new FileSystemResource(uploadPath+ File.separator + "default.jpeg");
        }

        HttpHeaders headers = new HttpHeaders();

        try{
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    // 첨부파일은 수정이라는 개념이 존재하지 않고, 기존 파일을 삭제하고 
    // 새로운 파일로 대체하는 개념이기 때문에 삭제하는 기능이 필요하다. 
    // 삭제 기능은 파일명 기준으로 한 번에 여러 개의 파일을 삭제하는 기능으로 구현
    public void deleteFiles(List<String> fileNames){
        if(fileNames == null || fileNames.size() == 0){
            return;
        }
        fileNames.forEach(fileName -> {
            // 썸네일이 있는지 확인하고 삭제
            String thumbnailFileName = "s_"+fileName;
            Path thumbnailPath = Paths.get(uploadPath, thumbnailFileName);
            Path filePath = Paths.get(uploadPath, fileName);
            try{
                Files.deleteIfExists(filePath);
                Files.deleteIfExists(thumbnailPath);
            }catch(IOException e){
                throw new RuntimeException(e.getMessage());
            }
        });
    }
}
