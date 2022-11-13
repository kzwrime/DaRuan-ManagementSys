package com.backend_pg.controller;


import com.backend_pg.Repository.UsersRepository;
import com.backend_pg.entity.Users;
import com.backend_pg.exception.ErrorCode;
import com.backend_pg.exception.RequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersHandler {

    @Autowired
    private UsersRepository usersRepository;

    public static <T> Page<T> listConvertToPage(List<T> list, Pageable pageable) {
        if (pageable.getOffset() > list.size()) {
            long total = 0L;
            return new PageImpl<>(list, pageable, total);
        }

        if (pageable.getOffset() <= list.size() && pageable.getOffset() + pageable.getPageSize() > list.size()) {
            List<T> bizPojos = list.subList((int)pageable.getOffset(), list.size());
            return new PageImpl<>(bizPojos, pageable, list.size());
        }
        ////应该是其他情况
        List<T> ucShopCourseBizPojos = list.subList((int)pageable.getOffset(), (int)(pageable.getOffset() + pageable.getPageSize()));

        return new PageImpl<>(ucShopCourseBizPojos, pageable, list.size());
    }



    //登陆
    @GetMapping("/login")
    public Integer login(@RequestParam String email, String passwd){
        Optional<Users> check=usersRepository.findById(email);
        if (check.isEmpty()) throw new RequestException(ErrorCode.USER_NOT_FOUND, email);
        Users person=check.get();
        String realPasswd=person.getPasswd();
        Integer status = person.getAdmin();
        if(!realPasswd.equals(passwd)) throw new RequestException(ErrorCode.BAD_CREDENTIALS);
        if(status == 1) return 10086;
        else return 1;
    }


    //注册
    @PostMapping("/register")
    public Integer register(@RequestParam String checkEmail,String pass,String Name,String usrId){
        Optional<Users> check=usersRepository.findById(checkEmail);
        if (check.isPresent())          //已存在用户
            throw new RequestException(ErrorCode.USERNAME_EXISTS, checkEmail);
        Users temp=new Users();
        temp.setAdmin(0);           //默认非管理
        temp.setUser_email(checkEmail);
        temp.setPasswd(pass);
        temp.setUser_name(Name);
        temp.setUsrid(usrId);
        usersRepository.save(temp);
        return 0;
    }


    /*
     * 管理员页面下：——这个后端是否要做校验？
     *
     */

    //获取所有的User信息
    //@GetMapping("/all")
    //public List<Users> all(){
    //    return usersRepository.findAll();
    //}

    @GetMapping("/all")
    public Page<Users> findAllBooks(@RequestParam Integer pagenum,Integer pagesize){
        //Pageable pageable= PageRequest.of(pagenum-1,pagesize);
        //return(usersRepository.findAll(pageable));
        List<Users> check = usersRepository.all();
        //Pageable pageable= PageRequest.of(pagenum-1,pagesize);
        if (!check.isEmpty()) {
            Pageable pageable = PageRequest.of(pagenum - 1, pagesize);
            return (listConvertToPage(check, pageable));
        }
        return null;
    }

    //查找：email查找：只会返回single，所以只需要一个User
    @GetMapping("/search/email")
    public Users searchByID(@RequestParam String email){
        Optional<Users> res=usersRepository.findById(email);
        return res.orElse(null);
    }

    //查找：学生名字查找
    @GetMapping("/search/name")
    public List<Users> searchByName(@RequestParam String name){
        return usersRepository.searchByName(name);
    }

    //修改学生信息
    @PutMapping("/update")
    public Integer update(@RequestParam String email,String name,String passwd,Integer admin,String usrid){
        Optional<Users> res=usersRepository.findById(email);
        if (res.isPresent()) {                      //循例问问，已有条目的话怎么可能没有呢
            Users tmp = res.get();
            tmp.setUser_email(email);
            tmp.setUser_name(name);
            tmp.setPasswd(passwd);
            tmp.setAdmin(admin);                //这个要考虑，如果是admin的话前端传1
            tmp.setUsrid(usrid);
            usersRepository.save(tmp);
            return 1;
        }
        else{
            return 0;
        }
    }


    //删除：小心就好
    @DeleteMapping("/admin/delete")
    public Integer delete(@RequestParam String email){
        usersRepository.deleteById(email);
        return 1;
    }
}
