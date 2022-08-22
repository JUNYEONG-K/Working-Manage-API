package fis_solution.PMproject.service;

import fis_solution.PMproject.controller.dto.UploadCreateFileRequest;
import fis_solution.PMproject.controller.dto.UploadSearchBoxRequest;
import fis_solution.PMproject.controller.dto.UploadSearchBoxResponse;
import fis_solution.PMproject.domain.record.Cases;
import fis_solution.PMproject.domain.record.Files;
import fis_solution.PMproject.repository.FileRepository;
import fis_solution.PMproject.service.pdfService.PDFconverter;
import fis_solution.PMproject.service.pdfService.TxtfileMaker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UploadService {

    private final FileRepository fileRepository;

    // 기관코드 + 미검수 포함 박스 검색 + 박스범위 지정 안할 경우
    public List<UploadSearchBoxResponse> UploadFileSearch(UploadSearchBoxRequest request){
        List<Files> search_results = fileRepository.findUploadSearchOnly(request);
        List<UploadSearchBoxResponse> response = new ArrayList<>();

        for(Files file : search_results){
            response.add(UploadSearchBoxResponse.CreateUploadSearchBoxResponse(file));
        }
        return response;
    }

    // 파일 생성 서비스 pdf 생성 + txt파일 생성
    public void createUploadFileService(UploadCreateFileRequest request) throws IOException {
        PDFconverter pdFconverter = new PDFconverter();
        TxtfileMaker txtfileMaker = new TxtfileMaker();
        for(Long f_id : request.getF_id()){
            System.out.println("=================" + f_id + "실행중=====================");
            Files label = fileRepository.findOne(f_id);
//            txtfileMaker.writeLabeltxt("label path", label);
            for(Cases _case : label.getCases()){
                // 이미지 폴더랑 데이터 폴더 나중에 따로 고려해서 추가해야함
                System.out.println("=================== 케이스 하나 실행 ===================\n");
                String path = pdFconverter.createuploaddir(label, _case);
                String pdf_path = pdFconverter.createPDFFile(System.getProperty("user.home") + "/test/image", "000001");
                pdFconverter.copyFile(path + ".pdf", pdf_path);
//                txtfileMaker.writecasetxt("case path", _case);
            }
            label.Uploaded();
        }
    }
}
