package fis_solution.PMproject.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import fis_solution.PMproject.controller.dto.CasesInfo;
import fis_solution.PMproject.controller.dto.QCasesInfo;
import fis_solution.PMproject.domain.record.Cases;
import fis_solution.PMproject.domain.record.Files;
import fis_solution.PMproject.domain.record.QCases;
import fis_solution.PMproject.domain.record.QFiles;
import fis_solution.PMproject.repository.querydslMethod.CasesQueryMethod;
import fis_solution.PMproject.repository.search.FindIndexCaseInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static fis_solution.PMproject.domain.record.QCases.cases;
import static fis_solution.PMproject.domain.record.QFiles.files;


/**
 * 작성자: 고준영
 * 작성날짜: 2021/08/23
 * 작성내용: save, findOne, remove
 */

@Repository
@RequiredArgsConstructor
public class CasesRepository extends CasesQueryMethod {

    private final EntityManager em;

    private final JPAQueryFactory jpaQueryFactory;

    QCases qCases = cases;

    public void save(Cases cases) {
        em.persist(cases);
    }

    public Cases findOne(Long id) {
        return em.find(Cases.class, id);
    }

    public Long remove(Cases cases) {

        Long id = cases.getId();
        em.remove(cases);
        return id;
    }

    public List<Cases> findByOldNumTitleReceiver(String c_oldnum, String c_title, String c_receiver) {
        // 동적쿼리를 이용한 or 조건은 BooleanBuilder로만 가능
        BooleanBuilder builder = new BooleanBuilder();
        if(c_oldnum!=null){
            builder.or(cOldNumLike(c_oldnum));
        }
        if (c_title != null) {
            builder.or(cTitleLike(c_title));
        }
        if (c_receiver != null) {
            builder.or(cReceiverLike(c_receiver));
        }
        return jpaQueryFactory
                .selectFrom(cases)
                .join(cases.files, files).fetchJoin()
                .where(builder)
                .fetch();
    }

    public List<Cases> findByFiles(Files files) {
        return jpaQueryFactory
                .selectFrom(qCases)
                .where(qCases.files.eq(files))
                .fetch();
    }

    public void deleteRemainCases(List<Long> deletedCasesIdList) {
        em.createQuery("delete from Cases c where c.id in :deletedCasesIdList")
                .setParameter("deletedCasesIdList", deletedCasesIdList)
                .executeUpdate();
        em.flush();
        em.clear();
    }

    public Cases findOneWithFilesVolume(Long casesId) {
         return em.createQuery(
                "select c " +
                        "from Cases c " +
                        "join fetch c.volume v " +
                        "join fetch c.files f " +
                        "where c.id = :casesId", Cases.class)
                .setParameter("casesId", casesId)
                .getSingleResult();
    }

    public Map<Long, List<CasesInfo>> findCasesMap(List<Long> volumeIds) {
        List<CasesInfo> casesInfoList = jpaQueryFactory
                .select(new QCasesInfo(cases.volume.id, cases.id, cases.c_spage, cases.c_epage, cases.c_page,
                        cases.c_class, cases.c_dodate, cases.c_pdate, cases.c_departmentname,
                        cases.c_oldnum, cases.c_kperiod, cases.c_title, cases.c_drafter, cases.c_approver,
                        cases.c_receiver, cases.c_edoc, cases.c_openable, cases.c_hidden))
                .from(cases)
                .where(cases.volume.id.in(volumeIds))
                .fetch();
        return casesInfoList.stream().collect(Collectors.groupingBy(casesInfo->casesInfo.getV_id()));
    }

    public List<Cases> findByVolume(Long v_id) {
        return em.createQuery("select c from Cases c where c.volume.id = :v_id", Cases.class)
                .setParameter("v_id", v_id)
                .getResultList();
    }
}
