package com.backend_pg.Repository;

import com.backend_pg.entity.Papers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PapersRepository extends JpaRepository<Papers,Integer> {

    @Query(value = "select * from papers WHERE TRUE ORDER BY papers.paper_click_times DESC",nativeQuery = true)
    List<Papers> all();

    @Query(value = "select * from papers as p where p.paper_name LIKE %?1% OR p.paper_author LIKE %?1%",nativeQuery = true)
    List<Papers> searchExactPapers(String keywords);

    @Query(value = "select MAX(paper_id) from papers",nativeQuery = true)
    Integer newestPaperID();

    //@Query(value = "SELECT DISTINCT * FROM" +
    //        "( SELECT * FROM papers WHERE paper_name REGEXP ?1 " +
    //        "UNION " +
    //        "SELECT * FROM papers WHERE paper_author REGEXP ?2 )" +
    //        "GROUP BY paper_name ORDER BY COUNT(paper_name) DESC",nativeQuery = true)
    //List<Papers> searchVaguePapers(String name_regexp, String author_regexp);


    @Query(value = "SELECT papers.* from (papers RIGHT JOIN" +
            "(SELECT paper_id,count(*) c from " +
            "(" +
            "(SELECT paper_id FROM papers where paper_name ~ ?1 )" +
            "UNION ALL" +
            "(SELECT paper_id FROM papers where paper_author ~ ?2)" +
            ")  GROUP BY paper_id) as b " +
            "on papers.paper_id=b.paper_id) ORDER BY c desc",nativeQuery = true)
    List<Papers> searchVaguePapers(String name_regexp, String author_regexp);
}
