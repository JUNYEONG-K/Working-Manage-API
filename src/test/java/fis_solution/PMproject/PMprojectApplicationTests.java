package fis_solution.PMproject;

import com.querydsl.jpa.impl.JPAQueryFactory;
import fis_solution.PMproject.domain.Office;
import fis_solution.PMproject.domain.management.Worker;
import fis_solution.PMproject.domain.record.Files;
import fis_solution.PMproject.domain.record.QFiles;
import fis_solution.PMproject.domain.record.fileEnum.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


@SpringBootTest
@Transactional
class PMprojectApplicationTests {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @Test
    void contextLoads() {
        Files files = createFile("000003", "002", F_process.CHECK, "3", "003", "경기도 안양시", "1990", F_kperiod.PERMANENT, F_kmethod.ORIGINAL, F_kplace.ARCHIVIST, "1990", "1995", F_construct.YES, F_construct.NO, "99999999", F_type.CARD, F_newold.OLD, F_modify.MODIFY, "00666", "984", "0", F_inheritance.NONE, "1998-03-29-09:00", "0", "0", "0");
        em.persist(files);

        queryFactory = new JPAQueryFactory(em);
        QFiles qFiles = QFiles.files;

        Files result = queryFactory
                .selectFrom(QFiles.files)
                .where(QFiles.files.f_labelcode.eq("000003"))
                .fetchOne();

        Assertions.assertThat(result).isEqualTo(files);
        Assertions.assertThat(result.getF_id()).isEqualTo(files.getF_id());
    }

    private Files createFile(String f_labelcode, String b_num, F_process f_process, String f_volumecount, String f_volumeamount, String f_name, String f_pyear, F_kperiod f_kperiod, F_kmethod f_kmethod, F_kplace f_kplace, String f_syear, String f_eyear, F_construct f_db, F_construct f_scan, String f_unitcode, F_type f_type, F_newold f_newold, F_modify f_modify, String f_regnum, String f_page, String f_efilenum, F_inheritance f_inheritance, String f_exportdate, String f_complete, String f_check, String f_upload) {
        Files file = new Files();
        file.setF_labelcode(f_labelcode);
        file.setB_num(b_num);
        file.setF_process(f_process);
        file.setF_volumecount(f_volumecount);
        file.setF_volumeamount(f_volumeamount);
        file.setF_name(f_name);
        file.setF_pyear(f_pyear);
        file.setF_kperiod(f_kperiod);
        file.setF_kmethod(f_kmethod);
        file.setF_kplace(f_kplace);
        file.setF_syear(f_syear);
        file.setF_eyear(f_eyear);
        file.setF_db(f_db);
        file.setF_scan(f_scan);
        file.setF_unitcode(f_unitcode);
        file.setF_type(f_type);
        file.setF_newold(f_newold);
        file.setF_modify(f_modify);
        file.setF_regnum(f_regnum);
        file.setF_page(f_page);
        file.setF_efilenum(f_efilenum);
        file.setF_inheritance(f_inheritance);
        file.setF_exportdate(f_exportdate);
        file.setF_complete(f_complete);
        file.setF_check(f_check);
        file.setF_upload(f_upload);
        return file;
    }

}
