package fis_solution.PMproject.controller;

import fis_solution.PMproject.controller.dto.UploadCreateFileRequest;
import fis_solution.PMproject.controller.dto.UploadSearchBoxRequest;
import fis_solution.PMproject.controller.dto.UploadSearchBoxResponse;
import fis_solution.PMproject.domain.management.Worker;
import fis_solution.PMproject.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UploadController {
    private final UploadService uploadService;
    private final LoginController loginController;

    @PostMapping("/upload/search/box")
    public List<UploadSearchBoxResponse> searchFile(@RequestBody UploadSearchBoxRequest request, HttpServletRequest httprequest, HttpServletResponse response, Worker worker){
//        if(!loginController.check_login(httprequest, response, worker)){
//            System.out.println("==========================================fdsfsdfsdfsfdsdfdsfsdf===================");
//            return new ArrayList<>();
//        }
        System.out.println("request = " + request);
        return uploadService.UploadFileSearch(request);
    }

    @PostMapping("upload/create/file")
    public void createPDF(@RequestBody UploadCreateFileRequest request) throws IOException {
        System.out.println("request.toString() = " + request.toString());
        uploadService.createUploadFileService(request);
    }
}
