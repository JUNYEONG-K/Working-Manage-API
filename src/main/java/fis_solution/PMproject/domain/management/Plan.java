package fis_solution.PMproject.domain.management;

import fis_solution.PMproject.controller.dto.ManageWorkplacePlanRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/*
 * 수정자: 한명수
 * 수정날짜: 2021/08/26
 * 수정내용: @Setter 추가
 */
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Plan {
    @Id
    @GeneratedValue
    @Column(name = "p_id")
    private Long id;

    @Column(length = 10)
    private String p_search;
    @Column(length = 10)
    private String p_searchw;

    @Column(length = 10)
    private String p_list;
    @Column(length = 10)
    private String p_listw;

    @Column(length = 10)
    private String p_export;
    @Column(length = 10)
    private String p_exportw;

    @Column(length = 10)
    private String p_classify;
    @Column(length = 10)
    private String p_classifyw;

    @Column(length = 10)
    private String p_classifych;
    @Column(length = 10)
    private String p_classifychw;

    @Column(length = 10)
    private String p_page;
    @Column(length = 10)
    private String p_pagew;

    @Column(length = 10)
    private String p_pagech;
    @Column(length = 10)
    private String p_pagechw;

    @Column(length = 10)
    private String p_scan;
    @Column(length = 10)
    private String p_scanw;

    @Column(length = 10)
    private String p_scanpage;
    @Column(length = 10)
    private String p_scanpagew;

    @Column(length = 10)
    private String p_image;
    @Column(length = 10)
    private String p_imagew;

    @Column(length = 10)
    private String p_imagech;
    @Column(length = 10)
    private String p_imagechw;

    @Column(length = 10)
    private String p_index;
    @Column(length = 10)
    private String p_indexw;

    @Column(length = 10)
    private String p_indexch;
    @Column(length = 10)
    private String p_indexchw;

    @Column(length = 10)
    private String p_loading;
    @Column(length = 10)
    private String p_loadingw;

    @Column(length = 10)
    private String p_reorg;
    @Column(length = 10)
    private String p_reorgw;

    @Column(length = 10)
    private String p_boxreorg;
    @Column(length = 10)
    private String p_boxreorgw;

    @Column(length = 10)
    private String p_place;
    @Column(length = 10)
    private String p_placew;

    @Column(length = 10)
    private String p_etc;
    @Column(length = 10)
    private String p_etcw;

    public Plan(String p_search, String p_searchw, String p_list, String p_listw, String p_export, String p_exportw, String p_classify, String p_classifyw,
                String p_classifych, String p_classifychw, String p_page, String p_pagew, String p_pagech, String p_pagechw, String p_scan, String p_scanw,
                String p_scanpage, String p_scanpagew, String p_image, String p_imagew, String p_imagech, String p_imagechw, String p_index, String p_indexw,
                String p_indexch, String p_indexchw, String p_loading, String p_loadingw, String p_reorg, String p_reorgw, String p_boxreorg, String p_boxreorgw,
                String p_place, String p_placew, String p_etc, String p_etcw) {
        this.p_search = p_search;
        this.p_searchw = p_searchw;
        this.p_list = p_list;
        this.p_listw = p_listw;
        this.p_export = p_export;
        this.p_exportw = p_exportw;
        this.p_classify = p_classify;
        this.p_classifyw = p_classifyw;
        this.p_classifych = p_classifych;
        this.p_classifychw = p_classifychw;
        this.p_page = p_page;
        this.p_pagew = p_pagew;
        this.p_pagech = p_pagech;
        this.p_pagechw = p_pagechw;
        this.p_scan = p_scan;
        this.p_scanw = p_scanw;
        this.p_scanpage = p_scanpage;
        this.p_scanpagew = p_scanpagew;
        this.p_image = p_image;
        this.p_imagew = p_imagew;
        this.p_imagech = p_imagech;
        this.p_imagechw = p_imagechw;
        this.p_index = p_index;
        this.p_indexw = p_indexw;
        this.p_indexch = p_indexch;
        this.p_indexchw = p_indexchw;
        this.p_loading = p_loading;
        this.p_loadingw = p_loadingw;
        this.p_reorg = p_reorg;
        this.p_reorgw = p_reorgw;
        this.p_boxreorg = p_boxreorg;
        this.p_boxreorgw = p_boxreorgw;
        this.p_place = p_place;
        this.p_placew = p_placew;
        this.p_etc = p_etc;
        this.p_etcw = p_etcw;
    }

    /**
     * 작성자: 고준영
     * 작성날짜: 2021/08/25
     * 작성내용: id값을 제외한 생성자  => test에는 setter를 쓰고, 향후에 updatePlan으로 바꾸자
     */
    public void updatePlan(ManageWorkplacePlanRequest request)
    {
        this.p_search = request.getP_search();
        this.p_searchw = request.getP_searchw();
        this.p_list = request.getP_list();
        this.p_listw = request.getP_listw();
        this.p_export = request.getP_export();
        this.p_exportw = request.getP_exportw();
        this.p_classify = request.getP_classify();
        this.p_classifyw = request.getP_classifyw();
        this.p_classifych = request.getP_classifych();
        this.p_classifychw = request.getP_classifychw();
        this.p_page = request.getP_page();
        this.p_pagew = request.getP_pagew();
        this.p_pagech = request.getP_pagech();
        this.p_pagechw = request.getP_pagechw();
        this.p_scan = request.getP_scan();
        this.p_scanw = request.getP_scanw();
        this.p_scanpage = request.getP_scanpage();
        this.p_scanpagew = request.getP_scanpagew();
        this.p_image = request.getP_image();
        this.p_imagew = request.getP_imagew();
        this.p_imagech = request.getP_imagech();
        this.p_imagechw = request.getP_imagechw();
        this.p_index = request.getP_index();
        this.p_indexw = request.getP_indexw();
        this.p_indexch = request.getP_indexch();
        this.p_indexchw = request.getP_indexchw();
        this.p_loading = request.getP_loading();
        this.p_loadingw = request.getP_loadingw();
        this.p_reorg = request.getP_reorg();
        this.p_reorgw = request.getP_reorgw();
        this.p_boxreorg = request.getP_boxreorg();
        this.p_boxreorgw = request.getP_boxreorgw();
        this.p_place = request.getP_place();
        this.p_placew = request.getP_placew();
        this.p_etc = request.getP_etc();
        this.p_etcw = request.getP_etcw();

    }

    /**
     * 작성자: 고준영
     * 작성날짜: 2021/09/01
     * 작성내용: createPlan
     */
    public static Plan createPlan(ManageWorkplacePlanRequest request) {
        Plan plan = new Plan(
                request.getP_search(), request.getP_searchw(), request.getP_list(), request.getP_listw(), request.getP_export(), request.getP_exportw(), request.getP_classify(), request.getP_classifyw(),
                request.getP_classifych(), request.getP_classifychw(), request.getP_page(), request.getP_pagew(), request.getP_pagech(), request.getP_pagechw(), request.getP_scan(), request.getP_scanw(),
                request.getP_scanpage(), request.getP_scanpagew(), request.getP_image(), request.getP_imagew(), request.getP_imagech(), request.getP_imagechw(), request.getP_index(), request.getP_indexw(),
                request.getP_indexch(), request.getP_indexchw(), request.getP_loading(), request.getP_loadingw(), request.getP_reorg(), request.getP_reorgw(), request.getP_boxreorg(), request.getP_boxreorgw(),
                request.getP_place(), request.getP_placew(), request.getP_etc(), request.getP_etcw());
        return plan;
    }
}
