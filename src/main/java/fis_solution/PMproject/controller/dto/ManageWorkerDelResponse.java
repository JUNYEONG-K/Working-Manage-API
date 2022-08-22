package fis_solution.PMproject.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 작성자: 고준영
 * 작성날짜: 2021/08/24
 * 작성내용: Manage Worker Delete Response
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageWorkerDelResponse {

    private Long w_id;  // 작업자 아이디

    private String err_code;  // 실패 시, 에러코드
}
