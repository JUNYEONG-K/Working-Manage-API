package fis_solution.PMproject.service;

import fis_solution.PMproject.domain.Office;
import fis_solution.PMproject.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 작성자: 고준영
 * 작성 날짜: 2021/08/26
 * 작성내용: Common Service (기관 코드 등록, 조회, 수정, 삭제)
 */

@Service
@RequiredArgsConstructor
@Transactional
public class CommonService {

    private final OfficeRepository officeRepository;

    // 기관 등록
    @Transactional
    public String join(Office office) {
        validateDuplicateWorker(office);
        officeRepository.save(office);
        return office.getO_code();
    }

    // 기관 코드로 검색
    public List<Office> findByCode(String code) {
        return officeRepository.findByCode(code);
    }
    // 기관명으로 검색
    public List<Office> findByName(String name) {
        return officeRepository.findByName(name);
    }
    // 기관 전체 조회
    public List<Office> findAll() {
        return officeRepository.findAll();
    }

    // 기관 1개 조회
    public Office findOne(String id) {
        return officeRepository.findOne(id);
    }

    // 기관 정보 수정
    @Transactional
    public void updateOffice(String o_code, String o_name, String o_del) {
        Office findOffice = findOne(o_code);
        findOffice.updateOffice(o_code, o_name, o_del);
    }

    // 기관 코드 중복 검사
    private void validateDuplicateWorker(Office office) {
        List<Office> findOffices = officeRepository.findByCode(office.getO_code());
        if(!findOffices.isEmpty()) {
            throw new IllegalStateException("동일 기관 코드의 기관이 이미 등록되어 있습니다.");
        }
    }

    // 기관 정보 삭제
    @Transactional
    public String removeOffice(Office office) {
        Office findOffice = findOne(office.getO_code());
        officeRepository.remove(findOffice);
        return findOffice.getO_code();
    }
}
