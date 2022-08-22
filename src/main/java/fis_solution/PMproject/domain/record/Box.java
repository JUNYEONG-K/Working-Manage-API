//package fis_solution.PMproject.domain.record;
//
//import fis_solution.PMproject.domain.Office;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//public class Box {
//    @Id @GeneratedValue
//    @Column(name = "b_id")
//    private Long id;
//
//    @Column(length = 3)
//    private String b_num;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "o_code")
//    private Office office;
//
//    @OneToMany(mappedBy = "box")
//    private List<Files> fileList = new ArrayList<>();
//
//}
