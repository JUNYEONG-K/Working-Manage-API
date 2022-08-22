package fis_solution.PMproject.controller.dto;

import fis_solution.PMproject.service.excelService.ExcelColumn;
import lombok.Data;

@Data
public class OfficeExcel {
    @ExcelColumn(name = "기관코드")
    private String o_code;
    @ExcelColumn(name = "전체기관명")
    private String o_name;
    @ExcelColumn(name = "폐지구분")
    private String o_del;
}
