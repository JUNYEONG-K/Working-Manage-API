package fis_solution.PMproject.domain.management;

import fis_solution.PMproject.controller.dto.TotalInfoResponse;
import fis_solution.PMproject.controller.dto.TotalSaveRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Total {

    @Id
    @GeneratedValue
    @Column(name = "t_id")
    private Long id;

    // 사전 조사
    private String p_search;
    private String p_searchw;

    // 문서 반출
    private String p_export;
    private String p_exportw;

    // 스캔문서
    private String p_scan;
    private String p_scanw;

    // 이미지 보정
    private String p_image;
    private String p_imagew;

    // 색인입력
    private String p_index;
    private String p_indexw;

    public Total(String p_search, String p_searchw, String p_export, String p_exportw, String p_scan, String p_scanw, String p_image, String p_imagew, String p_index, String p_indexw, String p_indexch, String p_indexchw, String p_uploading, String p_uploadingw) {
        this.p_search = p_search;
        this.p_searchw = p_searchw;
        this.p_export = p_export;
        this.p_exportw = p_exportw;
        this.p_scan = p_scan;
        this.p_scanw = p_scanw;
        this.p_image = p_image;
        this.p_imagew = p_imagew;
        this.p_index = p_index;
        this.p_indexw = p_indexw;
        this.p_indexch = p_indexch;
        this.p_indexchw = p_indexchw;
        this.p_uploading = p_uploading;
        this.p_uploadingw = p_uploadingw;
    }

    // 색인보정
    private String p_indexch;
    private String p_indexchw;

    // 업로드
    private String p_uploading;
    private String p_uploadingw;

    public Total(TotalSaveRequest totalSaveRequest) {
        this.p_search = totalSaveRequest.getP_search();
        this.p_searchw = totalSaveRequest.getP_searchw();
        this.p_export = totalSaveRequest.getP_export();
        this.p_exportw = totalSaveRequest.getP_exportw();
        this.p_scan = totalSaveRequest.getP_scan();
        this.p_scanw = totalSaveRequest.getP_scanw();
        this.p_image = totalSaveRequest.getP_image();
        this.p_imagew = totalSaveRequest.getP_imagew();
        this.p_index = totalSaveRequest.getP_index();
        this.p_indexw = totalSaveRequest.getP_indexw();
        this.p_indexch = totalSaveRequest.getP_indexch();
        this.p_indexchw = totalSaveRequest.getP_indexchw();
        this.p_uploading = totalSaveRequest.getP_uploading();
        this.p_uploadingw = totalSaveRequest.getP_uploadingw();
    }
}
