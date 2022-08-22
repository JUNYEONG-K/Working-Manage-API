package fis_solution.PMproject.service;

import fis_solution.PMproject.controller.dto.*;
import fis_solution.PMproject.domain.record.Cases;
import fis_solution.PMproject.domain.record.Files;
import fis_solution.PMproject.domain.record.Volume;
import fis_solution.PMproject.repository.*;
import fis_solution.PMproject.repository.search.FindIndexCaseInfo;
import fis_solution.PMproject.repository.search.FindIndexDetailInfo;
import fis_solution.PMproject.repository.search.FindIndexPreinfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class IndexService {

    private final CasesRepository casesRepository;
    private final FileRepository fileRepository;
    private final VolumeRepository volumeRepository;

    /**
     * 작성자: 한명수
     * 작성날짜: 2021/08/27
     * 작성내용: searchFilesByPreInfo
     */
    public List<IndexSearchResponse> searchFilesByPreInfo(FindIndexPreinfo findIndexPreinfo) {
        //Files 테이블에서 o_code, b_num, f_labelcode로 검색
        List<Files> findList = fileRepository.findByOcodeBoxNumLabel(
                findIndexPreinfo.getO_code(),
                findIndexPreinfo.getB_num(),
                findIndexPreinfo.getF_labelcode()
        );
        return findList.stream()
                .map(IndexSearchResponse::createIndexSearchResponse)
                .collect(Collectors.toList());
    }

    /**
     * 작성자: 한명수
     * 작성날짜: 2021/08/27
     * 작성내용: searchFilesByDetailInfo
     */

    public List<IndexSearchLabelResponse> searchFilesByDetailInfo(FindIndexDetailInfo findIndexDetailInfo) {

        List<Files> findList = fileRepository.findByFnameFpyearFeyear(
                findIndexDetailInfo.getF_name(),
                findIndexDetailInfo.getF_pyear(),
                findIndexDetailInfo.getF_eyear());      //넘어온 정보를 통해서 검색한 결과를 List 로 생성

        return findList.stream()
                .map(o -> new IndexSearchLabelResponse(o.getF_id(), o.getF_labelcode(), o.getF_volumeamount(), o.getF_name(),
                        o.getF_pyear(), o.getF_eyear(), o.getF_kperiod(), o.getOffice().getO_code(),
                        o.getOffice().getO_name(), o.getF_type(), o.getF_kmethod(), o.getF_kplace(), o.getF_manager()))
                .collect(Collectors.toList());  //dto로 변환하여 반환
    }

    // 2022-03-14 이승범 : 해당 철의 권정보들과 각 권들의 건정보들을 가져오기
    public List<VolumesInfo> getVolumesInfo(Long f_id) {
        List<VolumesInfo> result = volumeRepository.findByFilesWithFiles(f_id);
        List<Long> volumeIds = toVolumeIds(result);
        Map<Long, List<CasesInfo>> casesMap = casesRepository.findCasesMap(volumeIds);
        result.forEach(volumesInfo -> volumesInfo.setCasesInfoList(casesMap.get(volumesInfo.getV_id())));
        return result;
    }

    // 2022-03-14 이승범 : 권들의 아이디들을 반환하는 메서드
    private List<Long> toVolumeIds(List<VolumesInfo> result) {
        return result.stream()
                .map(volumesInfo -> volumesInfo.getV_id())
                .collect(Collectors.toList());
    }

    // 2022-03-08 이승범 : 철 정보 입력
    public IndexSaveLabelResponse saveFilesAndVolume(IndexSaveLabelRequest indexSaveLabelRequest) {
        int reqVolumeAmount = Integer.parseInt(indexSaveLabelRequest.getF_volumeamount());   //총 권호수 만큼 카운터 생성

        Files files = fileRepository.findOne(indexSaveLabelRequest.getF_id());      //file 찾아오기.

        IndexSaveLabelResponse indexSaveLabelResponse = new IndexSaveLabelResponse();

        // 색인 작업에서 최초로 철 정보를 저장할 경우
        if (files.getF_volumeSaved().compareTo("0") == 0) {
            //총 권호수 만큼 권 테이블에 생성
            for (int i = 1; i <= reqVolumeAmount; i++) {
                Volume volume = Volume.createVolume(files, i);
                volumeRepository.save(volume);
            }
        } // 철 정보 수정시 권호수가 줄었으면 그만큼 volume 삭제
        else if (reqVolumeAmount < Integer.parseInt(files.getF_volumeamount())) {
            volumeRepository.deleteByVolumeAmountOfFiles(files, reqVolumeAmount);
        } // 철 정보 수정시 권호수가 늘었으면 그만큼 volume 추가
        else {
            for (int i = Integer.parseInt(files.getF_volumeamount()) + 1; i <= reqVolumeAmount; i++) {
                Volume volume = Volume.createVolume(files, i);
                volumeRepository.save(volume);
            }
        }

        // 권호수 수정시 달라진 volumeCount 확인
        int volumeCount = 0;
        List<Volume> volumes = volumeRepository.findByFiles(files);
        for (Volume volume : volumes) {
            String caseCount = Optional.ofNullable(volume.getV_casecount()).orElse("-1");
            if(caseCount.equals("0")){
                volumeCount++;
            }
        }

        //file 정보 업데이트
        files = fileRepository.findOne(indexSaveLabelRequest.getF_id());
        files.updateFileIndex(indexSaveLabelRequest, volumeCount);
        checkVolumeCount(files);

        List<Long> result = volumes.stream()
                .map(Volume::getId)
                .collect(Collectors.toList());

        //dto 값 세팅
        indexSaveLabelResponse.setF_id(files.getF_id());
        indexSaveLabelResponse.setV_id(result);
        return indexSaveLabelResponse;
    }

    // 2022-03-15 이승범 : 해당 권에 건 껍데기 만들기
    public IndexSaveVolumeResponse saveCasesPages(IndexSaveVolumeRequest indexSaveVolumeRequest) {
        Files findFiles = fileRepository.findOne(indexSaveVolumeRequest.getF_id());
        Volume updateVolume = volumeRepository.findOne(indexSaveVolumeRequest.getV_id());
        List<IndexSaveVolumeRequest.PageInfo> pageList = indexSaveVolumeRequest.getV_info();
        IndexSaveVolumeResponse indexSaveVolumeResponse = new IndexSaveVolumeResponse();
        List<Long> result = new ArrayList<>();

        if (updateVolume.getV_pageSaved().compareTo("0") == 0) {
            updateVolume.resetCaseCount(pageList.size());
            for(int i=0; i<Integer.parseInt(updateVolume.getV_casenum()); i++){
                Cases cases = Cases.createCases(i, updateVolume, findFiles, pageList.get(i).getStartPage(), pageList.get(i).getEndPage());
                casesRepository.save(cases);
                result.add(cases.getId());
            }
        }else{
            List<Cases> casesList = casesRepository.findByVolume(indexSaveVolumeRequest.getV_id());
            if (pageList.size() > casesList.size()) {
                for (int j = 0; j < casesList.size(); j++) {
                    casesList.get(j).updatePage(pageList.get(j).getStartPage(), pageList.get(j).getEndPage());
                }
                for (int j = casesList.size(); j < pageList.size(); j++) {
                    Cases cases = Cases.createCases(j, updateVolume, findFiles, pageList.get(j).getStartPage(), pageList.get(j).getEndPage());
                    casesRepository.save(cases);
                    casesList.add(cases);
                }
            } // 수정된 권의 건수가 그 전보다 적을 경우 나머지 건들 삭제
            else if (pageList.size() < casesList.size()) {
                for (int j = 0; j < pageList.size(); j++) {
                    casesList.get(j).updatePage(pageList.get(j).getStartPage(), pageList.get(j).getEndPage());
                }
                List<Long> deletedCasesIdList = new ArrayList<>();
                for (int j = pageList.size(); j < casesList.size(); j++) {
                    deletedCasesIdList.add(casesList.get(j).getId());
                }
                casesRepository.deleteRemainCases(deletedCasesIdList);
                // 벌크 연산후 영속성 컨텍스트 다시 구성
                findFiles = fileRepository.findOne(indexSaveVolumeRequest.getF_id());
                updateVolume = volumeRepository.findOne(indexSaveVolumeRequest.getV_id());
                casesList = casesRepository.findByVolume(indexSaveVolumeRequest.getV_id());
                // 현재 남아있는 건들의 정보들이 다 입력된 상태인가
            } // 수정된 권의 건수가 그 전과 동일할 경우 페이지들만 수정
            else {
                for (int j = 0; j < casesList.size(); j++) {
                    casesList.get(j).updatePage(pageList.get(j).getStartPage(), pageList.get(j).getEndPage());
                }
            }
            updateVolume.updateCaseCount(casesList);
            checkCaseCount(findFiles, updateVolume);
            result = casesList.stream().map(cases -> cases.getId()).collect(Collectors.toList());
        }
        updateVolume.updatePageSaved();
        indexSaveVolumeResponse.setC_id(result);
        return indexSaveVolumeResponse;
    }

    // 2022-03-12 이승범 : 건 항목 입력
    public IndexSaveCaseResponse saveCases(IndexSaveCaseRequest indexSaveCaseRequest) {
        //건 테이블 저장
        //건 튜플 first 1인지 체크 후 그에따른 로직 실행
        //상위 테이블에 -1 후 0인지 체크
        //철 테이블 count가 0이되면 f_process 체크 , f_process가 INPUT 이면 check로 바꾼 후 f_complete 타임스탬프 찍기, CHECK 이면 UPLOAD 로 바꾼 후 f_check에 타임스템프 찍기
        //철 테이블 volumecount, 권테이블 casecount 초기화

        Cases findCases = casesRepository.findOneWithFilesVolume(indexSaveCaseRequest.getC_id());
        Files findFile = findCases.getFiles();
        Volume findVolume = findCases.getVolume();

        if (findCases.getC_first().equals("1")) {
            findCases.reduceFirst();
            findVolume.reduceCaseCount();
            checkCaseCount(findFile, findVolume);
        }
        findCases.updateCases(indexSaveCaseRequest);
        IndexSaveCaseResponse indexSaveCaseResponse = new IndexSaveCaseResponse();
        indexSaveCaseResponse.setC_id(findCases.getId());
        return indexSaveCaseResponse;
    }

    // 2022-03-11 이승범 : 권에 있는 건들의 정보가 모두 입력되었는지 확인
    private void checkCaseCount(Files findFile, Volume findVolume) {
        if (findVolume.getV_casecount().compareTo("0") == 0) {
            findFile.reduceVolumeCount();
            checkVolumeCount(findFile);
        }
    }

    // 2022-03-11 이승범 : 철에 있는 권들의 정보가 모두 입력되었는지 확인
    private void checkVolumeCount(Files findFile) {
        if (findFile.getF_volumecount().compareTo("0") == 0) {
            findFile.updateProcess();
            List<Cases> findCasesList = casesRepository.findByFiles(findFile);
            for (Cases cases : findCasesList) {
                cases.resetCount();
            }
            List<Volume> findVolumeList = volumeRepository.findByFiles(findFile);
            for (Volume volume : findVolumeList) {
                volume.resetCount();
            }
        }
    }
    /*
     * 작성자: 한명수
     * 작성날짜: 2021/08/30
     * 작성내용: deleteFiles
     */

    public IndexLabelDelResponse deleteFiles(Long f_id) {
        Files files = fileRepository.findOne(f_id);     //넘어온 file_id 를 이용하여 해당 file 찾음

        Long rmId = fileRepository.remove(files);       //해당 file을 삭제
        return new IndexLabelDelResponse(rmId, null);
    }
    /*
     * 작성자: 한명수
     * 작성날짜: 2021/08/31
     * 작성내용: searchCasesByCasesInfo
     */

    public List<IndexSearchCaseResponse> searchCasesByCasesInfo(FindIndexCaseInfo findIndexCaseInfo) {
        return casesRepository.findByOldNumTitleReceiver(findIndexCaseInfo.getC_oldnum(), findIndexCaseInfo.getC_title(),
                        findIndexCaseInfo.getC_receiver())
                .stream()
                .map(o -> new IndexSearchCaseResponse(o.getFiles().getF_labelcode(), o.getFiles().getF_volumeamount(),
                        o.getC_pdate(), o.getC_dodate(), o.getC_title(), o.getC_spage(), o.getC_epage(), o.getC_page(),
                        o.getC_oldnum(), o.getC_kperiod(), o.getFiles().getOffice().getO_code(),
                        o.getFiles().getOffice().getO_name(), o.getFiles().getF_type(), o.getC_class()))
                .collect(Collectors.toList());
    }
}
