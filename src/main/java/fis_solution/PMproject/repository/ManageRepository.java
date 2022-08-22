package fis_solution.PMproject.repository;

import fis_solution.PMproject.domain.management.Worker;
import fis_solution.PMproject.domain.management.WorkerManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
/*
* 작성자: 한명수
* 작성날짜: 2021/08/23
* 작성내용: save, findOne, remove
* */


@Repository
@RequiredArgsConstructor
public class ManageRepository {

    private final EntityManager em;

    public void save(WorkerManagement workerManagement){
        em.persist(workerManagement);
    }

    public WorkerManagement findOne(Long id) {
        return em.find(WorkerManagement.class, id);
    }

    /**
     * 작성자: 고준영
     * 작성날짜: ~~~2021/08/31
     * 작성내용: findByWorker, findByWorkerWithSsn, findByDate, findByPeriod, findAll
     */
    public List<WorkerManagement> findByWorker(String ssn) {
        return em.createQuery("select wm from WorkerManagement wm where wm.worker.w_ssn = : ssn", WorkerManagement.class)
                .setParameter("ssn", ssn)
                .getResultList();
    }

    public List<WorkerManagement> findByWorkerWithSsn(String ssn, String date) {
        return em.createQuery("select wm from WorkerManagement wm join fetch wm.worker w where w.w_ssn = : ssn and wm.m_date = : date", WorkerManagement.class)
                .setParameter("ssn", ssn)
                .setParameter("date", date)
                .getResultList();
    }

    public List<WorkerManagement> findByDate(String date) {
        return em.createQuery("select wm from WorkerManagement wm join fetch wm.worker w where wm.m_date = : date", WorkerManagement.class)
                .setParameter("date", date)
                .getResultList();
    }

    public List<WorkerManagement> findByPeriod(String sdate, String edate) {
        return em.createQuery("select wm from WorkerManagement wm where wm.m_date >= : sdate and wm.m_date <= : edate", WorkerManagement.class)
                .setParameter("sdate", sdate)
                .setParameter("edate", edate)
                .getResultList();
    }

    public List<WorkerManagement> findAll() {
        return em.createQuery("select wm from WorkerManagement wm", WorkerManagement.class)
                .getResultList();
    }

    public void remove(WorkerManagement workerManagement) {
        em.remove(workerManagement);
    }
}
