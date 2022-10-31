package com.backend_pg.Repository;

import com.backend_pg.controller.LocationHandler;
import com.backend_pg.entity.Books;
import com.backend_pg.entity.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;

@SpringBootTest
class BooksRepositoryTest {

    @Autowired
    private BooksRepository booksRepository;        //每一个book的声明都需要这个东西
    @Autowired
    private LocationHandler locationHandler;

    //@Test
    //void getLoc(){
    //    Location loc=locationHandler.pop();
    //    System.out.println(loc.getLoc());
    //}


    ////最简单的查
    //@Test
    // void findAll(){
    //    System.out.println(booksRepository.findAll());
    //}
    //
    ////一般般的查
    //@Test
    //void findByID(){
    //    Books book=booksRepository.findById(100).get();
    //    //这里提示是否有ispresent检测，想必是能检测是否为空的，可以用
    //    System.out.println(book);
    //}
    //
    ////@Test
    ////void findByAuthor(){
    ////    List<Books> book=booksRepository.findByAuthor("zwx");
    ////    System.out.println(book);
    ////}
    //
    ////增
    //@Test
    //void save(){
    //    Books book=new Books();
    //    book.setBook_id(100);
    //    book.setBook_author("zwx");
    //    book.setBook_name("无爱无家");
    //    book.setIsbn("ab-cd-ef");
    //    book.setBorrow_count(10086);
    //    book.setState(true);
    //    Timestamp tmp=new Timestamp(System.currentTimeMillis());
    //    book.setCreate_date(tmp);
    //    Books book1=booksRepository.save(book);
    //    System.out.println(book1);
    //}
    //
    ////改：就是存
    ////正确的思路是，先check是否有这本书，如果有的话就把所有信息丢给new出来的book，如果妹有store不也一样
    //@Test
    //void update(){
    //
    //    Books book = new Books();
    //    book.setBook_id(100);
    //    book.setBook_author("周伟贤");
    //    Books book1 = booksRepository.save(book);
    //    System.out.println(book1);
    //}
    ////结果其实是有fake的：Books(book_id=100, book_name=null, book_author=周伟贤, isbn=null, borrow_count=null)
    ////妹有取原来信息，就丢失了
    //
    ////删
    //@Test
    //void delete(){
    //    booksRepository.deleteById(100);
    //}


}