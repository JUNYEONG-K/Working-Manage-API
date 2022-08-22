package fis_solution.PMproject.CommonServiceTest;

import fis_solution.PMproject.domain.Office;
import fis_solution.PMproject.service.CommonService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
public class CommonServiceTest {

    @Autowired
    CommonService commonService;

    @Autowired
    EntityManager em;

    @Test
    public void 기관() {

        Office office1 = new Office();
        office1.setO_code("2347812");
        office1.setO_name("노원구청");
        office1.setO_del("0");

        Office office2 = new Office();
        office2.setO_code("1123456");
        office2.setO_name("도봉구청");
        office2.setO_del("1");

        // 기관 등록
        String id1 = commonService.join(office1);
        String id2 = commonService.join(office2);

        // 기관 조회
        Office first = commonService.findOne(id1);
        Office second = commonService.findOne(id2);

        List<Office> offices = commonService.findAll();

        // 등록, 조회 테스트
        Assertions.assertThat(first).isEqualTo(office1);
        Assertions.assertThat(second).isEqualTo(office2);


        Assertions.assertThat(offices.get(3)).isEqualTo(office2);
        Assertions.assertThat(offices.get(2)).isEqualTo(office1);
        Assertions.assertThat(offices.size()).isEqualTo(4);

        // 중복 오류 테스트
        Office repeat = new Office();
        repeat.setO_code("2347812");
        repeat.setO_name("강남구청");
        repeat.setO_del("0");

        try {
            commonService.join(repeat);
        } catch(IllegalStateException e) {
            return;
        }
        Assertions.fail("같은 기관코드를 가진 기관은 등록할 수 없습니다.");

        // 기관 정보 수정 테스트
        commonService.updateOffice(office1.getO_code(), "서울시청", "1");
        Assertions.assertThat(office1.getO_name()).isEqualTo("서울시청");
        Assertions.assertThat(office1.getO_del()).isEqualTo("1");

        // 기관 정보 삭제 테스트
        String removeCode = commonService.removeOffice(office2);
        Assertions.assertThat(removeCode).isEqualTo(office2.getO_code());

    }
}
