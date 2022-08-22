package fis_solution.PMproject.PreInfoServiceTest;

import fis_solution.PMproject.controller.dto.PreinfoFileDelResponse;
import fis_solution.PMproject.controller.dto.PreinfoFileSaveRequest;
import fis_solution.PMproject.controller.dto.PreinfoFileSaveResponse;
import fis_solution.PMproject.controller.dto.PreinfoFileSearchResponse;
import fis_solution.PMproject.domain.Office;
import fis_solution.PMproject.domain.record.Files;
import fis_solution.PMproject.domain.record.fileEnum.*;
import fis_solution.PMproject.repository.FileRepository;
import fis_solution.PMproject.repository.OfficeRepository;
import fis_solution.PMproject.repository.search.FindPreinfoBySearch;
import fis_solution.PMproject.service.CommonService;
import fis_solution.PMproject.service.PreInfoService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

/*
 * 작성자: 원보라
 * 작성날짜: 2021/08/27
 * 작성내용: PreInfoServiceTest
 */


@SpringBootTest
@Transactional()
public class PreInfoServiceTest {

    @Autowired
    PreInfoService preInfoService;

    @Autowired
    CommonService commonService;

    @Autowired
    EntityManager em;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    OfficeRepository officeRepository;

    @Test
    public void 철정보저장() {
        //등록 되어있는 기관이용을 위해 기관 등록
        Office office1 = new Office();
        office1.setO_code("3334444");
        office1.setO_name("어떤기관이름");
        office1.setO_del("0");
        String id1 = commonService.join(office1);

        //등록된 기관으로 부터 code,name 받아서 넘김
        Office testOffice = commonService.findOne(id1);
        final PreinfoFileSaveRequest savedto1 = new PreinfoFileSaveRequest(4L, testOffice.getO_code(), "123123", testOffice.getO_name(), "어떤문서이름", "1999", F_kperiod.PERMANENT, F_construct.YES, F_construct.YES, "003", null, F_kplace.PROFESSION, F_type.GENERAL, "어떤문서번호");


        PreinfoFileSaveResponse SavePreInfoId1 = preInfoService.savePreinfo(savedto1);

        //file 생성되는지 확인
        Files result1 = fileRepository.findOne(SavePreInfoId1.getF_id());

        Assertions.assertThat(result1.getF_id()).isEqualTo(4L);


        //레이블 중복 검사
        final PreinfoFileSaveRequest savedto2 = new PreinfoFileSaveRequest(5L, testOffice.getO_code(), "123123", testOffice.getO_name(), "어떤문서이름", "1999", F_kperiod.PERMANENT, F_construct.YES, F_construct.YES, "003", null, F_kplace.PROFESSION, F_type.GENERAL, "어떤문서번호");
        try {
            preInfoService.savePreinfo(savedto2);
        } catch (IllegalStateException e) {
            return;
        }
        Assertions.fail("이미 존재하는 레이블입니다.");
    }

    @Test
    public void 철정보수정() {
        //정보 수정 update
        final PreinfoFileSaveRequest updateDto1 = new PreinfoFileSaveRequest(3L, "5555444", "000004", "기관이름", "어떤문서이름", "1999", F_kperiod.PERMANENT, F_construct.YES, F_construct.YES, "003", null, F_kplace.PROFESSION, F_type.GENERAL, "어떤문서번호");
        preInfoService.updatePreinfo(updateDto1);
        em.flush();
        Assertions.assertThat(fileRepository.findOne(3L).getF_labelcode()).isEqualTo("000004");
    }

    @Test
    public void 철정보삭제() {
        //PreinfoFileDelResponse removeId = preInfoService.removePreinfo(fileRepository.findOne(3L).getF_id());
        //em.flush();
        //Assertions.assertThat(removeId.getF_id()).isEqualTo(3L);
    }


    @Test
    public void 철정보검색() {
        //여러개의 정보 중 null이면 무시하고 검색
        FindPreinfoBySearch findPreinfoBySearch1 = new FindPreinfoBySearch();
        Office office1 = officeRepository.findOne("1234567");
        findPreinfoBySearch1.setOffice(office1);
        findPreinfoBySearch1.setF_labelcode(null);
        findPreinfoBySearch1.setF_name("강남구청 인사과");
        findPreinfoBySearch1.setF_pyear("2021");

        List<PreinfoFileSearchResponse> result1 = preInfoService.searchFileByPreinfo(findPreinfoBySearch1);
        Files compare = fileRepository.findOne(1L);

        //철이름 Like
        FindPreinfoBySearch findPreinfoBySearch2 = new FindPreinfoBySearch();
        findPreinfoBySearch2.setOffice(null);
        findPreinfoBySearch2.setF_labelcode(null);
        findPreinfoBySearch2.setF_name("인사과");
        findPreinfoBySearch2.setF_pyear(null);

        List<PreinfoFileSearchResponse> result2 = preInfoService.searchFileByPreinfo(findPreinfoBySearch2);

        Assertions.assertThat(result1.get(0).getF_id()).isEqualTo(compare.getF_id());
        Assertions.assertThat(result2.size()).isEqualTo(2);
    }

    @Test
    public static void main(String[] args) {
        PreInfoService.mkdir(4L, "001", "1234567");
        PreInfoService.mkdir(5L, "001", "1234567");
        PreInfoService.mkdir(5L, "001", "1234567");
        PreInfoService.mkdir(5L, "002", "1234568");
    }
}
