package com.backend_pg.controller;

import com.backend_pg.Repository.BorrowingRepository;
import com.backend_pg.entity.Borrowing;
import com.backend_pg.entity.Papers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/borrowing")
public class BorrowingHandler {

    @Autowired
    private BorrowingRepository borrowingRepository;


    // public static <T> Page<T> listConvertToPage(List<T> list, Pageable pageable) {
    //     if (pageable.getOffset() > list.size()) {
    //         long total = 0L;
    //         PageImpl<T> emptyPage = new PageImpl<T>(list, pageable, total);
    //         return emptyPage;
    //     }

    //     if (pageable.getOffset() <= list.size() && pageable.getOffset() + pageable.getPageSize() > list.size()) {
    //         List<T> bizPojos = list.subList((int)pageable.getOffset(), list.size());
    //         PageImpl<T> pPage = new PageImpl<T>(bizPojos, pageable, list.size());
    //         return pPage;
    //     }
    //     ////应该是其他情况
    //     List<T> ucShopCourseBizPojos = list.subList((int)pageable.getOffset(), (int)(pageable.getOffset() + pageable.getPageSize()));

    //     PageImpl<T> pPage = new PageImpl<T>(ucShopCourseBizPojos, pageable, list.size());

    //     return pPage;
    // }



    //查:分页查询
    @GetMapping("/all")
    public Page<Borrowing> findAllBorrowing(@RequestParam Integer pagenum, Integer pagesize){
        List<Borrowing> check = borrowingRepository.all();
        if (!check.isEmpty()) {
            Pageable pageable = PageRequest.of(pagenum - 1, pagesize);
            Page<Borrowing> res = listConvertToPage(check, pageable);
            return (res);
        }
        return null;

        //Pageable pageable= PageRequest.of(pagenum-1,pagesize);
        //return(borrowingRepository.findAll(pageable));
    }

    //查询借阅表：书名
    @GetMapping("/search/bookname")
    public List<Borrowing> searchBybookname(String book_name){
        return borrowingRepository.searchByBookName(book_name);
    }

    //查询借阅表：人名
    @GetMapping("/search/username")
    public List<Borrowing> searchByusername(String user_name){
        return borrowingRepository.searchByUserName(user_name);
    }


    //内部使用
    //借书时内部使用
    public Integer borrow_insert(Integer book_id,String book_name,String book_author,String isbn,String user_email,String user_name,Integer action_type){
        Borrowing tmp = new Borrowing();

        //tmp
        tmp.setBook_id(book_id);
        tmp.setBook_name(book_name);
        tmp.setBook_author(book_author);
        tmp.setIsbn(isbn);
        tmp.setUser_email(user_email);
        tmp.setUser_name(user_name);
        tmp.setAction_type(action_type);            //0表示借走，1表示还书

        tmp.setAction_id(borrowingRepository.borrow_newestId()+1);

        //设置日期
        Timestamp tmp1=new Timestamp(System.currentTimeMillis());
        tmp.setAction_date(tmp1);

        if (borrowingRepository.save(tmp)!=null){
            return 1;
        }
        else{
            return 0;
        }
    }

    //还书时内部使用
    public Integer return_insert(Integer book_id,String book_name,String book_author,String isbn,String user_email,String user_name,Integer action_type){
        Borrowing tmp = new Borrowing();

        //tmp
        tmp.setBook_id(book_id);
        tmp.setBook_name(book_name);
        tmp.setBook_author(book_author);
        tmp.setIsbn(isbn);
        tmp.setUser_email(user_email);
        tmp.setUser_name(user_name);
        tmp.setAction_type(action_type);            //1表示还书

        tmp.setAction_id(borrowingRepository.borrow_newestId()+1);

        //设置日期
        Timestamp tmp1=new Timestamp(System.currentTimeMillis());
        tmp.setAction_date(tmp1);

        if (borrowingRepository.save(tmp)!=null){
            //System.out.println("why so serious");
            return 1;
        }
        else{
            return 0;
        }
    }




}
