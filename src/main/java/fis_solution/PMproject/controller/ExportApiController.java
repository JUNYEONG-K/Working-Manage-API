package fis_solution.PMproject.controller;

/*
 * 작성자: 원보라
 * 작성날짜: 2021/08/31
 * 작성내용: ExportApiController
 */

import fis_solution.PMproject.PMprojectApplication;
import fis_solution.PMproject.controller.dto.*;
import fis_solution.PMproject.repository.search.FindExportByBox;
import fis_solution.PMproject.repository.search.FindExportByDate;
import fis_solution.PMproject.repository.search.FindExportByLabel;
import fis_solution.PMproject.service.ExportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ExportApiController {
    private final ExportService exportService;

    //철 반출 등록 (날짜 등록)
    @PatchMapping("/export/save")
    public ExportSaveResponse saveResponse(@RequestBody @Valid ExportSaveRequest exportSaveRequest) {
        try{
            return exportService.updateExportInfo(exportSaveRequest);
        }
        catch(Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    //레이블 범위 검색
    @GetMapping("/export/search/label")
    public List<ExportSearchLabelResponse> labelResponses(@RequestParam(value = "slabel", required = false) String slabel,
                                                        @RequestParam(value = "elabel", required = false) String elabel){
        FindExportByLabel findExportByLabel = new FindExportByLabel();
        findExportByLabel.setFirst_label(slabel);
        findExportByLabel.setLast_label(elabel);

        return exportService.searchFileByLabelRange(findExportByLabel);
    }

    //반출된 레이블 중에 날짜로 범위 검색
    @GetMapping("/export/search/date")
    public List<ExportSearchDateResponse> dateResponses(@RequestParam(value = "sdate", required = false) String sdate,
                                                        @RequestParam(value = "edate", required = false) String edate) {
        FindExportByDate findExportByDate = new FindExportByDate();
        findExportByDate.setFirst_date(sdate);
        findExportByDate.setLast_date(edate);
        return exportService.searchFileByDateRange(findExportByDate);
    }

    //박스번호 범위 검색
    @GetMapping("/export/search/box")
    public List<ExportSearchBoxResponse> boxResponses(@RequestParam(value = "sbox", required = false) String sbox,
                                                      @RequestParam(value = "ebox", required = false) String ebox) {
        System.out.println("sbox = " + sbox);
        System.out.println("ebox = " + ebox);
        FindExportByBox findExportByBox = new FindExportByBox();
        findExportByBox.setFirst_box(sbox);
        findExportByBox.setLast_box(ebox);

        return exportService.searchFileByBoxRange(findExportByBox);
    }

}
