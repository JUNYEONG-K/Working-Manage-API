package fis_solution.PMproject.controller.dto;

import fis_solution.PMproject.domain.management.Worker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

/**
 * 작성자: 고준영
 * 작성날짜: 2021/09/01
 * 작성내용: Manage Workplace Daily Request 일일작업량
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageWorkplaceDailyRequest {

    private Long w_id;
    private String m_date;

    private int m_attend;

    private int m_night;

    private int m_late;

    private int m_leave;

    private int m_out;

    private int m_process;

    private Long m_amount;

    private String m_etc;
}
