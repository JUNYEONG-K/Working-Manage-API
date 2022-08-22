package fis_solution.PMproject.ManagerServiceTest;

import fis_solution.PMproject.controller.dto.ManageWorkerRequest;
import fis_solution.PMproject.controller.dto.ManageWorkerResponse;
import fis_solution.PMproject.controller.dto.ManageWorkplacePlanRequest;
import fis_solution.PMproject.controller.dto.ManageWorkplacePlanResponse;
import fis_solution.PMproject.domain.Office;
import fis_solution.PMproject.domain.management.Authority;
import fis_solution.PMproject.domain.management.Plan;
import fis_solution.PMproject.domain.management.Worker;
import fis_solution.PMproject.domain.management.WorkerManagement;
import fis_solution.PMproject.domain.record.Files;
import fis_solution.PMproject.domain.record.fileEnum.F_construct;
import fis_solution.PMproject.repository.FileRepository;
import fis_solution.PMproject.repository.OfficeRepository;
import fis_solution.PMproject.service.ManageService;
import org.apache.tomcat.jni.File;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
public class ManagerServiceTest {

    @Autowired
    ManageService manageService;

    @Autowired
    EntityManager em;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    OfficeRepository officeRepository;

    @Test
    public void 구축여부() {
        List<Files> allfile = fileRepository.findAll();
        F_construct f_db = allfile.get(0).getF_db();
        Assertions.assertThat(f_db).isEqualTo("0");
    }

    @Test
    public void 공정상태() {
        Office office = officeRepository.findOne("0000001");

    }

    @Test
    public void 계획저장() {
        ManageWorkplacePlanRequest request = new ManageWorkplacePlanRequest();
        request.setP_boxreorg("50");
        request.setP_searchw("30");

        System.out.println("request.getP_boxreorg() = " + request.getP_boxreorg());
        System.out.println("request.getP_searchw() = " + request.getP_searchw());


        Assertions.assertThat(request.getP_boxreorg()).isEqualTo("50");

        ManageWorkplacePlanResponse manageWorkplacePlanResponse = manageService.recordPlan(request);
        manageWorkplacePlanResponse.setP_searchw(request.getP_searchw());
        manageWorkplacePlanResponse.setP_boxreorg(request.getP_boxreorg());

        System.out.println("manage = " + manageWorkplacePlanResponse.getP_boxreorg());
        System.out.println("manageWorkplacePlanResponse = " + manageWorkplacePlanResponse.getP_searchw());

        Assertions.assertThat(manageWorkplacePlanResponse.getP_boxreorg()).isEqualTo("50");
    }

    @Test
    public void 과리스트() {
//        List<Office> officewithFile = manageService.findOfficewithFile();
////
//        String f_volumecount = officewithFile.get(0).getFileList().get(0).getF_volumecount();
//        String f_volumecount1 = officewithFile.get(0).getFileList().get(1).getF_volumecount();
//        String f_volumecount2 = officewithFile.get(1).getFileList().get(0).getF_volumecount();
//        String f_volumecount5 = officewithFile.get(1).getFileList().get(1).getF_volumecount();
//        String f_volumecount3 = officewithFile.get(4).getFileList().get(0).getF_volumecount();
//        String f_volumecount4 = officewithFile.get(4).getFileList().get(1).getF_volumecount();
//        Assertions.assertThat(f_volumecount1).isEqualTo("3");
//        Assertions.assertThat(f_volumecount2).isEqualTo("5");
//        Assertions.assertThat(f_volumecount3).isEqualTo("5");
//        Assertions.assertThat(f_volumecount4).isEqualTo("3");
////        Assertions.assertThat(f_volumecount5).isEqualTo("16"); 답 3
//        int integer = Integer.valueOf(f_volumecount);
//        Assertions.assertThat(integer).isEqualTo(5);
//
//        int size = officewithFile.get(0).getFileList().size();
//        int size1 = officewithFile.get(1).getFileList().size();
//        int size2 = officewithFile.get(2).getFileList().size();
//        int size3 = officewithFile.get(3).getFileList().size();
//        int size4 = officewithFile.get(4).getFileList().size();
//        Assertions.assertThat(size).isEqualTo(2);
////        Assertions.assertThat(size1).isEqualTo(1);    답 2
////        Assertions.assertThat(size2).isEqualTo(0);    답 1
////        Assertions.assertThat(size3).isEqualTo(0);    답 2
//        Assertions.assertThat(size4).isEqualTo(2);
//        Long f_id1_1 = officewithFile.get(0).getFileList().get(0).getF_id();
//        Long f_id3_1 = officewithFile.get(2).getFileList().get(0).getF_id();
//        Long f_id2_1 = officewithFile.get(1).getFileList().get(0).getF_id();
//
//
//
//        System.out.println("f_id1_1 = " + f_id1_1);
//        System.out.println("f_id2_1 = " + f_id2_1);
//        System.out.println("f_id3_1 = " + f_id3_1);

        List<Files> allWithOffice = fileRepository.findAllWithOffice();
        int size = allWithOffice.size();
        Assertions.assertThat(size).isEqualTo(5);
        String office1 = allWithOffice.get(0).getOffice().getO_code();
        String office2 = allWithOffice.get(1).getOffice().getO_code();
        String office3 = allWithOffice.get(2).getOffice().getO_code();
        String office4 = allWithOffice.get(3).getOffice().getO_code();
        String office5 = allWithOffice.get(4).getOffice().getO_code();
        Assertions.assertThat(office1).isEqualTo("1234567");
        Assertions.assertThat(office2).isEqualTo("1234567");
        Assertions.assertThat(office3).isEqualTo("5555444");
        Assertions.assertThat(office4).isEqualTo("0000001");
        Assertions.assertThat(office5).isEqualTo("0000001");

        List<Files> all = fileRepository.findAll();
        String office_1 = all.get(0).getOffice().getO_code();
        Assertions.assertThat(office_1).isEqualTo("1234567");


    }
}
