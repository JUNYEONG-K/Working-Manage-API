package fis_solution.PMproject.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageWorkplaceDailyResponse {

    private Long id;
    private String name;

    private String err_code;
}
