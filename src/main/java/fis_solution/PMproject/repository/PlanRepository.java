package fis_solution.PMproject.repository;

import fis_solution.PMproject.domain.management.Plan;
import fis_solution.PMproject.domain.management.Worker;
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
public class PlanRepository {
    private final EntityManager em;

    public void save(Plan plan){        //계획 등록
        em.persist(plan);
    }

    public Plan findOne(Long id){       //계획 하나 조회
        return em.find(Plan.class, id);
    }

    /**
     * 작성자: 고준영
     * 작성날짜: 2021/08/26
     * 작성내용: findAll
     */
    public Plan findAll() {
        return em.createQuery("select p from Plan p", Plan.class)
                .getSingleResult();
    }

    public void remove(Plan plan){      //계획 삭제
        em.remove(plan);
    }

}