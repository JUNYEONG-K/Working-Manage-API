package fis_solution.PMproject.controller.dto;

import fis_solution.PMproject.domain.management.WorkerManagement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

// 이게 맞나? ㅋㅋ

/**
 * 작성자: 고준영
 * 작업날짜: 2021/08/24
 * 작업내용: Manage Workplace Period Search Date Resonse
 * ?????????????????????
 */

@Data
@AllArgsConstructor
public class ManageWorkplacePeriodSearchDateResponse {


    private int search;
    private int list;
    private int export;
    private int classify;
    private int classifych;
    private int page;
    private int pagech;
    private int scan;
    private int scanpage;
    private int image;
    private int imagech;
    private int index;
    private int indexch;
    private int loading;
    private int reorg;
    private int boxreorg;
    private int place;
    private int etc;

}
