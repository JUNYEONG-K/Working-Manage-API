//package fis_solution.PMproject.repository;
//
///*
// * 작성자 : 현승구
// * 작성일자 : 2021/08/23
// * 작성내용 : save, findOne, remove
// */
//
//import fis_solution.PMproject.domain.record.Box;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//
//@Repository
//@RequiredArgsConstructor
//public class BoxRepository {
//    //스프링 빈 자동 등록
//    private final EntityManager em;
//
//    public void save(Box box){
//        em.persist(box);
//    }
//
//    public Box findOne(Long id){
//        return em.find(Box.class, id);
//    }
//
//    public Long remove(Box box){
//        Long Id = box.getId();
//        em.remove(box);
//        return Id;
//    }
//}
