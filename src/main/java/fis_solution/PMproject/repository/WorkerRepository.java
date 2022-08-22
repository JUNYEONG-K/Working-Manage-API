package fis_solution.PMproject.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import fis_solution.PMproject.domain.management.QWorker;
import fis_solution.PMproject.domain.management.Worker;
import fis_solution.PMproject.domain.record.QFiles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/*
* 작성자: 원보라
* 작성날짜: 2021/08/23
* 작성내용: save,findOne, remove
*/


@Repository
@RequiredArgsConstructor
public class WorkerRepository {
    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    public void save(Worker worker){        //작업자 등록
        em.persist(worker);
    }

    public Worker findOne(Long id){         //작업자 한명 조회
        return em.find(Worker.class, id);
    }

    /**
     * 작성자: 고준영
     * 작성날짜: 2021/08/25
     * 작성내용: findAll, findBySsn
     */
    public List<Worker> findAll() {
        return em.createQuery("select w from Worker w", Worker.class)
                .getResultList();
    }

    public List<Worker> findBySsn(String ssn) {
        return em.createQuery("select w from Worker w where w.w_ssn = :ssn", Worker.class)
                .setParameter("ssn", ssn)
                .getResultList();
    }


    public void remove(Worker worker){       //작업자 삭제
        em.remove(worker);
    }

    public Worker findnickname(String nickname){
        return em.createQuery("select w from Worker w where w.nickname = :nickname", Worker.class)
                .setParameter("nickname", nickname)
                .getSingleResult();
    }

    public Worker findBySessionId(String session_id){
        return em.createQuery("select w from Worker w where w.session_id = :session_id", Worker.class)
                .setParameter("session_id", session_id)
                .getSingleResult();
    }
}
