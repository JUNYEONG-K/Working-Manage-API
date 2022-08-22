package fis_solution.PMproject.controller.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class SaveImageRequest {
    private Long fileId;
    private List<MultipartFile> images;
}
