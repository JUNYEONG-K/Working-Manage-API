package fis_solution.PMproject.controller;

import fis_solution.PMproject.controller.dto.TotalInfoResponse;
import fis_solution.PMproject.controller.dto.TotalSaveRequest;
import fis_solution.PMproject.domain.management.Total;
import fis_solution.PMproject.repository.FileRepository;
import fis_solution.PMproject.repository.TotalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TotalController {

    final private TotalRepository totalRepository;
    final private FileRepository fileRepository;

    @PostMapping("/total/save")
    public Long saveTotal(@RequestBody TotalSaveRequest totalSaveRequest){
        Total total = new Total(totalSaveRequest);
        return totalRepository.save(total);
    }

    @GetMapping("/total/info")
    public List<TotalInfoResponse> info(){
        /*
            요청 보내면 get total 해서 입력 정보를 가져오고
            count 를 사용해서 f_process 구성품들을 확인한다. 숫자만 알면된다.
         */
        List<TotalInfoResponse> totalInfoResponseList = fileRepository.process();
        TotalInfoResponse[] totalInfoResponseArray = new TotalInfoResponse[7];
        Total total = totalRepository.get();

        totalInfoResponseList.stream().forEach(totalInfoResponse -> {
            if(totalInfoResponse.getName().equals("사전조사")){
                totalInfoResponse.setPlan(Long.valueOf(total.getP_search()));
                totalInfoResponse.setPlan_w(Long.valueOf(total.getP_searchw()));
                totalInfoResponseArray[0] = totalInfoResponse;
            } else if(totalInfoResponse.getName().equals("문서반출")){
                totalInfoResponse.setPlan(Long.valueOf(total.getP_export()));
                totalInfoResponse.setPlan_w(Long.valueOf(total.getP_exportw()));
                totalInfoResponseArray[1] = totalInfoResponse;
            } else if(totalInfoResponse.getName().equals("스캔작업")){
                totalInfoResponse.setPlan(Long.valueOf(total.getP_scan()));
                totalInfoResponse.setPlan_w(Long.valueOf(total.getP_scanw()));
                totalInfoResponseArray[2] = totalInfoResponse;
            } else if(totalInfoResponse.getName().equals("이미지보정")){
                totalInfoResponse.setPlan(Long.valueOf(total.getP_image()));
                totalInfoResponse.setPlan_w(Long.valueOf(total.getP_imagew()));
                totalInfoResponseArray[3] = totalInfoResponse;
            } else if(totalInfoResponse.getName().equals("색인입력")){
                totalInfoResponse.setPlan(Long.valueOf(total.getP_index()));
                totalInfoResponse.setPlan_w(Long.valueOf(total.getP_indexw()));
                totalInfoResponseArray[4] = totalInfoResponse;
            } else if(totalInfoResponse.getName().equals("색인검수")){
                totalInfoResponse.setPlan(Long.valueOf(total.getP_indexch()));
                totalInfoResponse.setPlan_w(Long.valueOf(total.getP_indexchw()));
                totalInfoResponseArray[5] = totalInfoResponse;
            } else if(totalInfoResponse.getName().equals("업로드")){
                totalInfoResponse.setPlan(Long.valueOf(total.getP_uploading()));
                totalInfoResponse.setPlan_w(Long.valueOf(total.getP_uploadingw()));
                totalInfoResponseArray[6] = totalInfoResponse;
            }
        });

        return Arrays.asList(totalInfoResponseArray);
    }

}
