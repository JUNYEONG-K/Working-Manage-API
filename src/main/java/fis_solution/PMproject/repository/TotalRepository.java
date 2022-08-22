package fis_solution.PMproject.repository;

import fis_solution.PMproject.domain.management.Total;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TotalRepository {

    final private EntityManager em;

    public Long save(Total total){
        em.persist(total);
        return total.getId();
    }

    public Total get(){
        List<Total> totalList = em.createQuery("select total from Total total" ,Total.class)
            .getResultList();

        return totalList.get(totalList.size() - 1);
    }
}
