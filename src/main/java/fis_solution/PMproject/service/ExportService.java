package fis_solution.PMproject.service;

import fis_solution.PMproject.controller.dto.*;
import fis_solution.PMproject.domain.record.fileEnum.F_construct;
import fis_solution.PMproject.domain.record.Files;
import fis_solution.PMproject.repository.FileRepository;
import fis_solution.PMproject.repository.OfficeRepository;
import fis_solution.PMproject.repository.search.FindExportByBox;
import fis_solution.PMproject.repository.search.FindExportByDate;
import fis_solution.PMproject.repository.search.FindExportByLabel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * 작성자: 원보라
 * 작성날짜: 2021/08/24
 * 작성내용: ExportService
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ExportService {

    private final FileRepository fileRepository;

    /**
     * 철 반출등록 로직(넘어온 데이터가 기존 데이터와 다르면 수정후 반출날짜 등록)
     */
    //박스번호, 구축여부, 스캔여부 수정할게 있으면 하고 아니면 그냥 반출날짜 등록
    @Transactional
    public ExportSaveResponse updateExportInfo(ExportSaveRequest exportSaveRequest) {
        List<ExportSaveRequest.ExportList> E_lists = exportSaveRequest.getE_list();
        E_lists.stream()
                .forEach(files -> (fileRepository.findOne(files.getF_id())).updateFileExport(files.getB_num(), files.getF_db(), files.getF_scan(), files.getF_exportdate()));
        List<Long> id = E_lists.stream()
                .map(o -> o.getF_id())
                .collect(Collectors.toList());
        ExportSaveResponse exportSaveResponse = new ExportSaveResponse();
        exportSaveResponse.setF_id(id);
        return exportSaveResponse;
    }


    /**
     * 철 검색 로직1(넘어온 레이블 범위로 검색)
     */
    public List<ExportSearchLabelResponse> searchFileByLabelRange(FindExportByLabel findExportByLabel) {
        return fileRepository.findByLabelRange(findExportByLabel.getFirst_label(), findExportByLabel.getLast_label())
                .stream()
                .map(files -> new ExportSearchLabelResponse(files.getF_id(), files.getF_labelcode(), files.getF_name(), files.getF_pyear(), files.getF_kperiod(), files.getF_db(), files.getF_scan(), files.getB_num()))
                .collect(Collectors.toList());
    }


    /**
     * 철 검색로직2(넘어온 날짜로 범위검색, 반출날짜있는것만 검색)
     */
    public List<ExportSearchDateResponse> searchFileByDateRange(FindExportByDate findExportByDate) {
        return fileRepository.findByDateRange(findExportByDate.getFirst_date(), findExportByDate.getLast_date())
                .stream()
                .map(files -> new ExportSearchDateResponse(files.getF_id(), files.getOffice().getO_code(), files.getF_labelcode(), files.getOffice().getO_name(), files.getF_name(), files.getF_pyear(), files.getF_kperiod(), files.getF_db(), files.getF_scan(), files.getB_num(), files.getF_location(), files.getF_kplace(), files.getF_type(), files.getF_typenum()))
                .collect(Collectors.toList());

    }


    /**
     * 철 검색로직3(넘어온 박스번호로 해당 철 범위 검색 , 반출날짜있는것만 검색)
     */
    public List<ExportSearchBoxResponse> searchFileByBoxRange(FindExportByBox findExportByBox) {
        return fileRepository.findByBoxRange(findExportByBox.getFirst_box(), findExportByBox.getLast_box())
                .stream()
                .map(files -> new ExportSearchBoxResponse(files.getF_id(), files.getOffice().getO_code(), files.getF_labelcode(), files.getOffice().getO_name(), files.getF_name(), files.getF_pyear(), files.getF_kperiod(), files.getF_db(), files.getF_scan(), files.getB_num(), files.getF_location(), files.getF_kplace(), files.getF_type(), files.getF_typenum()))
                .collect(Collectors.toList());
    }
}
