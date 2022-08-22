package fis_solution.PMproject;


import fis_solution.PMproject.domain.Office;

import fis_solution.PMproject.domain.Process;
import fis_solution.PMproject.domain.management.Authority;

import fis_solution.PMproject.domain.management.Plan;
import fis_solution.PMproject.domain.management.Total;
import fis_solution.PMproject.domain.management.Worker;
import fis_solution.PMproject.domain.record.*;
import fis_solution.PMproject.domain.record.caseEnum.C_edoc;
import fis_solution.PMproject.domain.record.fileEnum.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;




/*
 * 작성자: 원보라
 * 작성날짜: 2021/08/25
 * 작성내용: InitDb
 */

@Component
@RequiredArgsConstructor
public class InitDb {


    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit() {
            Process process = new Process("", F_process.NONE);
            em.persist(process);
            Process process1 = new Process("사전조사", F_process.PREINFO);
            em.persist(process1);
            Process process2 = new Process("문서반출", F_process.EXPORT);
            em.persist(process2);
            Process process3 = new Process("스캔작업", F_process.SCAN);
            em.persist(process3);
            Process process4 = new Process("이미지보정", F_process.IMGMODIFY);
            em.persist(process4);
            Process process5 = new Process("색인입력", F_process.INPUT);
            em.persist(process5);
            Process process6 = new Process("색인검수", F_process.CHECK);
            em.persist(process6);
            Process process7 = new Process("업로드", F_process.UPLOAD);
            em.persist(process7);
            Process process8 = new Process("업로드완료", F_process.UPLOADED);
            em.persist(process8);


            Office office1 = createOffice("1234567", "서울 강남구", "1");
            em.persist(office1);

            Office office2 = createOffice("5555444", "경기 안양시", "0");
            em.persist(office2);

            Office office3 = createOffice("2367812", "서울 노원구", "0");
            em.persist(office3);

            Office office4 = createOffice("4537812", "경기 의정부", "0");
            em.persist(office4);

            Office office5 = createOffice("0000001", "서울특별시 마포구 상수동 와우산로 94 홍익대학교", "0");
            em.persist(office5);


            F_location f_location = new F_location("층", "서가", "열", "번");

            Files files1 = createFile(office1, "000001", "001", F_process.IMGMODIFY, "5", "005", "강남구청 인사과", "2021", F_kperiod.SEMI, F_kmethod.ALL, F_kplace.PROFESSION, "2021", "2021", F_construct.YES, F_construct.YES, "99999999", F_type.GENERAL, F_newold.OLD, F_modify.NOTHING, "000100", "500", "0", F_inheritance.NONE, "2021-08-25", "0", "0", "0", f_location);
            em.persist(files1);

            Files files2 = createFile(office1, "000002", "002", F_process.UPLOAD, "3", "003", "경기도 안양시", "1990", F_kperiod.PERMANENT, F_kmethod.ORIGINAL, F_kplace.ARCHIVIST, "1990", "1995", F_construct.YES, F_construct.NO, "99999999", F_type.CARD, F_newold.OLD, F_modify.MODIFY, "00666", "984", "0", F_inheritance.NONE, "1998-03-29", "0", "0", "0", f_location);
            em.persist(files2);

            Files files3 = createFile(office2, "000003", "001", F_process.INPUT, "5", "005", "강남구청 인사과", "2021", F_kperiod.SEMI, F_kmethod.ALL, F_kplace.PROFESSION, "2021", "2021", F_construct.YES, F_construct.YES, "99999999", F_type.GENERAL, F_newold.OLD, F_modify.NOTHING, "000100", "500", "0", F_inheritance.NONE, "2021-08-25", "0", "0", "0", f_location);
            em.persist(files3);

            Files files4 = createFile(office5, "000004", "004", F_process.CHECK, "5", "005", "강남구청 인사과 안녕하세요 인사과입니다 안뇽뇽", "2024", F_kperiod.SEMI, F_kmethod.ALL, F_kplace.PROFESSION, "2024", "2024", F_construct.YES, F_construct.YES, "99999999", F_type.GENERAL, F_newold.OLD, F_modify.NOTHING, "000400", "500", "0", F_inheritance.NONE, "none", "0", "0", "0", f_location);
            em.persist(files4);

            Files files5 = createFile(office5, "000005", "005", F_process.CHECK, "3", "003", "안양시청 실내수영장 10월 일정표", "2025", F_kperiod.SEMI, F_kmethod.ALL, F_kplace.PROFESSION, "2025", "2025", F_construct.YES, F_construct.YES, "99999999", F_type.GENERAL, F_newold.OLD, F_modify.NOTHING, "000500", "500", "0", F_inheritance.NONE, "none", "0", "0", "0", f_location);
            em.persist(files5);

            Files files6 = createFile(office1, "000006", "006", F_process.CHECK, "10", "010", "금천구청 ...", "2000", F_kperiod.SEMI, F_kmethod.ALL, F_kplace.PROFESSION, "2000", "2001", F_construct.NO, F_construct.YES, "99999999", F_type.GENERAL, F_newold.OLD, F_modify.NOTHING, "000600", "600", "0", F_inheritance.NONE, "none", "0", "0", "0", f_location);
            em.persist(files6);

            Files files7 = createFile(office2, "000007", "007", F_process.CHECK, "8", "008", "경기도 ...", "1999", F_kperiod.SEMI, F_kmethod.ALL, F_kplace.PROFESSION, "1999", "1999", F_construct.NO, F_construct.NO, "99999999", F_type.GENERAL, F_newold.OLD, F_modify.NOTHING, "000700", "700", "0", F_inheritance.NONE, "none", "0", "0", "0", f_location);
            em.persist(files7);

            Worker worker1 = creatWorker(Authority.WORKER, "1234", "한명수");
            Worker worker2 = creatWorker(Authority.WORKER, "1234", "원보라");
            Worker worker3 = creatWorker(Authority.WORKER, "1234", "현승구");
            Worker worker4 = creatWorker(Authority.ADMIN, "1234", "고준영");
            em.persist(worker1);
            em.persist(worker2);
            em.persist(worker3);
            em.persist(worker4);

            Plan plan = createPlan("1000", "10", "1000", "10", "1000", "10", "1000", "10",
                    "1000", "10", "1000", "10", "1000", "10", "1000", "10",
                    "1000", "10", "1000", "10", "1000", "10", "1000", "10",
                    "1000", "10", "1000", "10", "1000", "10", "1000", "10",
                    "1000", "10", "1000", "10");

            em.persist(plan);

            Total total = new Total("1000", "10","1000", "10","1000", "10","1000", "10","1000", "10","1000", "10","1000", "10");
            em.persist(total);
        }

        private Cases createCase(String pdate, String pnum, String attachnum, Files file, String c_oldnum, String c_title, String c_receiver){
            Cases c = new Cases();
            c.setC_oldnum(c_oldnum);
            c.setC_title(c_title);
            c.setC_receiver(c_receiver);
            c.setC_pdate(pdate);
            c.setC_pnum(pnum);
            c.setC_attachnum(attachnum);
            c.setFiles(file);
            //c.setC_edoc(C_edoc.ELEC);
            return c;
        }

        private Office createOffice(String o_code, String o_name, String o_del) {
            Office office = new Office();
            office.setO_code(o_code);
            office.setO_name(o_name);
            office.setO_del(o_del);
            return office;
        }


        private Files createFile(Office office, String f_labelcode, String b_num, F_process f_process, String f_volumecount, String f_volumeamount, String f_name, String f_pyear, F_kperiod f_kperiod, F_kmethod f_kmethod, F_kplace f_kplace, String f_syear, String f_eyear, F_construct f_db, F_construct f_scan, String f_unitcode, F_type f_type, F_newold f_newold, F_modify f_modify, String f_regnum, String f_page, String f_efilenum, F_inheritance f_inheritance, String f_exportdate, String f_complete, String f_check, String f_upload, F_location f_location) {
            Files file = new Files();
            file.setOffice(office);
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
            file.setF_volumeSaved("0");
            file.setF_pageSaved("0");
            file.setF_location(f_location);
            return file;
        }

        private Worker creatWorker(Authority authority, String password, String w_name){
            Worker worker = new Worker();
            worker.setAuthority(authority);
            worker.setPassword(password);
            worker.setW_name(w_name);
            worker.setNickname(w_name);
            return worker;
        }

        private Plan createPlan(String p_search, String p_searchw, String p_list, String p_listw, String p_export, String p_exportw, String p_classify, String p_classifyw,
                                String p_classifych, String p_classifychw, String p_page, String p_pagew, String p_pagech, String p_pagechw, String p_scan, String p_scanw,
                                String p_scanpage, String p_scanpagew, String p_image, String p_imagew, String p_imagech, String p_imagechw, String p_index, String p_indexw,
                                String p_indexch, String p_indexchw, String p_loading, String p_loadingw, String p_reorg, String p_reorgw, String p_boxreorg, String p_boxreorgw,
                                String p_place, String p_placew, String p_etc, String p_etcw) {
            Plan plan = new Plan();
            plan.setP_search(p_search);
            plan.setP_searchw(p_searchw);
            plan.setP_list(p_list);
            plan.setP_listw(p_listw);
            plan.setP_export(p_export);
            plan.setP_exportw(p_exportw);
            plan.setP_classify(p_classify);
            plan.setP_classifyw(p_classifyw);
            plan.setP_classifych(p_classifych);
            plan.setP_classifychw(p_classifychw);
            plan.setP_page(p_page);
            plan.setP_pagew(p_pagew);
            plan.setP_pagech(p_pagech);
            plan.setP_pagechw(p_pagechw);
            plan.setP_scan(p_scan);
            plan.setP_scanw(p_scanw);
            plan.setP_scanpage(p_scanpage);
            plan.setP_scanpagew(p_scanpagew);
            plan.setP_image(p_image);
            plan.setP_imagew(p_imagew);
            plan.setP_imagech(p_imagech);
            plan.setP_imagechw(p_imagechw);
            plan.setP_index(p_index);
            plan.setP_indexw(p_indexw);
            plan.setP_indexch(p_indexch);
            plan.setP_indexchw(p_indexchw);
            plan.setP_loading(p_loading);
            plan.setP_loadingw(p_loadingw);
            plan.setP_reorg(p_reorg);
            plan.setP_reorgw(p_reorgw);
            plan.setP_boxreorg(p_boxreorg);
            plan.setP_boxreorgw(p_boxreorgw);
            plan.setP_place(p_place);
            plan.setP_placew(p_placew);
            plan.setP_etc(p_etc);
            plan.setP_etcw(p_etcw);
            return plan;
        }
    }
}

