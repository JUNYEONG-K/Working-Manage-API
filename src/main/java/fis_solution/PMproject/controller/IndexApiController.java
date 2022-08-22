package fis_solution.PMproject.controller;

import fis_solution.PMproject.controller.dto.*;
import fis_solution.PMproject.repository.search.FindIndexCaseInfo;
import fis_solution.PMproject.repository.search.FindIndexDetailInfo;
import fis_solution.PMproject.repository.search.FindIndexPreinfo;
import fis_solution.PMproject.service.IndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class IndexApiController {
    private final IndexService indexService;

    // 해당 기관에 있는 대상 목록들을 가져오는 api (철 항목 입력 정보가 있으면 포함해서)
    @GetMapping("/index/search/{o_code}")
    public List<IndexSearchResponse> indexSearchResponses(@PathVariable String o_code,
                                                          @RequestParam(value = "box", required = false) String f_bnum,
                                                          @RequestParam(value = "label", required = false) String f_labelcode) {
        FindIndexPreinfo findIndexPreinfo = new FindIndexPreinfo();
        findIndexPreinfo.setB_num(f_bnum);
        findIndexPreinfo.setF_labelcode(f_labelcode);
        findIndexPreinfo.setO_code(o_code);

        return indexService.searchFilesByPreInfo(findIndexPreinfo);
    }

    // 해당 철의 권 정보들과 권에 속한 건 정보들을 반환하는 api
    @GetMapping("/index/volumes")
    public List<VolumesInfo> getVolumesInfo(@RequestParam Long f_id){
        return indexService.getVolumesInfo(f_id);
    }

    // 철 항목 입력
    @PostMapping("/index/label")
    public IndexSaveLabelResponse indexSaveLabelResponse(@RequestBody IndexSaveLabelRequest indexSaveLabelRequest) {
        return indexService.saveFilesAndVolume(indexSaveLabelRequest);
    }

    // 건수 입력
    @PostMapping("/index/volume")
    public IndexSaveVolumeResponse indexSaveVolumeResponse(@RequestBody IndexSaveVolumeRequest indexSaveVolumeRequest) {
        return indexService.saveCasesPages(indexSaveVolumeRequest);
    }

    // 건 항목 입력
    @PatchMapping("/index/save")
    public IndexSaveCaseResponse indexSaveCaseRequest(@RequestBody IndexSaveCaseRequest indexSaveCaseRequest) {
        return indexService.saveCases(indexSaveCaseRequest);
    }

    // 철 삭제
    @DeleteMapping("/index/label/{f_id}")
    public IndexLabelDelResponse indexLabelDelResponse(@PathVariable Long f_id) {
        return indexService.deleteFiles(f_id);
    }

    // 철 항목 검색 api
    @GetMapping("/index/label")
    public List<IndexSearchLabelResponse> indexSearchLabelResponses(@RequestParam(value = "f_name", required = false) String f_name,
                                                                    @RequestParam(value = "syear", required = false) String syear,
                                                                    @RequestParam(value = "eyear", required = false) String eyear) {
        FindIndexDetailInfo findIndexDetailInfo = new FindIndexDetailInfo();
        findIndexDetailInfo.setF_name(f_name);
        findIndexDetailInfo.setF_pyear(syear);
        findIndexDetailInfo.setF_eyear(eyear);

        return indexService.searchFilesByDetailInfo(findIndexDetailInfo);
    }

    // 건 항목 검색 api
    @GetMapping("/index/case")
    public List<IndexSearchCaseResponse> indexSearchCaseResponse(@RequestParam(value = "docnum", required = false) String c_oldnum,
                                                                 @RequestParam(value = "c_name", required = false) String c_title,
                                                                 @RequestParam(value = "c_receiver", required = false) String c_receiver) {
        FindIndexCaseInfo findIndexCaseInfo = new FindIndexCaseInfo();
        findIndexCaseInfo.setC_oldnum(c_oldnum);
        findIndexCaseInfo.setC_title(c_title);
        findIndexCaseInfo.setC_receiver(c_receiver);
        return indexService.searchCasesByCasesInfo(findIndexCaseInfo);
    }

}
