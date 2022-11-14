package com.backend_pg.controller;

import com.backend_pg.Repository.BooksRepository;
import com.backend_pg.Repository.LocationRepository;
import com.backend_pg.Repository.UsersRepository;
import com.backend_pg.entity.Books;
import com.backend_pg.entity.Location;
import com.backend_pg.entity.Users;
import net.bytebuddy.TypeCache;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.awt.print.Book;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
//@ResponseBody
public class BooksHandler {                 //实现bonding page对应的功能
    // this is bookshandler
    @Autowired
    private BooksRepository booksRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private LocationHandler locationHandler;
    @Autowired
    private BorrowingHandler borrowingHandler;
    //@Autowired
    //private CommonTools commonTools;

    //public static <T> Page<T> listConvertToPage1(List<T> list, Pageable pageable) {
    //    int start=(int)pageable.getOffset();
    //    //int start =start_long.intValue();
    //    int end = (start + pageable.getPageSize()) > list.size() ? list.size() : (start + pageable.getPageSize());
    //    return new PageImpl<T>(list.subList(end, start), pageable, list.size());
    //}

    public static <T> Page<T> listConvertToPage(List<T> list, Pageable pageable) {
        if (pageable.getOffset() > list.size()) {
            long total = 0L;
            PageImpl<T> emptyPage = new PageImpl<T>(list, pageable, total);
            return emptyPage;
        }

        if (pageable.getOffset() <= list.size() && pageable.getOffset() + pageable.getPageSize() > list.size()) {
            List<T> bizPojos = list.subList((int)pageable.getOffset(), list.size());
            PageImpl<T> pPage = new PageImpl<T>(bizPojos, pageable, list.size());
            return pPage;
        }
        ////应该是其他情况
        List<T> ucShopCourseBizPojos = list.subList((int)pageable.getOffset(), (int)(pageable.getOffset() + pageable.getPageSize()));

        PageImpl<T> pPage = new PageImpl<T>(ucShopCourseBizPojos, pageable, list.size());

        return pPage;
    }

    @PostMapping("/searchVague")
    public List<Books> searchVagueBooks(@RequestParam String authors,@RequestParam String names){
        String[] names_split=names.split(",");
        Long cnt_n_long = Arrays.stream(names_split).count();
        int cnt_n = cnt_n_long.intValue();

        String str1 = "(";           //(ab)
        String str2 = "(";           //(a.*b)
        String str3 = "(";           //a|b

        for (int i=0;i<cnt_n;i++){
            str1+=names_split[i];                       //最后应为(abc
            str2 = str2 + names_split[i] + ".*";        //最后应为(a.*b.*c.*
            //str3+= names_split[i];
        }
        for (int i=0;i<cnt_n-1;i++){
            str3 = str3 + names_split[i] +"|";          //最后应为(a|b|
        }
        str1+=")";                                      //(abc)
        str2+=")";                                      //(a.*b.*c.*)
        str3 = str3 + names_split[cnt_n-1]+")";         //(a|b|c)
        String name_regexp = str1+ "|" + str2 + "|" +str3;            //这个是书名

        //作者名划分
        String[] authors_split=authors.split(",");
        Long cnt_a_long = Arrays.stream(authors_split).count();
        int cnt_a = cnt_a_long.intValue();

        //目标：(LeiJun | Ma Dongmei | Xia Luo)
        String author_regexp = "(";
        for (int i=0;i<cnt_a-1;i++){
            author_regexp = author_regexp + authors_split[i] + "|";         //Expected: (LJ|MDm|
        }
        author_regexp = author_regexp + authors_split[cnt_a-1]+ ")";        //Expected: (LJ|Mdm|XL)

        System.out.println(name_regexp);
        System.out.println(author_regexp);
        List<Books> ans = booksRepository.searchVagueBooks(name_regexp,author_regexp);
        //Pageable pageable = PageRequest.of(pagenum - 1, pagesize);
        //Page<Books> res = listConvertToPage(ans, pageable);
        return ans;
    }

    /********************
     *一进来的页面：映入眼帘*
     ********************/

    //查:按页查找

    ////分页查询——指定版
    //@GetMapping("/all/{page}/{size}")
    //public Page<Books> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
    //    Pageable pageable= PageRequest.of(page-1,size);
    //    return(booksRepository.findAll(pageable));
    //}

    //分页查询——乱参版
    @GetMapping("/all")
    public Page<Books> findAllBooks(@RequestParam Integer pagenum,Integer pagesize){
        //Pageable pageable= PageRequest.of(pagenum-1,pagesize,Sort.by(Sort.Direction.ASC,"book_id"));
        List<Books> check = booksRepository.all();
        if (!check.isEmpty()) {
            Pageable pageable = PageRequest.of(pagenum - 1, pagesize);
            Page<Books> res = listConvertToPage(check, pageable);
            return (res);
        }
        return null;
    }


    /****************
     * Retrieve：查 *
     * 查找书本API  *
     **************/

    //精确查找书本
    @GetMapping("/search/exact")
    public List<Books> searchExactBooks1(@RequestParam String keywords){
        List<Books> check=booksRepository.searchExactBooks(keywords);
        //if (!check.isEmpty()){
        //    //System.out.println(pagenum);
        //    //System.out.println(pagesize);
        //    //Pageable pageRequest = PageRequest.of(pagenum-1,pagesize);
        //    Page<Books> res = listConvertToPage(check, pageRequest);
        //    //System.out.println(res);
        //    return res;
        //}
        //else
        //    return null;
        return check;
    }

    //模糊搜索（待plus）

    /***************
     * Create：增  *
     **************/

    //增：新书
    @PostMapping("/insert")
    public String insert(@RequestParam String book_name, String book_author, String isbn,String intro){
        Books tmp=new Books();

        //根据传过来的set
        tmp.setBook_name(book_name);
        tmp.setBook_author(book_author);
        tmp.setIsbn(isbn);
        tmp.setIntro(intro);

        //自动set
        tmp.setBorrow_count(0);
        tmp.setState(1);
        tmp.setBook_holder("0");

        //设置日期
        Timestamp tmp1=new Timestamp(System.currentTimeMillis());
        tmp.setCreate_date(tmp1);

//////////////////////////////////////////////////////////////////////////////////////////////////////
        //任何的交互都需要加框架

        //设置book_id
        Integer num=booksRepository.newestBookID();
        tmp.setBook_id(num+1);

        //设置location
        Location loc=locationHandler.pop();
        if (loc.getLoc()==null){
            return "error";             //没有空位，这个再协商
        }
        String loc_str = loc.getLoc();
        tmp.setLocate(loc_str);

        Books result= booksRepository.save(tmp);
/////////////////////////////////////////////////////////////////////////////////////////////
        //后期加框架
        if (result !=null){
            return "success";
        }
        else{
            return "error";
        }
    }

    //改：书本信息
    @PutMapping("/update")
    public String updateBooks(@RequestParam Integer book_id, @RequestParam String book_name,@RequestParam String book_author,@RequestParam String isbn,@RequestParam String intro){
        Books temp= new Books();
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Optional<Books> res= booksRepository.findById(book_id);     //这里传的ID必定是对的，没有关系
        if (res.isPresent()){
            temp = res.get();           //拿到旧book的信息
            temp.setBook_name(book_name);
            temp.setBook_author(book_author);
            temp.setIsbn(isbn);
            temp.setIntro(intro);
            System.out.println(intro);
            System.out.println(isbn);


            //这先别，按照原来的来,确实是ok的
            //temp.setBook_id(book_id);
            //temp.setCreate_date();
            //temp.setBorrow_count();
            //temp.setBook_holder(holder);

            booksRepository.save(temp);
            return "success";
        }
        return "error:";
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    //用来借阅表的set state和borrow_count
    public Integer update1(Integer book_id, Integer state,String holder){
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Optional<Books> res= booksRepository.findById(book_id);     //这里传的ID必定是对的，没有关系
        if (res.isPresent()){
            Books temp = res.get();

            Integer old_count= temp.getBorrow_count();
            temp.setBorrow_count(old_count+1);

            temp.setState(state);
            temp.setBook_holder(holder);

            booksRepository.save(temp);
            return 1;
        }
        return 0;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    //删：删除书本
    //妹有返回值是个大问题,但是里面说会返回个空的200到前端？
    @DeleteMapping("/delete")
    public Integer delete(@RequestParam Integer book_id){
/////////////////////////////////////////////////////////////////////////////////////////////
        Optional<Books> check=booksRepository.findById(book_id);
        if(check.isPresent()){    //照例判断一下而已
            Books tmp = check.get();
            String loc = tmp.getLocate();
            locationHandler.push(loc);
        }
        booksRepository.deleteById(book_id);
        return 1;
        //否则中间出错再return 0，这个放在exception里面吧
//////////////////////////////////////////////////////////////////////////////////////////
    }

    /**************
     *  借还书流程  *
     **************/

    //查：自己的负债清单
    @GetMapping("/rentbook")
    public List<Books> rentBooks1(String user_email){
        List<Books> tmp = booksRepository.rentBooks(user_email);
        if (!tmp.isEmpty()){
            return tmp;
        }
        else{
            List<Books> tmp1 = new ArrayList<>();
            Books books= new Books();
            books.setBook_id(-2);
            books.setIntro("NoBookUsb");
            tmp1.add(books);
            return tmp1;
        }
    }


    //@GetMapping("/tryonetry")
    //public List<Books> wtf(){
    //    return null;
    //}

    //借书
    @GetMapping("/borrowbook")
    public Integer borrowBook(@RequestParam Integer book_id,String user_email){
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Optional<Users> check0 = usersRepository.findById(user_email);
        String user_name = "";
        if (check0.isPresent()){
            Users tmp = check0.get();
            user_name = tmp.getUser_name();
        }

        Optional<Books> check = booksRepository.findById(book_id);
        if (check.isPresent()){                                     //循例检查，能click借的就应该一定会有
            Books temp1=check.get();
            Integer state=temp1.getState();
            //System.out.println(state);
            if (state == 0)                                         //被借走了
                return -1;
            else{                                                   //可以借
                //Integer book_id = temp1.getBook_id();
                String book_name=temp1.getBook_name();
                String book_author=temp1.getBook_author();
                String isbn=temp1.getIsbn();

                //books书本信息更新
                Integer res0 = update1(temp1.getBook_id(),0,user_email);
                if (res0 == 0)
                    return 0;

                //borrowing插入条目
                Integer res = borrowingHandler.borrow_insert(book_id,book_name,book_author,isbn,user_email,user_name,0);
                if (res == 1)
                    return 1;
                else
                    return 0;
                //如果ok的话，return 1；
                //不ok的话，return 0（Exception）
////////////////////////////////////////////////////////////////////////////////////////////////////////////
            }
        }
        return  0;      //为毛一定要在这里写。。。
    }

    //还书：相同操作，再说吧，user_email+user_name+书本信息即可
    @GetMapping("/returnbook")
    public List<Books> returnBook(@RequestParam Integer book_id,String user_email){
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Optional<Users> check0 = usersRepository.findById(user_email);
        String user_name = "";
        if (check0.isPresent()){
            Users tmp = check0.get();
            user_name = tmp.getUser_name();
        }

        Optional<Books> check = booksRepository.findById(book_id);
        if (check.isPresent()){                                     //循例检查，能click还的就应该一定会有
            Books temp1=check.get();
            Integer state=temp1.getState();
            if (state == 1){                                        //这特么都在在馆了必然出大问题，实在不行remove掉这个判断
                List<Books> tmp = new ArrayList<>();
                Books books= new Books();
                books.setBook_id(-1);
                books.setIntro("sb");
                tmp.add(books);
                return tmp;
            }
            else{                                                   //可以还
                String book_name=temp1.getBook_name();
                String book_author=temp1.getBook_author();
                String isbn=temp1.getIsbn();

                Integer res0 = update1(temp1.getBook_id(),1,"0");                 //books书本信息更新,持有者置0
                if (res0 == 0){
                    List<Books> tmp = new ArrayList<>();
                    Books books= new Books();
                    books.setBook_id(-1);
                    books.setIntro("sb");
                    tmp.add(books);
                    return tmp;
                }

                Integer res = borrowingHandler.return_insert(book_id,book_name,book_author,isbn,user_email,user_name,1);
                if (res == 1) {               //说明还书成功
                    return rentBooks1(user_email);
                }
                else{
                    List<Books> tmp = new ArrayList<>();
                    Books books= new Books();
                    books.setBook_id(-1);
                    books.setIntro("sb");
                    tmp.add(books);
                    return tmp;
                }
                //如果ok的话，return 1；
                //不ok的话，return 0（Exception）
////////////////////////////////////////////////////////////////////////////////////////////////////////////
            }
        }
        else {
            List<Books> tmp = new ArrayList<>();
            Books books = new Books();
            books.setBook_id(-1);
            books.setIntro("sb");
            tmp.add(books);
            return tmp;
        }
    }
}

