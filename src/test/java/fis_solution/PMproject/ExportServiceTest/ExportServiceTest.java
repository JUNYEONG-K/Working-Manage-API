package fis_solution.PMproject.ExportServiceTest;


import fis_solution.PMproject.controller.dto.*;
import fis_solution.PMproject.domain.record.Files;
import fis_solution.PMproject.domain.record.fileEnum.F_construct;
import fis_solution.PMproject.domain.record.fileEnum.F_kperiod;
import fis_solution.PMproject.domain.record.fileEnum.F_kplace;
import fis_solution.PMproject.domain.record.fileEnum.F_type;
import fis_solution.PMproject.repository.FileRepository;
import fis_solution.PMproject.repository.search.FindExportByBox;
import fis_solution.PMproject.repository.search.FindExportByDate;
import fis_solution.PMproject.repository.search.FindExportByLabel;
import fis_solution.PMproject.service.ExportService;
import fis_solution.PMproject.service.PreInfoService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/*
 * 작성자: 원보라
 * 작성날짜: 2021/08/27
 * 작성내용: ExportServiceTest
 */

@SpringBootTest
@Transactional
public class ExportServiceTest {
    @Autowired
    PreInfoService preInfoService;

    @Autowired
    ExportService exportService;


    @Autowired
    FileRepository fileRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 철반출등록로직() {
        /*
         * 철 반출등록 로직(넘어온 데이터가 기존 데이터와 다르면 수정후 반출날짜 등록)

        //박스번호, 구축여부, 스캔여부 수정할게 있으면 하고 아니면 그냥 반출날짜 등록
        @Transactional
        public ExportSaveResponse updateExportInfo(ExportSaveRequest exportSaveRequest) {
            Files files = fileRepository.findOne(exportSaveRequest.getF_id()); //선택된 행
            files.updateFileExport(exportSaveRequest.getB_num(), exportSaveRequest.getF_db(), exportSaveRequest.getF_scan(), exportSaveRequest.getF_exportdate());
            ExportSaveResponse exportSaveResponse = new ExportSaveResponse();
            exportSaveResponse.setF_id(files.getF_id());
            return exportSaveResponse;
        }
            //=======================수정 메서드(210825원보라)==========================//
    public void updateFileExport(String b_num, F_construct f_db, F_construct f_scan, String f_exportdate){
        this.b_num=b_num;                   // 박스번호
        this.f_db=f_db;                     // 구축여부
        this.f_scan=f_scan;                 // 스캔여부
        this.f_exportdate=f_exportdate;     // 반출날짜
    }

        *
        * */

        //정보가 수정된 경우
        Files files1 = fileRepository.findOne(1L);
        Date date = new Date();
        String strExportDate = String.format("%1$tY-%1$tm-%1$td", date); //반출날짜 string으로 변환
        final ExportSaveRequest nothingChangeDto1 = new ExportSaveRequest(files1.getF_id(), strExportDate, F_construct.YES, F_construct.NO, "777");
        exportService.updateExportInfo(nothingChangeDto1);
        em.flush();
        Assertions.assertThat(fileRepository.findOne(1L).getF_exportdate()).isEqualTo("2021-08-30");




    }
    @Test
    public void 철검색로직1() {
        /*
         * 철 검색 로직1(넘어온 레이블 범위로 검색)

        public List<Files> searchFileByLabelRange(FindExportByLabel findExportByLabel){
            return fileRepository.findByLabelRange(findExportByLabel.getFirst_label(), findExportByLabel.getLast_label());
        }
         */

        //빈칸으로 검색 (파일 모두 반환)
        FindExportByLabel findExportByLabel1 = new FindExportByLabel();
        findExportByLabel1.setFirst_label(null);
        findExportByLabel1.setLast_label(null);
        List<ExportSearchLabelResponse> result1 = exportService.searchFileByLabelRange(findExportByLabel1);

        //시작 label만 검색 (시작 label 이후 반환)
        FindExportByLabel findExportByLabel2 = new FindExportByLabel();
        findExportByLabel2.setFirst_label("000003");
        findExportByLabel2.setLast_label(null);
        List<ExportSearchLabelResponse> result2 = exportService.searchFileByLabelRange(findExportByLabel2);

        //끝 label만 검색 (끝 label 이전 반환)
        FindExportByLabel findExportByLabel3 = new FindExportByLabel();
        findExportByLabel3.setFirst_label(null);
        findExportByLabel3.setLast_label("000003");
        List<ExportSearchLabelResponse> result3 = exportService.searchFileByLabelRange(findExportByLabel3);

        //시작, 끝 label로 검색 (시작~끝 반환)
        FindExportByLabel findExportByLabel4 = new FindExportByLabel();
        findExportByLabel4.setFirst_label("000002");
        findExportByLabel4.setLast_label("000003");
        List<ExportSearchLabelResponse> result4 = exportService.searchFileByLabelRange(findExportByLabel4);

        Assertions.assertThat(result1.size()).isEqualTo(3); //현재 init db 철 개수 3개임
        Assertions.assertThat(result2.get(0).getF_id()).isEqualTo(3L);
        Assertions.assertThat(result3.get(1).getF_id()).isEqualTo(2L);
        Assertions.assertThat(result4.get(1).getF_id()).isEqualTo(3L);



    }
    @Test
    public void 철검색로직2() {
        /*
         * 철 검색로직2(넘어온 날짜로 범위검색, 반출날짜있는것만 검색)

        public List<Files> searchFileByDateRange(FindExportByDate findExportByDate){
            return fileRepository.findByDatelRange(findExportByDate.getFirst_date(), findExportByDate.getLast_date());
        }
        */

        //빈칸으로 검색 (반출된 파일 모두 반환)
        FindExportByDate findExportByDate1 = new FindExportByDate();
        findExportByDate1.setFirst_date(null);
        findExportByDate1.setLast_date(null);
        List<ExportSearchDateResponse> result1 = exportService.searchFileByDateRange(findExportByDate1);

        //시작 날짜만 검색 (시작 날짜 이후 반환)
        FindExportByDate findExportByDate2 = new FindExportByDate();
        findExportByDate2.setFirst_date("2021-08-20");
        findExportByDate2.setLast_date(null);
        List<ExportSearchDateResponse> result2 = exportService.searchFileByDateRange(findExportByDate2);

        //끝 날짜만 검색 (끝 날짜 이전 반환)
        FindExportByDate findExportByDate3 = new FindExportByDate();
        findExportByDate3.setFirst_date(null);
        findExportByDate3.setLast_date("2000-01-01");
        List<ExportSearchDateResponse> result3 = exportService.searchFileByDateRange(findExportByDate3);

        //시작, 끝 날짜로 검색 (시작~끝 반환)
        FindExportByDate findExportByDate4 = new FindExportByDate();
        findExportByDate4.setFirst_date("1997-01-01");
        findExportByDate4.setLast_date("2000-01-01");
        List<ExportSearchDateResponse> result4 = exportService.searchFileByDateRange(findExportByDate4);

        Assertions.assertThat(result1.size()).isEqualTo(3);
        Assertions.assertThat(result2.get(0).getF_id()).isEqualTo(1L);
        Assertions.assertThat(result3.get(0).getF_id()).isEqualTo(2L);
        Assertions.assertThat(result4.get(0).getF_id()).isEqualTo(2L);

    }
    @Test
    public void 철검색로직3() {
        /*
         * 철 검색로직3(넘어온 박스번호로 해당 철 범위 검색, 반출날짜있는것만 검색)

        public List<Files> searchFileByBoxRange(FindExportByBox findExportByBox){
            return fileRepository.findByBoxRange(findExportByBox.getFirst_box(), findExportByBox.getLast_box());
        }
         */

        //빈칸으로 검색 (반출된 파일 모두 반환)
        FindExportByBox findExportByBox1 = new FindExportByBox();
        findExportByBox1.setFirst_box(null);
        findExportByBox1.setLast_box(null);
        List<ExportSearchBoxResponse> result1 = exportService.searchFileByBoxRange(findExportByBox1);

        //시작 Box만 검색 (시작 Box 이후 반환)
        FindExportByBox findExportByBox2 = new FindExportByBox();
        findExportByBox2.setFirst_box("002");
        findExportByBox2.setLast_box(null);
        List<ExportSearchBoxResponse> result2 = exportService.searchFileByBoxRange(findExportByBox2);

        //끝 Box만 검색 (끝 Box 이전 반환)
        FindExportByBox findExportByBox3 = new FindExportByBox();
        findExportByBox3.setFirst_box(null);
        findExportByBox3.setLast_box("003");
        List<ExportSearchBoxResponse> result3 = exportService.searchFileByBoxRange(findExportByBox3);

        //시작, 끝 Box로 검색 (시작~끝 반환)
        FindExportByBox findExportByBox4 = new FindExportByBox();
        findExportByBox4.setFirst_box("002");
        findExportByBox4.setLast_box("007");
        List<ExportSearchBoxResponse> result4 = exportService.searchFileByBoxRange(findExportByBox4);

        Assertions.assertThat(result1.size()).isEqualTo(3);
        Assertions.assertThat(result2.get(0).getF_id()).isEqualTo(2L);
        Assertions.assertThat(result3.size()).isEqualTo(3);
        Assertions.assertThat(result4.get(0).getF_id()).isEqualTo(2L);


    }

}
