package fis_solution.PMproject.controller;

import fis_solution.PMproject.domain.record.Files;
import fis_solution.PMproject.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ManageController {

    private final FileRepository fileRepository;
    /*
        계획 대비 실적을 작성해야 한다.
        필요한 내용
        1. 사전조사, 문서 반출은 철기준으로 작성된다.
        2. 철기준으로 완료된 스캔, 미완료 스캔
        3. 이미지 업로드 문서 반출 철기준
        4. 이미지 보정 상태를 알수있는 건 없다.
        5. 색인입력에서 건들을 입력하게 되는데, 그 건들 생성으로 알수 있음
        6. 업로드 이것도 어차피 철단위
    */
    @GetMapping("manage/finshwork")
    public void work(){
        List<Files> filesList = fileRepository.findAll();

    }
}
