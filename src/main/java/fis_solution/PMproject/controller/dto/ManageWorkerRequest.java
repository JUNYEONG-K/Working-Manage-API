package fis_solution.PMproject.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import fis_solution.PMproject.domain.management.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.ConstructorProperties;

/**
 * 작성자: 고준영
 * 작성날짜: 2021/08/24
 * 작성내용: Manage Worker Request
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageWorkerRequest {

    private Long w_id;                  // 작업자 id
    private Authority w_authority;      // 작업자 권한 (WORKER, ADMIN)
    private String w_name;              // 작업자 이름
    private String w_ssn;               // 작업자 주민등록 번호
    private String w_tel;               // 작업자 전화번호
    private String w_address;           // 작업자 주소
    private String w_bank;              // 작업자 은행
    private String w_account;           // 작업자 계좌번호
    private String w_sdate;             // 작업자 근무 시작일
    private String w_edate;             // 작업자 근무 종료일

}
