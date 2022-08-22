package fis_solution.PMproject.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 작성자: 고준영
 * 작성날짜: 2021/08/24
 * 작성내용: Manage Workplace Record Response
 */

@Data
@AllArgsConstructor
public class ManageWorkplaceRecordResponse {

    private List<HistoryDTO> historyDTOS;

    public static class HistoryDTO {

        private String f_labelcode;     // 레이블 번호
        private String o_name;          // 생산기관명
        private String f_name;          // 철 제목
        private String f_complete;      // 색인 입력 날짜
        private String complete_list;   // 색인 입력을 작업한 작업자 이름 (w_idput 활용)
        private String f_check;         // 색인 검수 날짜
        private String check_list;      // 색인 검수를 작업한 작업자 이름 (w_idcheck 활용)

    }

}
