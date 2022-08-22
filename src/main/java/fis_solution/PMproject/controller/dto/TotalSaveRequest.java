package fis_solution.PMproject.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TotalSaveRequest {
    private String p_search;
    private String p_searchw;
    private String p_export;
    private String p_exportw;
    private String p_scan;
    private String p_scanw;
    private String p_image;
    private String p_imagew;
    private String p_index;
    private String p_indexw;
    private String p_indexch;
    private String p_indexchw;
    private String p_uploading;
    private String p_uploadingw;
}
