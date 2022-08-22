package fis_solution.PMproject.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 작성자: 고준영
 * 작성날짜: 2021/08/24
 * 직상내용: Manage Workplace Plan Request
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageWorkplacePlanRequest {

    private Long p_id;

    private String p_search;    // 전수조사 계획(권)
    private String p_searchw;   // 전수조사 인력

    private String p_list;        // 목록작업 계획(권)
    private String p_listw;       // 목록작업 인력

    private String p_export;    // 반입반출 계획(권)
    private String p_exportw;   // 반입반출 인력

    private String p_classify;  // 분류 계획(권)
    private String p_classifyw; // 분류 인원

    private String p_classifych; // 분류검증 계획(권)
    private String p_classifychw; // 분류검증 인력

    private String p_page;       // 면표시 계획(권)
    private String p_pagew;      // 면표시 인력

    private String p_pagech;     // 면표시 검증 계획(권)
    private String p_pagechw;    // 면표시 검증 인력

    private String p_scan;       // 스캔문서 계획(권)
    private String p_scanw;      // 스캔문서 인력

    private String p_scanpage;   // 스캔도면 계획(권)
    private String p_scanpagew;  // 스캔도면 인력

    private String p_image;      // 이미지 보정 계획(권)
    private String p_imagew;     // 이미지 보정 인력

    private String p_imagech;    // 이미지 검증 계획(권)
    private String p_imagechw;   // 이미지 검증 인력

    private String p_index;      // 색인 입력 계획(권)
    private String p_indexw;     // 색인 입력 인력

    private String p_indexch;    // 색인 검증 계획(권)
    private String p_indexchw;   // 색인 검증 인력

    private String p_loading;    // 로딩 계획(권)
    private String p_loadingw;   // 로딩 인력

    private String p_reorg;      // 재편철 계획(권)
    private String p_reorgw;     // 재편철 인력

    private String p_boxreorg;   // 보존상자편성 계획(권)
    private String p_boxreorgw;  // 보존상자편성 인력

    private String p_place;      // 서가배치 계획(권)
    private String p_placew;     // 서가배치 인력

    private String p_etc;        // 기타작업 계획(권)
    private String p_etcw;       // 기타작업 인력
}
