package fis_solution.PMproject.controller.dto;

import fis_solution.PMproject.domain.management.Authority;
import fis_solution.PMproject.domain.management.Worker;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 작성자: 고준영
 * 작성날짜: 2021/08/24
 * 작성내용: Manage Worker List Response
 */

@Data
@AllArgsConstructor
public class ManageWorkerListResponse {

    private List<WorkerDTO> workers; // 작업자 리스트 반환

    // 이러면 작업자 리스트가 뜨나??

    @Data
    public static class WorkerDTO {

        private Authority authority; // 작업자 구분
        private String w_name;  // 작업자 이름
        private String w_ssn;   // 작업자 주민번호
        private String w_address; // 작업자 주소
        private String w_tel;   // 작업자 전화번호
        private String w_bank;  // 작업자 은행
        private String w_account;  // 작업자 계좌
        private String w_sdate; // 작업자 근무시작 날짜
        private String w_edate; // 작업자 근무종료 날짜

        public WorkerDTO(Worker worker) {
            authority = worker.getAuthority();
            w_name = worker.getW_name();
            w_ssn = worker.getW_ssn();
            w_address = worker.getW_address();
            w_tel = worker.getW_tel();
            w_bank = worker.getW_bank();
            w_account = worker.getW_account();
            w_sdate = worker.getW_sdate();
            w_edate = worker.getW_edate();
        }
    }
}
