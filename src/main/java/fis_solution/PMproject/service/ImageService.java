package fis_solution.PMproject.service;

import fis_solution.PMproject.controller.dto.ImagesMaxnumResponse;
import fis_solution.PMproject.controller.dto.SaveImageRequest;
import fis_solution.PMproject.domain.record.Files;
import fis_solution.PMproject.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final FileRepository fileRepository;

    @Value("${image.path.origin}")
    private String originPath;

    @Value("${image.path.modify}")
    private String modifyPath;

    public String getFullPath(Long fileId, String state) {
        if (state.equals("origin")) {
            return originPath + fileId + '/';
        }
        return modifyPath + fileId + '/';
    }

    @Transactional
    public Long storeImages(SaveImageRequest request, String state) throws IOException {

        // 등록된 철인지 검사
        Files findFile = fileRepository.findOne(request.getFileId());
        if (findFile == null)
            return null;

        // 철의 이미지들이 저장될 디렉토리 생성(overwrite)
        String path = getFullPath(request.getFileId(), state);
        mkdir(path);

        // request에 담겨있는 이미지들을 생성한 디렉토리에 저장
        long imageCnt = 0;
        for (MultipartFile multipartFile : request.getImages()) {
            if (!multipartFile.isEmpty()) {
                // 이미지 저장
                multipartFile.transferTo(new File(path + (++imageCnt)));
            }
        }
        findFile.imageUpload(imageCnt, state);

        // 철의 이미지 갯수 반환
        return imageCnt;
    }

    private void mkdir(String path) {
        File directory = new File(path);
        while (directory.exists()) {
            File[] subFiles = directory.listFiles();
            for (int i = 0; i < subFiles.length; i++) {
                subFiles[i].delete();
            }
            System.out.println(directory.length());
            if (subFiles.length == 0) {
                directory.delete();
            }
        }
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public ImagesMaxnumResponse imagesMaxnum() {
        List<Files> filesList = fileRepository.findAllWithImages();
        List<ImagesMaxnumResponse.ImagesNum> collect = filesList.stream()
                .map(files -> new ImagesMaxnumResponse.ImagesNum(String.valueOf(files.getF_id()), files.getImages()))
                .collect(Collectors.toList());
        ImagesMaxnumResponse imagesMaxnumResponse = new ImagesMaxnumResponse(collect);
        return imagesMaxnumResponse;
    }
}
