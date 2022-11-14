package com.backend_pg.Repository;

import com.backend_pg.entity.Books;
import com.backend_pg.entity.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BorrowingRepository extends JpaRepository<Borrowing,Integer> {

    @Query(value = "select * from borrowing WHERE TRUE ORDER BY borrowing.action_id DESC",nativeQuery = true)
    List<Borrowing> all();

    @Query(value = "select MAX(action_id) FROM borrowing",nativeQuery = true)
    Integer borrow_newestId();

    @Query(value = "select * from borrowing as b where b.book_name=  ?1",nativeQuery = true)
    List<Borrowing> searchByBookName(String book_name);

    @Query(value = "select * from borrowing as b where b.user_name=?1",nativeQuery = true)
    List<Borrowing> searchByUserName(String user_name);
}
