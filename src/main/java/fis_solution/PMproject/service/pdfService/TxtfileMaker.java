package fis_solution.PMproject.service.pdfService;

//현승구 08/26

import fis_solution.PMproject.domain.record.Files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TxtfileMaker {
    public void writeLabeltxt(String label_path, Files file) throws IOException {
        /*
        1. 처리과기관코드  f_inheroffice
        2. 구기록물 철생산기관명 foffice.geto_name?
        3. 생산년도     f_pyear
        4. 단위업무코드 -> 구기록등록 단위업무(99999999)로 만들기      f_unitcode
        5. 소기능코드(필수 x)      f_smallfunc
        6. 기록물철 등록일련번호      f_inherlabelcode
        7. 권호수              참조로 가져오기
        8. 기록물철 제목              f_name
        9. 기목물철 기타제목(필수 x)
        10. 기록물 형태              f_type
        11. 시작년도                f_syear
        12. 종료연도                    f_eyear
        13. 보존기간                    f_kperiod
        14. 보존방법                    f_kmethod
        15. 보존장소                    f_kplace
        16. 비치종결일자(필수 x)
        17. 비치사유(필수 x)
        18. 업무담당자(필수 x)
        19. 구기록물철 분류번호(필수 x)
        20. 기록물 구분(필수 x)
        21. (신/구)(필수 x)
        22. 수정여부(필수 x)
        23. 기록물등록건수                f_regnum
        24. 기록물 쪽수                  f_page
        25. 전자파일갯수              f_efilenum
        26. 인수인계구분 => "0" 해당없음으로 자동 세팅
        27. 처리과 기관코드(필수 x)
        28. 단위 업무코드(필수 x)
        29. 생산 년도(필수 x)
        30. 기록물철 등록일련번호(필수 x)
        31. 권호수(필수 x)
        32. 내용요약(필수 x)
        33. 기타(필수 x)
         */
        File labele_txt = new File(label_path + File.separator + "DOCU.txt");
        FileWriter fw = new FileWriter(labele_txt, true);
        BufferedWriter writer = new BufferedWriter(fw);

        if(!labele_txt.exists()) {
            labele_txt.createNewFile();
            //ㅇ이미 있다면 생성하지 않는다.
        }
        try {

            String data = file.getF_inheroffice() + '\t' + file.getOffice().getO_name() + '\t' + file.getF_pyear() + '\t' + file.getF_unitcode() + '\t' + '\t' + file.getF_inherlabelcode() + '\t' + file.getF_volumeamount() + '\t' +
                    file.getF_name() + '\t' + '\t' + file.getF_type() + '\t' + file.getF_syear() + '\t' + file.getF_syear() + '\t' + file.getF_kperiod() + '\t' + file.getF_kmethod() + '\t' + file.getF_kplace() + '\t' +
                    '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + file.getF_regnum() + '\t' + file.getF_page() + '\t' + file.getF_efilenum() + '\t' + "0" + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t';
            writer.write(data);
            writer.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                if (fw != null) fw.close();
                if (writer != null) writer.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void writecasetxt(String case_path, Files file) throws IOException {
        /*
        1, 처리과 기관코드
        2. 구기록물 생산기관명
        3. 등록구분
        4. 생산(접수) 등록일자
        5. 구기록물 문서번호
        6. 분리등록번호
        7. 제목
        8. 기타 제목 (필수 x)
        9. 쪽수
        10. 결제권자 (필수 x)
        11. 검토자
        12. 협조자
        13. 기안자
        14. 결제일자
         */
        File case_txt = new File(case_path + File.separator + "DETAIL.txt");
        FileWriter fw = new FileWriter(case_txt, true);
        BufferedWriter writer = new BufferedWriter(fw);
        if(!case_txt.exists()) {
            case_txt.createNewFile();
            //ㅇ이미 있다면 생성하지 않는다.
        }
        try {
            String data = file.getF_inheroffice() + '\t' + file.getOffice().getO_name() + '\t' + file.getF_pyear() + '\t' + file.getF_unitcode() + '\t' + '\t' + file.getF_inherlabelcode() + '\t' + file.getF_volumeamount() + '\t' +
                    file.getF_name() + '\t' + '\t' + file.getF_type() + '\t' + file.getF_syear() + '\t' + file.getF_syear() + '\t' + file.getF_kperiod() + '\t' + file.getF_kmethod() + '\t' + file.getF_kplace() + '\t' +
                    '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + file.getF_regnum() + '\t' + file.getF_page() + '\t' + file.getF_efilenum() + '\t' + "0" + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t';
            writer.write(data);
            writer.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                if (fw != null) fw.close();
                if (writer != null) writer.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
