package fis_solution.PMproject.domain.management;

import fis_solution.PMproject.controller.dto.ManageWorkplaceDailyRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

// @Table 사용해서

/*
 * 수정자: 한명수
 * 수정날짜: 2021/08/26
 * 수정내용: @Setter 추가
 */
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "M")
public class WorkerManagement {
    @Id @GeneratedValue
    @Column(name = "m_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "w_id")
    private Worker worker;

    private String m_date;

    private int m_attend;

    private int m_night;

    private int m_late;

    private int m_leave;

    private int m_out;

    private int m_process;

    private Long m_amount;

    @Column(length = 500)
    private String m_etc;


    /**
     * 작성자: 고준영
     * 작성날짜: 2021/0825
     * 작성내용: 연관관계 메서드, 수정
     */
    //== 연관 관계 메서드 ==//
    // 작업자(worker)와 일일 작업(workermanagement) 엔티티 연결
//    public void setWorker(Worker worker) {
//        this.worker = worker;
//        worker.getWorkerManagements().add(this);
//    }

    public void updateManagement(String date, int attend, int late, int leave, int out, int night, int process, Long amount, String etc) {
        this.m_date = date;
        this.m_attend = attend;
        this.m_late = late;
        this.m_leave = leave;
        this.m_out = out;
        this.m_night = night;
        this.m_process = process;
        this.m_amount = amount;
        this.m_etc = etc;
    }

    public WorkerManagement(Worker worker, String m_date, int m_attend, int m_night, int m_late, int m_leave, int m_out, int m_process, Long m_amount, String m_etc) {
        this.worker = worker;
        this.m_date = m_date;
        this.m_attend = m_attend;
        this.m_night = m_night;
        this.m_late = m_late;
        this.m_leave = m_leave;
        this.m_out = m_out;
        this.m_process = m_process;
        this.m_amount = m_amount;
        this.m_etc = m_etc;
    }

    public static WorkerManagement createWorkerManagement(ManageWorkplaceDailyRequest request, Worker worker) {
        WorkerManagement workerManagement = new WorkerManagement(worker, request.getM_date(), request.getM_attend(),
                request.getM_night(), request.getM_late(), request.getM_leave(), request.getM_out(),
                request.getM_process(), request.getM_amount(), request.getM_etc());
        return workerManagement;

    }
}
