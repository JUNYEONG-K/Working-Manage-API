package fis_solution.PMproject.controller;

import fis_solution.PMproject.controller.dto.ImportOfficeJsonDto;
import fis_solution.PMproject.controller.dto.OfficeExcel;
import fis_solution.PMproject.controller.dto.SearchOfficeResponse;
import fis_solution.PMproject.domain.Office;
import fis_solution.PMproject.exception.ExcelException;
import fis_solution.PMproject.repository.OfficeRepository;
import fis_solution.PMproject.service.CommonService;
import fis_solution.PMproject.service.excelService.ExcelService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommonApiController {

    private final CommonService commonService;
    private final ExcelService excelService;
    private final OfficeRepository officeRepository;

    /**
     *   Excel 내부 오류 ExcelException, NoSuchMethodException(리플렉션으로 발생)
     */
    @PostMapping("office/excel")
    public List<OfficeExcel> importOfficeExcel(@RequestParam MultipartFile excelFile) throws ExcelException, NoSuchMethodException {
            List<OfficeExcel> officeList = new ArrayList<>();
            excelService.excelToJson(excelFile, OfficeExcel.class).forEach(o -> {
                Office office = new Office((OfficeExcel) o);
                officeList.add((OfficeExcel) o);
                commonService.join(office);
            });
            return officeList;
    }

    /**
     *   중복된 Office 존재하면 IllegalStateException 발생
     */
    @PostMapping("office/json")
    public List<OfficeExcel> importOfficeJson(@RequestBody ImportOfficeJsonDto importOfficeJsonDto, BindingResult bindingResult) throws IllegalStateException {
        List<OfficeExcel> dataList = importOfficeJsonDto.getData();
        dataList.stream().forEach(data -> {
            Office office = new Office((OfficeExcel) data);
            commonService.join(office);
        });
        return dataList;
    }

    /**
     *
     */
    @GetMapping("search/office")
    public Result searchOfficeByName(@RequestParam(required = false) String o_name) {
        List<Office> offices = commonService.findByName(o_name);
        List<OfficeDTO> collect = offices.stream()
                .map(office -> new OfficeDTO(office.getO_code(), office.getO_name(), office.getO_del()))
                .collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }

    /**
     *
     */
    @GetMapping("search/office/{o_code}")
    public Result searchOfficeByCode(@PathVariable String o_code) {
        List<Office> offices = commonService.findByCode(o_code);
        List<OfficeDTO> collect = offices.stream()
                .map(office -> new OfficeDTO(office.getO_code(), office.getO_name(), office.getO_del()))
                .collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }
    @Data
    @AllArgsConstructor
    static class OfficeDTO {
        private String o_code;
        private String o_name;
        private String o_del;

//        private String err_code;
    }
}
