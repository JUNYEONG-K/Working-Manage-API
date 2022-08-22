package fis_solution.PMproject.controller.dto;

import fis_solution.PMproject.domain.record.fileEnum.F_process;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
public class TotalInfoResponse {
    private String name;
    private Long plan;
    private Long plan_w;
    private Long worked;

    public TotalInfoResponse(String name, @Nullable Long worked) {
        this.name = name;
        this.worked = worked;
    }
}
