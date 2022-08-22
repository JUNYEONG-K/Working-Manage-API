package fis_solution.PMproject.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 작성자: 고준영
 * 작성날짜: 2021/08/30
 * 작성내용: Manage Worker Update Response
 */

@Data
@AllArgsConstructor
public class ManageWorkerUpdateResponse {

    private Long id;

    private String err_code;
}
