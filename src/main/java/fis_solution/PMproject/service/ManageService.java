package fis_solution.PMproject.service;

import fis_solution.PMproject.controller.dto.*;
import fis_solution.PMproject.domain.Office;
import fis_solution.PMproject.domain.management.Authority;
import fis_solution.PMproject.domain.management.Plan;
import fis_solution.PMproject.domain.management.Worker;
import fis_solution.PMproject.domain.management.WorkerManagement;
import fis_solution.PMproject.domain.record.Files;
import fis_solution.PMproject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ManageService {

    private final WorkerRepository workerRepository;
    private final ManageRepository manageRepository;
    private final PlanRepository planRepository;
    private final FileRepository fileRepository;
    private final OfficeRepository officeRepository;

    // 작업자 중복 검사 후 등록
    @Transactional
    public Long join(Worker worker) {
        validateDuplicateWorker(worker); // 중복 작업자 검증
        workerRepository.save(worker);   // 작업자 등록
        return worker.getId();
    }
    @Transactional
    public ManageWorkerResponse saveWorker(ManageWorkerRequest request) {
        Worker worker = Worker.createWorker(request);
        validateDuplicateWorker(worker);
        workerRepository.save(worker);
        ManageWorkerResponse manageWorkerResponse = new ManageWorkerResponse();
        manageWorkerResponse.setW_id(worker.getId());
        return manageWorkerResponse;
    }

    // 작업자 정보 수정
    @Transactional
    public ManageWorkerResponse updateWorker(ManageWorkerRequest request) {
        Worker findWorker = findWorker(request.getW_id());
        findWorker.updateWorker(request.getW_authority(), request.getW_name(), request.getW_ssn(), request.getW_address(), request.getW_tel(), request.getW_bank(), request.getW_account(), request.getW_sdate(), request.getW_edate());
        ManageWorkerResponse manageWorkerResponse = new ManageWorkerResponse();
        manageWorkerResponse.setW_id(findWorker.getId());
        return manageWorkerResponse;
    }

    // 작업자 조회 (한 명)
    public Worker findWorker(Long workerId) {
        return workerRepository.findOne(workerId);
    }

    // 작업자 전체 조회
    public List<Worker> findWorkers() {
        return workerRepository.findAll();
    }
    // 작업자 정보 삭제
    @Transactional
    public ManageWorkerDelResponse removeWorker(Long w_id) {
        Worker findWorker = findWorker(w_id);
        workerRepository.remove(findWorker);
        ManageWorkerDelResponse manageWorkerDelResponse = new ManageWorkerDelResponse();
        manageWorkerDelResponse.setW_id(w_id);
        return manageWorkerDelResponse;
    }

    // 작업자 주민번호를 바탕으로 중복검사 (같은 주민번호의 작업자를 등록하지 않기 위해)
    private void validateDuplicateWorker(Worker worker) {
        List<Worker> findWorkers = workerRepository.findBySsn(worker.getW_ssn());
        if(!findWorkers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 작업자입니다.");
        }
    }

    // 작업장 계획 저장
    @Transactional
    public ManageWorkplacePlanResponse recordPlan(ManageWorkplacePlanRequest request) {
        Plan plan = Plan.createPlan(request);
        planRepository.save(plan);
        ManageWorkplacePlanResponse manageWorkplacePlanResponse = new ManageWorkplacePlanResponse();
        manageWorkplacePlanResponse.match(plan);
        return manageWorkplacePlanResponse;
    }
    // 작업장 계획 수정
    @Transactional
    public ManageWorkplacePlanResponse updatePlan(ManageWorkplacePlanRequest request) {
        Plan plan = findPlan(request.getP_id());
        plan.updatePlan(request);
        ManageWorkplacePlanResponse manageWorkplacePlanResponse = new ManageWorkplacePlanResponse();
        manageWorkplacePlanResponse.match(plan);
        return manageWorkplacePlanResponse;
    }
    // 작업장 계획 조회 (한개)
    public Plan findPlan(Long id) {
        return planRepository.findOne(id);
    }

    // 작업장 계획 전체 조회
//    public List<Plan> findPlans() {
//        return planRepository.findAll();
//    }

    // 일일 작업 저장
    @Transactional
    public ManageWorkplaceDailyResponse recordDailyWork(ManageWorkplaceDailyRequest request) {
        Worker worker = findWorker(request.getW_id());
        WorkerManagement workerManagement = WorkerManagement.createWorkerManagement(request, worker);
        validateDuplicateWorker(workerManagement);
        manageRepository.save(workerManagement);
        ManageWorkplaceDailyResponse manageWorkplaceDailyResponse = new ManageWorkplaceDailyResponse();
        manageWorkplaceDailyResponse.setId(worker.getId());
        manageWorkplaceDailyResponse.setName(workerManagement.getWorker().getW_name());
        return manageWorkplaceDailyResponse;
    }

    // 일일 작업 조회 (날짜별)
    public List<WorkerManagement> findDailyWorkByDate(String date) {
        List<WorkerManagement> result = manageRepository.findByDate(date);
        return result;
    }

    // 일일 작업 조회 (아이디로)
    public WorkerManagement findDailyWorkById(Long m_id) {
        return manageRepository.findOne(m_id);
    }

    // 일일 작업 수정
    @Transactional
    public void updateDailyWork(Long id, String date, int attend, int late, int leave, int out, int night, int process, Long amount, String etc) {
        WorkerManagement dailyWorkById = findDailyWorkById(id);
        dailyWorkById.updateManagement(date,attend, late, leave, out, night, process, amount, etc);
    }
    // 작업자 주민번호를 바탕으로 중복검사 (같은 주민번호의 작업자에 작업량을 중복으로 저장하지 않기 위해)
    private void validateDuplicateWorker(WorkerManagement workerManagement) {
        List<WorkerManagement> findWorkers = manageRepository.findByWorkerWithSsn(workerManagement.getWorker().getW_ssn(), workerManagement.getM_date());
        if(!findWorkers.isEmpty()) {
            throw new IllegalStateException("이 작업자의 작업이 이미 존재합니다.");
        }
    }

    public int[] findMemberAll() {
        int[] allMember;
        allMember = new int [19];

        List<WorkerManagement> all = manageRepository.findAll();

        for (WorkerManagement wm : all) {
            switch (wm.getM_process()) {
                case 1: allMember[1]++;
                    break;
                case 2: allMember[2]++;
                    break;
                case 3: allMember[3]++;
                    break;
                case 4: allMember[4]++;
                    break;
                case 5: allMember[5]++;
                    break;
                case 6: allMember[6]++;
                    break;
                case 7: allMember[7]++;
                    break;
                case 8: allMember[8]++;
                    break;
                case 9: allMember[9]++;
                    break;
                case 10: allMember[10]++;
                    break;
                case 11: allMember[11]++;
                    break;
                case 12: allMember[12]++;
                    break;
                case 13: allMember[13]++;
                    break;
                case 14: allMember[14]++;
                    break;
                case 15: allMember[15]++;
                    break;
                case 16: allMember[16]++;
                    break;
                case 17: allMember[17]++;
                    break;
                case 18: allMember[18]++;
                    break;
            }
        }
        return allMember;
    }
    // 전체 공정 작업량 조회
    public int[] findProcessAll() {
        int[] allProcess;
        allProcess = new int[19];

        List<WorkerManagement> all = manageRepository.findAll();

        for (WorkerManagement wm : all) {
            switch (wm.getM_process()) {
                case 1: allProcess[1] += wm.getM_amount();
                    break;
                case 2: allProcess[2] += wm.getM_amount();
                    break;
                case 3: allProcess[3] += wm.getM_amount();
                    break;
                case 4: allProcess[4] += wm.getM_amount();
                    break;
                case 5: allProcess[5] += wm.getM_amount();
                    break;
                case 6: allProcess[6] += wm.getM_amount();
                    break;
                case 7: allProcess[7] += wm.getM_amount();
                    break;
                case 8: allProcess[8] += wm.getM_amount();
                    break;
                case 9: allProcess[9] += wm.getM_amount();
                    break;
                case 10: allProcess[10] += wm.getM_amount();
                    break;
                case 11: allProcess[11] += wm.getM_amount();
                    break;
                case 12: allProcess[12] += wm.getM_amount();
                    break;
                case 13: allProcess[13] += wm.getM_amount();
                    break;
                case 14: allProcess[14] += wm.getM_amount();
                    break;
                case 15: allProcess[15] += wm.getM_amount();
                    break;
                case 16: allProcess[16] += wm.getM_amount();
                    break;
                case 17: allProcess[17] += wm.getM_amount();
                    break;
                case 18: allProcess[18] += wm.getM_amount();
                    break;
            }
        }
        return allProcess;
    }
    // 실적 조회 (기간별 공정 조회)
    public int[] findProcessByPeriod(String sdate, String edate) {
        int[] process; // 공정의 작업량을 담을 배열
        process = new int[19]; // 19의 크기를 가지고, 초기값 0으로 채워진 배열 생성

        List<WorkerManagement> result = manageRepository.findByPeriod(sdate, edate); // sdate부터 edate까지의 workermanage를 모두 가져옴

        // workermanage의 공정 값에 따라 작업량 추가? 이게 맞나 싶다,,,
        for (WorkerManagement workerManagement : result) {
            switch (workerManagement.getM_process()) {
                case 1: process[1] += workerManagement.getM_amount();
                    break;
                case 2: process[2] += workerManagement.getM_amount();
                    break;
                case 3: process[3] += workerManagement.getM_amount();
                    break;
                case 4: process[4] += workerManagement.getM_amount();
                    break;
                case 5: process[5] += workerManagement.getM_amount();
                    break;
                case 6: process[6] += workerManagement.getM_amount();
                    break;
                case 7: process[7] += workerManagement.getM_amount();
                    break;
                case 8: process[8] += workerManagement.getM_amount();
                    break;
                case 9: process[9] += workerManagement.getM_amount();
                    break;
                case 10: process[10] += workerManagement.getM_amount();
                    break;
                case 11: process[11] += workerManagement.getM_amount();
                    break;
                case 12: process[12] += workerManagement.getM_amount();
                    break;
                case 13: process[13] += workerManagement.getM_amount();
                    break;
                case 14: process[14] += workerManagement.getM_amount();
                    break;
                case 15: process[15] += workerManagement.getM_amount();
                    break;
                case 16: process[16] += workerManagement.getM_amount();
                    break;
                case 17: process[17] += workerManagement.getM_amount();
                    break;
                case 18: process[18] += workerManagement.getM_amount();
                    break;
            }
        }
        return process;
    }


    // 철별 이력 조회
    public List<Files> findByLabel() {
//        fileRepository.findRecordByLabel().get(0).getOffice().getO_name();      // 기관명
//        fileRepository.findRecordByLabel().get(0).getF_labelcode();        // 레이블 번호
//        fileRepository.findRecordByLabel().get(0).getF_name();                  // 철 제목
//        fileRepository.findRecordByLabel().get(0).getF_complete();          // 색인 입력 날짜
//        fileRepository.findRecordByLabel().get(0).getF_check();                // 색인 검수 날짜
//
//        List<Files> recordByLabel = fileRepository.findRecordByLabel();
//        String[] o_name = new String[recordByLabel.size()];
//        String[] f_labelcode = new String[recordByLabel.size()];
//        String[] f_name = new String[recordByLabel.size()];
//        String[] f_complete = new String[recordByLabel.size()];
//        String[] f_check = new String[recordByLabel.size()];
//
//        for(int i = 0; i < recordByLabel.size(); i++) {
//            o_name[i] = fileRepository.findRecordByLabel().get(0).getOffice().getO_name();
//            f_labelcode[i] = fileRepository.findRecordByLabel().get(0).getF_labelcode();
//            f_name[i] = fileRepository.findRecordByLabel().get(0).getF_name();
//            f_complete[i] = fileRepository.findRecordByLabel().get(0).getF_complete();
//            f_check[i] = fileRepository.findRecordByLabel().get(0).getF_check();
//        }

        return fileRepository.findRecordByLabel();
    }

    // 등록된 과 리스트
    public List<Office> findOfficewithFile() {
        List<Office> allwithFile = officeRepository.findAllwithFile();

        return allwithFile;
    }

}
