package fis_solution.PMproject.controller.dto;

import fis_solution.PMproject.domain.management.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 작성자: 고준영
 * 작성날짜: 2021/08/30
 * 작성내용: Manage Worker UPdate Request
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageWorkerUpdateRequest {

    private Long w_id;
    private Authority w_authority;
    private String w_name;
    private String w_ssn;
    private String w_address;
    private String w_tel;
    private String w_bank;
    private String w_account;
    private String w_sdate;
    private String w_edate;
}
