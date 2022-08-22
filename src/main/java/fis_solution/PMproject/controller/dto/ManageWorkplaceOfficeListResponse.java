package fis_solution.PMproject.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ManageWorkplaceOfficeListResponse {
    private int[] tot;
    private String[] name;
    private int count;
    private int[] db;
    private int[] scan;
    private String[] o_code;
//    private Long dbVolume;
//    private Long scanVolume;
}
