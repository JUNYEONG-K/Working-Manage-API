package fis_solution.PMproject.domain;

import fis_solution.PMproject.domain.record.fileEnum.F_process;
import fis_solution.PMproject.domain.record.fileEnum.F_processConverter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Process {

    @Id
    String id;

    @Convert(converter= F_processConverter.class)
    F_process f_process;

    public Process(F_process f_process) {
        this.f_process = f_process;
    }
}
