package fis_solution.PMproject.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 작성자: 고준영
 * 작업날짜: 2021/08/24
 * 작업내용: Manage Worker Response
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageWorkerResponse {

    private Long w_id;  // 작업자 id

    private String err_code;  // 실패 시, 에러코드
}
