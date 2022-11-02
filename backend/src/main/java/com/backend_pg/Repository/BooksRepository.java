package com.backend_pg.Repository;

import com.backend_pg.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BooksRepository extends JpaRepository<Books,Integer> {

    @Query(value = "select * from books WHERE TRUE ORDER BY books.book_id",nativeQuery = true)
    List<Books> all();

    @Query(value = "select * from books as b where b.book_name = ?1 OR b.book_author = ?1 OR b.isbn = ?1 ORDER BY b.book_id",nativeQuery = true)
    List<Books> searchExactBooks(String keywords);

    @Query(value = "select MAX(book_id) from books",nativeQuery = true)
    Integer newestBookID();

    @Query(value = "select * from books as b where b.book_holder=?1",nativeQuery = true)
    List<Books> rentBooks(String user_email);

    //@Query(value = "SELECT DISTINCT * FROM" +
    //        "( SELECT * FROM books WHERE book_name REGEXP ?1 " +
    //        "UNION " +
    //        "SELECT * FROM books WHERE book_author REGEXP ?2 )" +
    //        "GROUP BY book_name ORDER BY COUNT(book_name) DESC",nativeQuery = true)
    //List<Books> searchVagueBooks(String name_regexp, String author_regexp);

    @Query(value = "SELECT books.* from (books RIGHT JOIN" +
                        "(SELECT book_id,count(*) c from " +
                            "(" +
                            "(SELECT book_id FROM books where book_name ~ ?1 )" +
                                "UNION ALL" +
                            "(SELECT book_id FROM books where book_author ~ ?2)" +
                            ")  GROUP BY book_id) as b " +
                        "on books.book_id=b.book_id) ORDER BY c desc",nativeQuery = true)
    List<Books> searchVagueBooks(String name_regexp, String author_regexp);
}
