package fis_solution.PMproject.controller.dto;

import fis_solution.PMproject.domain.management.Plan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageWorkplacePlanResponse {

    // 보류 사항임
    private Long id;
//    private String err_code;


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

    public void match(Plan plan) {
//        this.id = plan.getId();
        this.p_search = plan.getP_search();
        this.p_searchw = plan.getP_searchw();
        this.p_list = plan.getP_list();
        this.p_listw = plan.getP_listw();
        this.p_export = plan.getP_export();
        this.p_exportw = plan.getP_exportw();
        this.p_classify = plan.getP_classify();
        this.p_classifyw = plan.getP_classifyw();
        this.p_classifych = plan.getP_classifych();
        this.p_classifychw = plan.getP_classifychw();
        this.p_page = plan.getP_page();
        this.p_pagew = plan.getP_pagew();
        this.p_pagech = plan.getP_pagech();
        this.p_pagechw = plan.getP_pagechw();
        this.p_scan = plan.getP_scan();
        this.p_scanw = plan.getP_scanw();
        this.p_scanpage = plan.getP_scanpage();
        this.p_scanpagew = plan.getP_scanpagew();
        this.p_image = plan.getP_image();
        this.p_imagew = plan.getP_imagew();
        this.p_imagech = plan.getP_imagech();
        this.p_imagechw = plan.getP_imagechw();
        this.p_index = plan.getP_index();
        this.p_indexw = plan.getP_indexw();
        this.p_indexch = plan.getP_indexch();
        this.p_indexchw = plan.getP_indexchw();
        this.p_loading = plan.getP_loading();
        this.p_loadingw = plan.getP_loadingw();
        this.p_reorg = plan.getP_reorg();
        this.p_reorgw = plan.getP_reorgw();
        this.p_boxreorg = plan.getP_boxreorg();
        this.p_boxreorgw = plan.getP_boxreorgw();
        this.p_place = plan.getP_place();
        this.p_placew = plan.getP_placew();
        this.p_etc = plan.getP_etc();
        this.p_etcw = plan.getP_etcw();
    }
}
