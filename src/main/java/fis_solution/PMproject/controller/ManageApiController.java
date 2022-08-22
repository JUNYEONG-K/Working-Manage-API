package fis_solution.PMproject.controller;

import fis_solution.PMproject.controller.dto.*;
import fis_solution.PMproject.domain.Office;
import fis_solution.PMproject.domain.management.Authority;
import fis_solution.PMproject.domain.management.Plan;
import fis_solution.PMproject.domain.management.Worker;
import fis_solution.PMproject.domain.management.WorkerManagement;
import fis_solution.PMproject.domain.record.Files;
import fis_solution.PMproject.domain.record.fileEnum.F_construct;
import fis_solution.PMproject.domain.record.fileEnum.F_process;
import fis_solution.PMproject.repository.FileRepository;
import fis_solution.PMproject.repository.OfficeRepository;
import fis_solution.PMproject.repository.PlanRepository;
import fis_solution.PMproject.service.ManageService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ManageApiController {

    private final ManageService manageService;
    private final PlanRepository planRepository;
    private final FileRepository fileRepository;
    private final OfficeRepository officeRepository;
    // ===== 작업자 ===== //

    // 작업자 정보 등록 & 수정 (성공)
    @PostMapping("manage/worker")
    public ManageWorkerResponse saveWorker(@RequestBody @Valid  ManageWorkerRequest workerrequest, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (workerrequest.getW_id() == null) {
            return manageService.saveWorker(workerrequest);
        } else {
            return manageService.updateWorker(workerrequest);
        }

    }

    // 작업자 정보 삭제 (성공)
    @DeleteMapping("manage/worker/{w_id}")
    public ManageWorkerDelResponse delWorker(@PathVariable Long w_id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        return manageService.removeWorker(w_id);
    }

    // 전체 작업자 조회 (성공)
    @GetMapping("manage/worker")
    public Result searchWorker(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<Worker> workers = manageService.findWorkers();
        List<WorkerDTO> collect = workers.stream()
                .map(worker -> new WorkerDTO(worker.getId(), worker.getAuthority(), worker.getW_name(), worker.getW_ssn(),worker.getW_address(), worker.getW_tel(), worker.getW_bank(), worker.getW_account(), worker.getW_sdate(), worker.getW_edate()))
                .collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }
    @Data
    @AllArgsConstructor
    static class WorkerDTO {
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


    // ===== 계획 ===== //
    // 작업장 계획 저장 & 수정 (성공)
    @PostMapping("manage/workplace/plan")
    public ManageWorkplacePlanResponse savePlan(HttpServletRequest request, @RequestBody ManageWorkplacePlanRequest planRequest){
        HttpSession session = request.getSession();
        if(planRequest.getP_id() == null) {
            return manageService.recordPlan(planRequest);
        } else {
            return manageService.updatePlan(planRequest);
        }
    }

    // 작업장 계획 조회 (성공)
    @GetMapping("manage/workplace/plan")
    public ManageWorkplacePlanResponse searchPlan(HttpServletRequest request) {
//        Plan plan = manageService.findPlan(p_id);
//        ManageWorkplacePlanResponse manageWorkplacePlanResponse = new ManageWorkplacePlanResponse();
//        manageWorkplacePlanResponse.match(plan);
//        return manageWorkplacePlanResponse;

        Plan plan = planRepository.findAll();
        return new ManageWorkplacePlanResponse(plan.getId(), plan.getP_search(), plan.getP_searchw(), plan.getP_list(), plan.getP_listw(), plan.getP_export(),
                plan.getP_exportw(), plan.getP_classify(), plan.getP_classifyw(), plan.getP_classifych(), plan.getP_classifychw(), plan.getP_page(),
                plan.getP_pagew(), plan.getP_pagech(), plan.getP_pagechw(), plan.getP_scan(), plan.getP_scanw(), plan.getP_scanpage(), plan.getP_scanpagew(),
                plan.getP_image(), plan.getP_imagew(), plan.getP_imagech(), plan.getP_imagechw(), plan.getP_index(), plan.getP_indexw(), plan.getP_indexch(),
                plan.getP_indexchw(), plan.getP_loading(), plan.getP_loadingw(), plan.getP_reorg(), plan.getP_reorgw(), plan.getP_boxreorg(), plan.getP_boxreorgw(),
                plan.getP_place(), plan.getP_placew(), plan.getP_etc(), plan.getP_etcw());
    }

    // 등록된 과 리스트
    @GetMapping("manage/workplace/plan/officeList")
    public ManageWorkplaceOfficeListResponse searchOffice(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<Office> allOffice = officeRepository.findAll();
        List<Files> allFile = fileRepository.findAll();
        int[] tot;
        tot = new int[allOffice.size()];
        String[] office;
        office = new String[allOffice.size()];
        String[] o_code;
        o_code = new String[allOffice.size()];
        int[] db;
        db = new int[allOffice.size()];
        int[] scan;
        scan = new int[allOffice.size()];


        for(int i = 0; i < allOffice.size(); i++) {
            office[i] = allOffice.get(i).getO_name();
            o_code[i] = allOffice.get(i).getO_code();
            for(int j =0; j < allFile.size(); j++) {
//                if(allFile.get(j).getF_db().equals(F_construct.YES)) {
//                    db[i] += Integer.valueOf(allFile.get(j).getF_volumeamount());
//                }
//                if(allFile.get(j).getF_scan().equals(F_construct.YES)) {
//                    scan[i] += Integer.valueOf(allFile.get(j).getF_volumeamount());
//                }
                if(allOffice.get(i).getO_code() == allFile.get(j).getOffice().getO_code()) {
                    tot[i] += Integer.valueOf(allFile.get(j).getF_volumeamount());
                }
            }
        }

        for(int i = 0; i < allOffice.size(); i++) {
            for(int j = 0; j< allFile.size(); j++) {
                if(allOffice.get(i).getO_code() == allFile.get(j).getOffice().getO_code()) {
                    if(allFile.get(j).getF_db().equals(F_construct.YES)) {
                        db[i] += Integer.valueOf(allFile.get(j).getF_volumeamount());
                    }
                    if(allFile.get(j).getF_scan().equals(F_construct.YES)) {
                        scan[i] += Integer.valueOf(allFile.get(j).getF_volumeamount());
                    }
                }
            }
        }

        return new ManageWorkplaceOfficeListResponse(tot, office, allOffice.size(), db, scan, o_code);
    }

    //과별 공정상황
    @GetMapping("manage/workplace/plan/officeProcess")
    public ManageWorkplaceOfficeProcessResponse processOffice(HttpServletRequest request, @RequestParam String ocode) {
        HttpSession session = request.getSession();
        int[] process;
        process = new int[3];

        Office office = officeRepository.findOne(ocode);

        for(int i = 0; i < office.getFileList().size(); i++) {
            if(office.getFileList().get(i).getF_process().equals(F_process.CHECK)) {
                process[0] += Integer.valueOf(office.getFileList().get(i).getF_volumeamount());
            } else if(office.getFileList().get(i).getF_process().equals(F_process.LOADING)) {
                process[0] += Integer.valueOf(office.getFileList().get(i).getF_volumeamount());
                process[1] += Integer.valueOf(office.getFileList().get(i).getF_volumeamount());
            } else if(office.getFileList().get(i).getF_process().equals(F_process.UPLOAD)) {
                process[0] += Integer.valueOf(office.getFileList().get(i).getF_volumeamount());
                process[1] += Integer.valueOf(office.getFileList().get(i).getF_volumeamount());
            } else if(office.getFileList().get(i).getF_process().equals(F_process.UPLOADED)) {
                process[0] += Integer.valueOf(office.getFileList().get(i).getF_volumeamount());
                process[1] += Integer.valueOf(office.getFileList().get(i).getF_volumeamount());
                process[2] += Integer.valueOf(office.getFileList().get(i).getF_volumeamount());
            }
        }

        return new ManageWorkplaceOfficeProcessResponse(process);
    }





    // ===== 일일 작업 ===== //

    // 작업자별 일일 작업량 저장 (성공)
    @PostMapping("manage/workplace/day")
    public ManageWorkplaceDailyResponse saveDailyWork(HttpServletRequest request, @RequestBody ManageWorkplaceDailyRequest dailyRequest) {
        HttpSession session = request.getSession();
        return manageService.recordDailyWork(dailyRequest);
    }

    // 날짜별 일일 작업량 조회 (성공)
    @GetMapping("manage/workplace/search/day")
    public Result searchWorkByDay(@RequestParam String date, HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<WorkerManagement> dailyWorkByDate = manageService.findDailyWorkByDate(date);
        List<WorkerManageDTO> collect = dailyWorkByDate.stream()
                .map(workerManagement -> new WorkerManageDTO(workerManagement.getWorker().getW_name(), workerManagement.getM_date(),
                                                            workerManagement.getM_attend(), workerManagement.getM_night(), workerManagement.getM_late(),
                                                            workerManagement.getM_leave(), workerManagement.getM_out(), workerManagement.getM_process(),
                                                            workerManagement.getM_amount(), workerManagement.getM_etc()))
                .collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }
    @Data
    @AllArgsConstructor
    static class WorkerManageDTO {
        private String w_name;      // 작업자 이름
        private String m_date;      // 작업 날짜
        private int m_attend;    // 출근
        private int m_night;     // 야근
        private int m_late;      // 지각
        private int m_leave;     // 조퇴
        private int m_out;       // 외출
//        private String MD;          // MD????
        private int m_process;
        private Long m_amount;
        private String m_etc;
    }

    // ===== 기간별 공정 ===== //
    // 기간별 공정 조회 (성공)
    @GetMapping("manage/workplace/search/period")    // ?sdate=8&edate=9  => sdate = 8, edate = 9
    public ManageWorkplacePeriodSearchDateResponse searchProcessByPeriod(@RequestParam String sdate, @RequestParam String edate, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int[] processByPeriod = manageService.findProcessByPeriod(sdate, edate);

        return new ManageWorkplacePeriodSearchDateResponse(processByPeriod[1], processByPeriod[2], processByPeriod[3], processByPeriod[4],
                processByPeriod[5], processByPeriod[6], processByPeriod[7], processByPeriod[8],
                processByPeriod[9], processByPeriod[10], processByPeriod[11], processByPeriod[12],
                processByPeriod[13], processByPeriod[14], processByPeriod[15], processByPeriod[16],
                processByPeriod[17], processByPeriod[18]);
    }

    // 전체 공정 조회 (기간 상관 없이) (성공)
    @GetMapping("manage/workplace/search")
    public ManageWorkplacePeriodSearchDateResponse searchProcessAll(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int[] processAll = manageService.findProcessAll();

        return new ManageWorkplacePeriodSearchDateResponse(processAll[1], processAll[2], processAll[3], processAll[4], processAll[5],
                processAll[6], processAll[7], processAll[8], processAll[9],
                processAll[10], processAll[11], processAll[12], processAll[13], processAll[14],
                processAll[15], processAll[16], processAll[17], processAll[18]);
    }

    // 공정별 인원 조회 (기관 상관 없이)
    @GetMapping("manage/workplace/search/member")
    public ManageWorkplacePeriodSearchDateResponse searchNumberAll(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int[] memberAll = manageService.findMemberAll();

        return new ManageWorkplacePeriodSearchDateResponse(memberAll[1], memberAll[2], memberAll[3], memberAll[4], memberAll[5],
                memberAll[6], memberAll[7], memberAll[8], memberAll[9], memberAll[10], memberAll[11], memberAll[12], memberAll[13],
                memberAll[14], memberAll[15], memberAll[16], memberAll[17], memberAll[18]);
    }


    // ===== 철별 이력 조회 ===== //
    // 이게 씨ㅡㅡㅡ발 맞냐공,,, ㅋ;;

    // workerput과 workercheck에서 오류남 ㅅㅂㅅㅂㅅㅂㅅㅂ
    @GetMapping("manage/workplace/record")
    public Result searchHistoryByFile(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
//            Object worker = session.getAttribute("worker");
//            System.out.println("worker.toString() = " + worker.toString());
            List<Files> historyByLabel = manageService.findByLabel();
            List<HistoryDTO> collect = historyByLabel.stream()
                    .map(files -> new HistoryDTO(files.getF_labelcode(), files.getOffice().getO_name(), files.getF_name(), files.getF_complete(), files.getF_check()
//                        , files.getCases().get(files.getCases().size()).getWorkerput().getW_name(), files.getCases().get(files.getCases().size()).getWorkercheck().getW_name()
                    ))
                    .collect(Collectors.toList());

            return new Result(collect.size(), collect);
        }
        catch (Exception e){
            System.out.println("e = " + e);
            return new Result(1, new ArrayList<>());
        }
    }
    @Data
    @AllArgsConstructor
    static class HistoryDTO {
        private String f_labelcode;     // 레이블 번호
        private String o_name;          // 생산기관명
        private String f_name;          // 철 제목
        private String f_complete;      // 색인 입력 날짜
        private String f_check;         // 색인 검수 날짜
//        private String complete_list;   // 색인 입력을 작업한 작업자 이름 (w_idput 활용)  workerput
//        private String check_list;      // 색인 검수를 작업한 작업자 이름 (w_idcheck 활용)  workercheck
    }
}
