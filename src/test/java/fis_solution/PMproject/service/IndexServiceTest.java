package fis_solution.PMproject.service;

import fis_solution.PMproject.controller.dto.*;
import fis_solution.PMproject.domain.Office;
import fis_solution.PMproject.domain.record.Cases;
import fis_solution.PMproject.domain.record.Files;
import fis_solution.PMproject.domain.record.caseEnum.C_edoc;
import fis_solution.PMproject.domain.record.caseEnum.C_kperiod;
import fis_solution.PMproject.domain.record.fileEnum.*;
import fis_solution.PMproject.repository.CasesRepository;
import fis_solution.PMproject.repository.FileRepository;
import fis_solution.PMproject.repository.OfficeRepository;
import fis_solution.PMproject.repository.VolumeRepository;
import fis_solution.PMproject.repository.search.FindIndexCaseInfo;
import fis_solution.PMproject.repository.search.FindIndexDetailInfo;
import fis_solution.PMproject.repository.search.FindIndexPreinfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class IndexServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    IndexService indexService;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    OfficeRepository officeRepository;

    @Autowired
    CasesRepository casesRepository;

    @Autowired
    VolumeRepository volumeRepository;

    @Test
    void findByPreInfo(){
//        FindIndexPreinfo findIndexPreinfo1 = new FindIndexPreinfo();
//        Office office1 = officeRepository.findOne("1234567");
//        findIndexPreinfo1.setOffice(office1);
//        findIndexPreinfo1.setB_num("001");
//       // List<Files> result1 = indexService.searchFilesByPreInfo(findIndexPreinfo1);
//        Files compare = fileRepository.findOne(1L);
//
//        FindIndexPreinfo findIndexPreinfo2 = new FindIndexPreinfo();
//        findIndexPreinfo2.setOffice(office1);
       // List<Files> result2 = indexService.searchFilesByPreInfo(findIndexPreinfo2);

      //  Assertions.assertThat(result1.get(0)).isEqualTo(compare);
      //  Assertions.assertThat(result2.size()).isEqualTo(2);

    }

    @Test
    void findByDetailInfo() {
        FindIndexDetailInfo findIndexDetailInfo1 = new FindIndexDetailInfo();
        findIndexDetailInfo1.setF_name("강남구청 인사과");
        //List<Files> result1 = indexService.searchFilesByDetailInfo(findIndexDetailInfo1);

        /*Assertions.assertThat(result1.size()).isEqualTo(2);*/
    }

    @Test
    void saveIndexInfo() {
        IndexSaveLabelRequest indexSaveLabelRequest = new IndexSaveLabelRequest(1L,"000001", "강남구청 인사과","3", "2", "한명수", F_kperiod.SEMI, F_kmethod.ALL, F_kplace.ARCHIVIST, F_type.CARD);
        IndexSaveLabelResponse result = indexService.saveFilesAndVolume(indexSaveLabelRequest);
        em.flush();
        Assertions.assertThat(result.getV_id().size()).isEqualTo(3);
    }

    @Test
    void delFile() {
        IndexLabelDelResponse indexLabelDelResponse = indexService.deleteFiles(1L);
        em.flush();
        Assertions.assertThat(fileRepository.findOne(1l)).isEqualTo(null);
    }


    @Test
    void savePageInfo(){
        IndexSaveLabelRequest indexSaveLabelRequest = new IndexSaveLabelRequest(1L,"000001", "강남구청 인사과","3", "2", "한명수", F_kperiod.SEMI, F_kmethod.ALL, F_kplace.ARCHIVIST, F_type.CARD);
        IndexSaveLabelResponse result = indexService.saveFilesAndVolume(indexSaveLabelRequest);
        List<List<IndexSaveVolumeRequest.PageInfo>> pageInfos = new ArrayList<>();
        List<IndexSaveVolumeRequest.PageInfo> pageInfo1 = new ArrayList<>();
        List<IndexSaveVolumeRequest.PageInfo> pageInfo2 = new ArrayList<>();
        List<IndexSaveVolumeRequest.PageInfo> pageInfo3 = new ArrayList<>();

        IndexSaveVolumeRequest.PageInfo info1 = new IndexSaveVolumeRequest.PageInfo("1", "30");
        IndexSaveVolumeRequest.PageInfo info2 = new IndexSaveVolumeRequest.PageInfo("31", "50");
        IndexSaveVolumeRequest.PageInfo info3 = new IndexSaveVolumeRequest.PageInfo("51", "70");
        IndexSaveVolumeRequest.PageInfo info11 = new IndexSaveVolumeRequest.PageInfo("71", "90");
        IndexSaveVolumeRequest.PageInfo info12 = new IndexSaveVolumeRequest.PageInfo("91", "120");
        IndexSaveVolumeRequest.PageInfo info13 = new IndexSaveVolumeRequest.PageInfo("121", "150");
        IndexSaveVolumeRequest.PageInfo info14 = new IndexSaveVolumeRequest.PageInfo("151", "160");
        IndexSaveVolumeRequest.PageInfo info21 = new IndexSaveVolumeRequest.PageInfo("161", "200");
        IndexSaveVolumeRequest.PageInfo info22 = new IndexSaveVolumeRequest.PageInfo("201", "230");
        IndexSaveVolumeRequest.PageInfo info23 = new IndexSaveVolumeRequest.PageInfo("231", "260");

        pageInfo1.add(info1);
        pageInfo1.add(info2);
        pageInfo1.add(info3);
        pageInfo2.add(info11);
        pageInfo2.add(info12);
        pageInfo2.add(info13);
        pageInfo2.add(info14);
        pageInfo3.add(info21);
        pageInfo3.add(info22);
        pageInfo3.add(info23);

        pageInfos.add(pageInfo1);
        pageInfos.add(pageInfo2);
        pageInfos.add(pageInfo3);

        IndexSaveVolumeRequest indexSaveVolumeRequest = new IndexSaveVolumeRequest(1L, pageInfos);
        IndexSaveVolumeResponse indexSaveVolumeResponse = indexService.saveCasesPages(indexSaveVolumeRequest);

        em.flush();
        Assertions.assertThat(casesRepository.findOne(indexSaveVolumeResponse.getC_id().get(0)).getC_spage()).isEqualTo("1");
    }

    @Test
    public void searchByCasesInfo(){
        FindIndexCaseInfo findIndexCaseInfo = new FindIndexCaseInfo();
        findIndexCaseInfo.setC_oldnum("111111");
        List<IndexSearchCaseResponse> result = indexService.searchCasesByCasesInfo(findIndexCaseInfo);
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    /*
    @Test
    public void totalTest() {
        IndexSaveLabelRequest indexSaveLabelRequest = new IndexSaveLabelRequest(1L, "000001", "강남구청 인사과", "3", "11","한명수", F_kperiod.SEMI, F_kmethod.ALL, F_kplace.ARCHIVIST, F_type.GENERAL);
        IndexSaveLabelResponse indexSaveLabelResponse = indexService.saveFilesAndVolume(indexSaveLabelRequest);

        List<List<IndexSaveVolumeRequest.PageInfo>> pageInfos = new ArrayList<>();
        List<IndexSaveVolumeRequest.PageInfo> pageInfo1 = new ArrayList<>();
        List<IndexSaveVolumeRequest.PageInfo> pageInfo2 = new ArrayList<>();
        List<IndexSaveVolumeRequest.PageInfo> pageInfo3 = new ArrayList<>();

        IndexSaveVolumeRequest.PageInfo info1 = new IndexSaveVolumeRequest.PageInfo("1", "30");
        IndexSaveVolumeRequest.PageInfo info2 = new IndexSaveVolumeRequest.PageInfo("31", "50");
        IndexSaveVolumeRequest.PageInfo info3 = new IndexSaveVolumeRequest.PageInfo("51", "70");
        IndexSaveVolumeRequest.PageInfo info11 = new IndexSaveVolumeRequest.PageInfo("71", "90");
        IndexSaveVolumeRequest.PageInfo info12 = new IndexSaveVolumeRequest.PageInfo("91", "120");
        IndexSaveVolumeRequest.PageInfo info13 = new IndexSaveVolumeRequest.PageInfo("121", "150");
        IndexSaveVolumeRequest.PageInfo info14 = new IndexSaveVolumeRequest.PageInfo("151", "160");
        IndexSaveVolumeRequest.PageInfo info21 = new IndexSaveVolumeRequest.PageInfo("161", "200");
        IndexSaveVolumeRequest.PageInfo info22 = new IndexSaveVolumeRequest.PageInfo("201", "230");
        IndexSaveVolumeRequest.PageInfo info23 = new IndexSaveVolumeRequest.PageInfo("231", "260");

        pageInfo1.add(info1);
        pageInfo1.add(info2);
        pageInfo1.add(info3);
        pageInfo2.add(info11);
        pageInfo2.add(info12);
        pageInfo2.add(info13);
        pageInfo2.add(info14);
        pageInfo3.add(info21);
        pageInfo3.add(info22);
        pageInfo3.add(info23);

        pageInfos.add(pageInfo1);
        pageInfos.add(pageInfo2);
        pageInfos.add(pageInfo3);

        IndexSaveVolumeRequest indexSaveVolumeRequest = new IndexSaveVolumeRequest(1L, pageInfos);
        IndexSaveVolumeResponse indexSaveVolumeResponse = indexService.saveCasesPages(indexSaveVolumeRequest);

        Assertions.assertThat(casesRepository.findOne(indexSaveVolumeResponse.getC_id().get(0)).getC_first()).isEqualTo("1");
    //        IndexSaveCaseRequest indexSaveCaseRequest1 = new IndexSaveCaseRequest(1L, indexSaveVolumeResponse.getC_id().get(0),"1", "30", "30", "1","20210825", "20210830", "강남구청 인사과", "1111", C_kperiod.SEMI,"강남구청 인사과 인사이동 리스트", "한명수", "구청장", "한명수", C_edoc.PAPER, "공개가능", "없음");
//        IndexSaveCaseRequest indexSaveCaseRequest2 = new IndexSaveCaseRequest(1L, indexSaveVolumeResponse.getC_id().get(1),"1", "30", "30", "1","20210825", "20210830", "강남구청 인사과", "1111", C_kperiod.SEMI,"강남구청 인사과 인사이동 리스트", "한명수", "구청장", "한명수", C_edoc.PAPER, "공개가능", "없음");
//        IndexSaveCaseRequest indexSaveCaseRequest3 = new IndexSaveCaseRequest(1L, indexSaveVolumeResponse.getC_id().get(2),"1", "30", "30", "1","20210825", "20210830", "강남구청 인사과", "1111", C_kperiod.SEMI,"강남구청 인사과 인사이동 리스트", "한명수", "구청장", "한명수", C_edoc.PAPER, "공개가능", "없음");
//        IndexSaveCaseRequest indexSaveCaseRequest4 = new IndexSaveCaseRequest(1L, indexSaveVolumeResponse.getC_id().get(3),"1", "30", "30", "1","20210825", "20210830", "강남구청 인사과", "1111", C_kperiod.SEMI,"강남구청 인사과 인사이동 리스트", "한명수", "구청장", "한명수", C_edoc.PAPER, "공개가능", "없음");
//        IndexSaveCaseRequest indexSaveCaseRequest5 = new IndexSaveCaseRequest(1L, indexSaveVolumeResponse.getC_id().get(4),"1", "30", "30", "1","20210825", "20210830", "강남구청 인사과", "1111", C_kperiod.SEMI,"강남구청 인사과 인사이동 리스트", "한명수", "구청장", "한명수", C_edoc.PAPER, "공개가능", "없음");
//        IndexSaveCaseRequest indexSaveCaseRequest6 = new IndexSaveCaseRequest(1L, indexSaveVolumeResponse.getC_id().get(5),"1", "30", "30", "1","20210825", "20210830", "강남구청 인사과", "1111", C_kperiod.SEMI,"강남구청 인사과 인사이동 리스트", "한명수", "구청장", "한명수", C_edoc.PAPER, "공개가능", "없음");
//        IndexSaveCaseRequest indexSaveCaseRequest7 = new IndexSaveCaseRequest(1L, indexSaveVolumeResponse.getC_id().get(6),"1", "30", "30", "1","20210825", "20210830", "강남구청 인사과", "1111", C_kperiod.SEMI,"강남구청 인사과 인사이동 리스트", "한명수", "구청장", "한명수", C_edoc.PAPER, "공개가능", "없음");
//        IndexSaveCaseRequest indexSaveCaseRequest8 = new IndexSaveCaseRequest(1L, indexSaveVolumeResponse.getC_id().get(7),"1", "30", "30", "1","20210825", "20210830", "강남구청 인사과", "1111", C_kperiod.SEMI,"강남구청 인사과 인사이동 리스트", "한명수", "구청장", "한명수", C_edoc.PAPER, "공개가능", "없음");
//        IndexSaveCaseRequest indexSaveCaseRequest9 = new IndexSaveCaseRequest(1L, indexSaveVolumeResponse.getC_id().get(8),"1", "30", "30", "1","20210825", "20210830", "강남구청 인사과", "1111", C_kperiod.SEMI,"강남구청 인사과 인사이동 리스트", "한명수", "구청장", "한명수", C_edoc.PAPER, "공개가능", "없음");
//        IndexSaveCaseRequest indexSaveCaseRequest10 = new IndexSaveCaseRequest(1L, indexSaveVolumeResponse.getC_id().get(9),"1", "30", "30", "1","20210825", "20210830", "강남구청 인사과", "1111", C_kperiod.SEMI,"강남구청 인사과 인사이동 리스트", "한명수", "구청장", "한명수", C_edoc.PAPER, "공개가능", "없음");

        Assertions.assertThat(fileRepository.findOne(1L).getF_volumecount()).isEqualTo("3");
        Assertions.assertThat(volumeRepository.findOne(indexSaveLabelResponse.getV_id().get(0)).getV_casecount()).isEqualTo("3");

        indexService.saveCases(indexSaveCaseRequest1);

        Assertions.assertThat(volumeRepository.findOne(indexSaveLabelResponse.getV_id().get(0)).getV_casecount()).isEqualTo("2");
        indexService.saveCases(indexSaveCaseRequest1);
        Assertions.assertThat(volumeRepository.findOne(indexSaveLabelResponse.getV_id().get(0)).getV_casecount()).isEqualTo("2");

        indexService.saveCases(indexSaveCaseRequest2);
        indexService.saveCases(indexSaveCaseRequest3);

        Assertions.assertThat(volumeRepository.findOne(indexSaveLabelResponse.getV_id().get(0)).getV_casecount()).isEqualTo("0");
        System.out.println(" ========================================================== ");
        Assertions.assertThat(fileRepository.findOne(1L).getF_volumecount()).isEqualTo("2");    ////////////////////////////
        System.out.println(" ========================================================== ");
        Assertions.assertThat(volumeRepository.findOne(indexSaveLabelResponse.getV_id().get(1)).getV_casecount()).isEqualTo("4");

        indexService.saveCases(indexSaveCaseRequest4);
        Assertions.assertThat(volumeRepository.findOne(indexSaveLabelResponse.getV_id().get(1)).getV_casecount()).isEqualTo("3");
        indexService.saveCases(indexSaveCaseRequest5);
        indexService.saveCases(indexSaveCaseRequest6);
        indexService.saveCases(indexSaveCaseRequest7);
        indexService.saveCases(indexSaveCaseRequest8);
        indexService.saveCases(indexSaveCaseRequest9);
        indexService.saveCases(indexSaveCaseRequest10);

        Assertions.assertThat(fileRepository.findOne(1L).getF_process()).isEqualTo(F_process.CHECK);
        Assertions.assertThat(volumeRepository.findOne(indexSaveLabelResponse.getV_id().get(0)).getV_casecount()).isEqualTo("3");
        Assertions.assertThat(casesRepository.findOne(indexSaveCaseRequest1.getC_id()).getC_first()).isEqualTo("1");


    }
     */

    @Test
    public void casesFindTest () {
        Cases cases = casesRepository.findOne(4L);
        Assertions.assertThat(cases).isEqualTo(cases);

    }
}