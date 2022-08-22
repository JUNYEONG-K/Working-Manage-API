package fis_solution.PMproject.controller.dto;
import fis_solution.PMproject.domain.record.F_location;
import fis_solution.PMproject.domain.record.fileEnum.F_construct;
import fis_solution.PMproject.domain.record.fileEnum.F_kperiod;
import fis_solution.PMproject.domain.record.fileEnum.F_kplace;
import fis_solution.PMproject.domain.record.fileEnum.F_type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 작성자: 원보라
 * 작성날짜: 2021/08/24
 * 작성내용: ExportSearchDateResponse
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportSearchDateResponse {
    private Long f_id;          // 철아이디
    private String o_code;      // 기관코드
    private String f_labelcode; // 레이블
    private String o_name;      // 기관이름
    private String f_name;      // 철이름
    private String f_pyear;     // 생산년도
    private F_kperiod f_kperiod;     // 보존기간
    private F_construct f_db;        // 구축여부
    private F_construct f_scan;      // 스캔여부
    private String b_num;       // 박스번호
    private F_location f_location;  // 위치(서가, 층, 열, 번)
    private F_kplace f_kplace;  // 보존장소
    private F_type f_type;      // 문서종류
    private String f_typenum;   // 분류번호
}
