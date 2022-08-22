package fis_solution.PMproject.domain.management;

import fis_solution.PMproject.controller.dto.ManageWorkerRequest;
import fis_solution.PMproject.domain.WorkList;
import fis_solution.PMproject.domain.record.Cases;
import fis_solution.PMproject.domain.record.Cases;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
/*
 * 수정자: 한명수
 * 수정날짜: 2021/08/26
 * 수정내용: @Setter 추가
 */
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Worker {
    @Id @GeneratedValue
    @Column(name = "w_id")
    private Long id;

    @OneToMany(mappedBy = "worker")
    private List<WorkList> workList = new ArrayList<>();

    public Worker(Authority authority, String password, String w_name, String w_ssn, String w_address, String w_tel, String w_bank, String w_account, String w_sdate, String w_edate) {
        this.authority = authority;
        this.password = password;
        this.w_name = w_name;
        this.w_ssn = w_ssn;
        this.w_address = w_address;
        this.w_tel = w_tel;
        this.w_bank = w_bank;
        this.w_account = w_account;
        this.w_sdate = w_sdate;
        this.w_edate = w_edate;
    }

    private String nickname;

    //작업자 권한
    @Enumerated(EnumType.STRING) @Column(name = "w_authority")
    private Authority authority;

    //작업자 패스워드
    @Column(name = "w_pwd") @NotBlank
    private String password;

    //작업자 이름
    @NotBlank
    private String w_name;

    private String session_id;

    //작업자 주민등록 번호
    private String w_ssn;

    //작업자 주소
    private String w_address;

    //작업자 전화번호
    private String w_tel;

    //작업자 은행 이름
    private String w_bank;

    //작업자 계좌번호
    private String w_account;

    //작업자 일 시작 날짜
    private String w_sdate;

    private String w_edate;

    @OneToMany(mappedBy = "worker")
    private List<WorkerManagement> workerManagements;

    @OneToMany(mappedBy = "workerput")
    private List<Cases> caseList_put = new ArrayList<>();

    @OneToMany(mappedBy = "workercheck")
    private List<Cases> caseList_check = new ArrayList<>();

    /**
     * 작성자: 고준영
     * 작성날짜: 2021/08/25
     * 작성내용: updateWorker
     */
    public void updateWorker(Authority authority, String w_name, String w_ssn, String w_address, String w_tel, String w_bank, String w_account, String w_sdate, String w_edate) {
        this.authority = authority;
        this.w_name = w_name;
        this.w_ssn = w_ssn;
        this.w_address = w_address;
        this.w_tel = w_tel;
        this.w_bank = w_bank;
        this.w_account = w_account;
        this.w_sdate = w_sdate;
        this.w_edate = w_edate;
    }

    public void updateSession(String session_id){
        this.session_id = session_id;
    }
  
    /**
     * 작성자: 고준영
     * 작성날짜: 2021/09/01
     * 작성내용: createWorker
     */
    public static Worker createWorker(ManageWorkerRequest request) {
        Worker worker = new Worker(request.getW_authority(), request.getW_ssn(), request.getW_name(), request.getW_ssn(), request.getW_address(),
                request.getW_tel(), request.getW_bank(), request.getW_account(), request.getW_sdate(), request.getW_edate());
        return worker;
    }
}
