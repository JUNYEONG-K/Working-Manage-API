package fis_solution.PMproject.controller;


import fis_solution.PMproject.controller.dto.*;
import fis_solution.PMproject.controller.modelAttribute.PreInfoSearchDTO;
import fis_solution.PMproject.domain.Office;
import fis_solution.PMproject.domain.record.Files;
import fis_solution.PMproject.repository.search.FindPreinfoBySearch;
import fis_solution.PMproject.service.CommonService;
import fis_solution.PMproject.service.ErrorMessageBinder;
import fis_solution.PMproject.service.PreInfoService;
import fis_solution.PMproject.service.excelService.ExcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

/*
 * 작성자: 원보라
 * 작성날짜: 2021/08/30
 * 작성내용: PreInfoApiController
 */

@RestController
@RequiredArgsConstructor
@Slf4j
public class PreInfoApiController {

    private final PreInfoService preInfoService;
    private final CommonService commonService;
    private final ExcelService excelService;
    private final ErrorMessageBinder errorMessageBinder;

    //사전정보 저장 철기준
    @PostMapping("/preinfo/file")
    public PreinfoFileSaveResponse saveResponse(@RequestBody @Validated PreinfoFileSaveRequest preinfoFileSaveRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            PreinfoFileSaveResponse preinfoFileSaveResponse = new PreinfoFileSaveResponse();
            bindingResult.getAllErrors().forEach(objectError -> {
                String message = errorMessageBinder.bindMessage(objectError.getCodes(), null);
                preinfoFileSaveResponse.getErr_code().add(message);
            });
            return preinfoFileSaveResponse;
        }
        return preInfoService.savePreinfo(preinfoFileSaveRequest);
    }

    //사전정보 수정 철기준
    @PatchMapping("/preinfo/file")
    public PreinfoFileSaveResponse modifyResponse(@RequestBody PreinfoFileSaveRequest preinfoFileSaveRequest) {
         //해당되는 f_id 찾아서 update
        return preInfoService.updatePreinfo(preinfoFileSaveRequest);
    }

    //사전정보 철 삭제
    @DeleteMapping("/preinfo/file")
    public PreinfoFileDelResponse delResponse(@RequestBody PreInfoFileDelrequest preInfoFileDelrequest, BindingResult bindingResult) {
        Stream<Long> stream = Arrays.stream(preInfoFileDelrequest.getF_id());
        PreinfoFileDelResponse preinfoFileDelResponse = new PreinfoFileDelResponse();
        stream.forEach(f_id -> {
            try {
                preInfoService.removePreinfo(f_id);
                preinfoFileDelResponse.getF_id().add(f_id);
            } catch (NoSuchElementException noSuchElementException) {
                String exception_id = noSuchElementException.getMessage();
                Object[] args = {f_id};
                preinfoFileDelResponse.setErr_code(errorMessageBinder.bindMessage("delete.file.notexist", args));
            }
        });
        return preinfoFileDelResponse;
    }

    //사전정보 검색
    @GetMapping("/preinfo/file")
    public List<PreinfoFileSearchResponse> searchResponse(@ModelAttribute PreInfoSearchDTO preInfoSearchDTO,
                                                          BindingResult bindingResult) {
        FindPreinfoBySearch findPreinfoBySearch = new FindPreinfoBySearch();
        if (preInfoSearchDTO.getO_code() != null) {
            Office office = commonService.findOne(preInfoSearchDTO.getO_code());
            findPreinfoBySearch.setOffice(office);
        }
        findPreinfoBySearch.setF_labelcode(preInfoSearchDTO.getF_labelcode());
        findPreinfoBySearch.setF_name(preInfoSearchDTO.getF_name());
        findPreinfoBySearch.setF_pyear(preInfoSearchDTO.getF_pyear());

        return preInfoService.searchFileByPreinfo(findPreinfoBySearch);
    }

    @PostMapping("preinfo/excel")
    public Result<List> excelUpdate(HttpServletRequest request, HttpServletResponse response, @RequestParam("excelfile") MultipartFile file) throws IOException, NoSuchMethodException {

        List<ExcelUpdateDTO> dataList = new ArrayList<>();
        List<PreinfoFileSaveRequest> daoList = new ArrayList<>();
        try {
            preInfoService.reset();
            excelService.excelToJson(file, ExcelUpdateDTO.class).forEach(data -> {
                dataList.add((ExcelUpdateDTO) data);
                daoList.add(new PreinfoFileSaveRequest((ExcelUpdateDTO) data));
            });
            daoList.forEach(preinfoFileSaveRequest -> {
                try {
                    preInfoService.savePreinfo(preinfoFileSaveRequest);
                } catch (Exception e){

                }
            });
            return new Result<>(daoList);
        } catch (Exception exception){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            exception.printStackTrace();
            return null;
        }
    }

    @GetMapping("preinfo/excel")
    public void excelFile(HttpServletResponse response) throws IOException {
        List<Files> files = preInfoService.findAll();
        List<ExcelUpdateDTO> dataList = new ArrayList<>();
        files.stream().forEach(data -> {
            ExcelUpdateDTO excelUpdateDTO = new ExcelUpdateDTO(data);
            dataList.add(excelUpdateDTO);
        });

        // 컨텐츠 타입과 파일명 지정
        response.setContentType("ms-vnd/excel");
        //response.setHeader("Content-Disposition", "attachment;filename=example.xls");
        response.setHeader("Content-Disposition", "attachment;filename=preInfo.xlsx");

        // Excel File Output
        Workbook workbook = excelService.dbToExcel(dataList, ExcelUpdateDTO.class);
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
