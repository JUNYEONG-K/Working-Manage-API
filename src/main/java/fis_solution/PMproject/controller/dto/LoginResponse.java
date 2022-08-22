package fis_solution.PMproject.controller.dto;

import fis_solution.PMproject.domain.management.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private Long workerId;
    private String w_name;
    private Authority authority;
}
