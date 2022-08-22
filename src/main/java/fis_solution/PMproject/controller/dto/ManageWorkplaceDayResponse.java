package fis_solution.PMproject.controller.dto;

import fis_solution.PMproject.domain.management.Worker;
import fis_solution.PMproject.domain.management.WorkerManagement;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 작성자: 고준영
 * 작성날짜: 2021/08/24
 * 작성내용: Manage Workplace Day Response
 */

@Data
@AllArgsConstructor
public class ManageWorkplaceDayResponse {

    private List<WorkerManagementDTO> workerManagementDTOS;

    public static class WorkerManagementDTO {

        private String w_name;      // 작업자 이름
        private String m_date;      // 작업 날짜
        private String m_attend;    // 출근
        private String m_night;     // 야근
        private String m_late;      // 지각
        private String m_leave;     // 조퇴
        private String m_out;       // 외출
        private String MD;          // MD????

        private String p_search;    // 전수조사(권)
        private String p_list;      // 목록조사(권)
        private String p_export;    // 바닙반출(권)
        private String p_classify;  // 분류(권)
        private String p_classifych;// 분류검증(권)
        private String p_page;      // 면표시(권)
        private String p_pagech;    // 면표시 검증(권)
        private String p_scan;      // 스캔문서(권)
        private String p_scanpage;  // 스캔도면(권)
        private String p_image;     // 이미지 보정(권)
        private String p_imagech;   // 이미지 검증(권)
        private String p_index;     // 색인작업(권)
        private String p_indexch;   // 색인검증(권)
        private String p_loading;   // 로딩(권)
        private String p_reorg;     // 재편철(권)
        private String p_boxreorg;  // 보존상자 편성(권)
        private String p_place;     // 서가배치(권)
        private String p_etc;       // 기타작업(권)

    }
}
