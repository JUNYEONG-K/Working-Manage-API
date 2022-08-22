package fis_solution.PMproject.service;

import fis_solution.PMproject.controller.dto.PreinfoFileDelResponse;
import fis_solution.PMproject.controller.dto.PreinfoFileSaveRequest;
import fis_solution.PMproject.controller.dto.PreinfoFileSaveResponse;
import fis_solution.PMproject.controller.dto.PreinfoFileSearchResponse;
import fis_solution.PMproject.domain.Office;
import fis_solution.PMproject.domain.record.*;

import fis_solution.PMproject.repository.FileRepository;
import fis_solution.PMproject.repository.OfficeRepository;
import fis_solution.PMproject.repository.search.FindPreinfoBySearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 * 작성자: 원보라
 * 작성날짜: 2021/08/24
 * 작성내용: PreInfoService
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PreInfoService {

    private final FileRepository fileRepository;
    private final OfficeRepository officeRepository;

    /**
     * 철 정보 저장 & 수정
     **/
    @Transactional
    public PreinfoFileSaveResponse savePreinfo(PreinfoFileSaveRequest preinfoFileSaveRequest) {
        //처음 저장
        //office 생성
        Office office = officeRepository.findOne(preinfoFileSaveRequest.getO_code());
        validateDuplicateLabel(preinfoFileSaveRequest); //레이블 중복검사
        Files files = Files.createFiles(preinfoFileSaveRequest, office); // 사전정보 저장
        fileRepository.save(files);
        PreinfoFileSaveResponse preinfoFileSaveResponse = new PreinfoFileSaveResponse();
        preinfoFileSaveResponse.setF_id(files.getF_id());
//        preinfoFileSaveResponse.setErr_code();
        return preinfoFileSaveResponse;
    }

    //처음 저장된 레이블이면 f_labelcode 를 중복검사
    private void validateDuplicateLabel(PreinfoFileSaveRequest preinfoFileSaveRequest) {
        List<Files> findFile = fileRepository.findByLabel(preinfoFileSaveRequest.getF_labelcode());
        if (!findFile.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 레이블입니다.");
        }
    }

    //0827원보라 updatePreinfo 수정
    @Transactional
    public PreinfoFileSaveResponse updatePreinfo(PreinfoFileSaveRequest preinfoFileSaveRequest) {
        //수정한 정보 저장
        Files findFile = fileRepository.findOne(preinfoFileSaveRequest.getF_id());
        Office findOffice = officeRepository.findOne(preinfoFileSaveRequest.getO_code());
        findFile.updateFileinfo(findOffice, preinfoFileSaveRequest.getF_labelcode(), preinfoFileSaveRequest.getF_name(), preinfoFileSaveRequest.getF_pyear(), preinfoFileSaveRequest.getF_kperiod(), preinfoFileSaveRequest.getF_db(), preinfoFileSaveRequest.getF_scan(), preinfoFileSaveRequest.getB_num(), preinfoFileSaveRequest.getF_location(), preinfoFileSaveRequest.getF_kplace(), preinfoFileSaveRequest.getF_type(), preinfoFileSaveRequest.getF_typenum());
        PreinfoFileSaveResponse preinfoFileSaveResponse = new PreinfoFileSaveResponse();
        preinfoFileSaveResponse.setF_id(findFile.getF_id());
        return preinfoFileSaveResponse;
    }


    /**
     * 철 정보 삭제
     **/
    @Transactional
    public void removePreinfo(Long f_id) throws NoSuchElementException {
        Files files = Optional.ofNullable(fileRepository.findOne(f_id)).orElseThrow(()->{
            return new NoSuchElementException(String.valueOf(f_id));
        });
        fileRepository.remove(files);
    }


    /**
     * 철 정보 검색
     **/
    //기관코드, 레이블, 철제목, 생산년도 으로 검색
    public List<PreinfoFileSearchResponse> searchFileByPreinfo(FindPreinfoBySearch findPreinfoBySearch) {
        List<PreinfoFileSearchResponse> dataList = new ArrayList<>();
         fileRepository.findByOcodeLabelFnamePyear(
                findPreinfoBySearch.getOffice(),
                findPreinfoBySearch.getF_labelcode(),
                findPreinfoBySearch.getF_name(),
                findPreinfoBySearch.getF_pyear())
                 .stream()
                 .forEach(files -> {
                     try {
                         PreinfoFileSearchResponse preinfoFileSearchResponse = new PreinfoFileSearchResponse(files.getF_id(), files.getOffice().getO_code(), files.getF_labelcode(), files.getOffice().getO_name(), files.getF_name(), files.getF_pyear(), files.getF_kperiod(), files.getF_db(), files.getF_scan() , files.getB_num(), files.getF_location(), files.getF_kplace(), files.getF_type(), files.getF_typenum());
                         dataList.add(preinfoFileSearchResponse);
                     } catch (Exception exception){
                         PreinfoFileSearchResponse preinfoFileSearchResponse = new PreinfoFileSearchResponse(files.getF_id(), files.getF_labelcode(), files.getF_name(), files.getF_pyear(), files.getF_kperiod(), files.getF_db(), files.getF_scan() , files.getB_num(), files.getF_location(), files.getF_kplace(), files.getF_type(), files.getF_typenum());
                         dataList.add(preinfoFileSearchResponse);
                     }
                 });
         return dataList;
//                 .map(files -> new PreinfoFileSearchResponse(files.getF_id(), files.getOffice().getO_code(), files.getF_labelcode(), files.getOffice().getO_name(), files.getF_name(), files.getF_pyear(), files.getF_kperiod(), files.getF_db(), files.getF_scan() , files.getB_num(), files.getF_location(), files.getF_kplace(), files.getF_type(), files.getF_typenum()))
//                 .collect(Collectors.toList());
    }

    public List<Files> findAll(){
        return fileRepository.findAll();
    }

    public void reset(){
        fileRepository.resetAll();
    }


    /**
     * 폴더 생성
     **/
    public static void mkdir(Long f_labelcode, String b_num, String o_code) {
        String path1 = System.getProperty("user.home") + File.separator + o_code; //폴더 경로
        String path2 = path1 + File.separator + b_num;
        String path3 = path2 + File.separator + f_labelcode;
        File Folder_o_code = new File(path1);
        File Folder_b_num = new File(path2);
        File Folder_f_labelcode = new File(path3);

        int finish = 0;
        // 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
        while (finish == 0) {
            if (!Folder_o_code.exists()) {  //기관코드 폴더가 없다면
                try {
                    Folder_o_code.mkdir(); //기관코드 폴더 생성
                } catch (Exception e) {
                    e.getStackTrace();
                }
            } else {    //기관코드 폴더가 이미 있다면
                if (!Folder_b_num.exists()) {   //박스 폴더가 없다면
                    try {
                        Folder_b_num.mkdir(); //박스번호 폴더 생성
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                } else {    //박스 폴더가 이미 있다면
                    if (!Folder_f_labelcode.exists()) { //레이블 폴더가 없다면
                        try {
                            Folder_f_labelcode.mkdir(); //레이블 번호 폴더 생성
                            System.out.println("폴더 생성되었습니다.");
                            System.out.println("Folder = " + Folder_o_code.getAbsolutePath());
                            finish = 1;
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                    } else {    //레이블 폴더가 있으면 끝남
                        System.out.println("이미 폴더가 생성되어 있습니다.");
                        finish = 1;
                    }
                }
            }
        }
    }
}

