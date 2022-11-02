package com.backend_pg.controller;


import com.alibaba.fastjson.JSON;
import com.backend_pg.Repository.UsersRepository;
import com.backend_pg.entity.Books;
import com.backend_pg.entity.Papers;
import com.backend_pg.entity.Users;
import org.apache.catalina.User;
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



    //登陆
    @GetMapping("/login")
    public Integer login(@RequestParam String email, String passwd){
        Integer ans=0;              //默认下为0
        Optional<Users> check=usersRepository.findById(email);
        if (!check.isPresent()) {
            //System.out.println("Fake");
            return 0;
        }
        else{
            Users person=check.get();
            String realPasswd=person.getPasswd();
            Integer status = person.getAdmin();
            if (realPasswd.equals(passwd)) {
                ans = 1;               //在库,接下来判断是admin/普通user
                //System.out.println("User!ans="+ans);
                if (status == 1) {
                    ans = 10086;
                    //System.out.println("Admin!ans="+ans);
                }
            }
        }
        return ans;
    }


    //注册
    @PostMapping("/register")
    public Integer register(@RequestParam String checkEmail,String pass,String Name,String usrId){
        Optional<Users> check=usersRepository.findById(checkEmail);
        if (check.isPresent())          //已存在用户
            return -1;
        else{
            Users temp=new Users();
            temp.setAdmin(0);           //默认非管理
            temp.setUser_email(checkEmail);
            temp.setPasswd(pass);
            temp.setUser_name(Name);
            temp.setUsrid(usrId);
            Users res=usersRepository.save(temp);
            if (res!=null)
                //System.println(res);
                return 1;               //注册成功
            else
                return 0;              //注册失败，请稍后重试
        }
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
            Page<Users> res = listConvertToPage(check, pageable);
            return (res);
        }
        return null;
    }

    //查找：email查找：只会返回single，所以只需要一个User
    @GetMapping("/search/email")
    public Users searchByID(@RequestParam String email){
        Optional<Users> res=usersRepository.findById(email);
        if (res.isPresent())
            return res.get();
        else
            return null;
    }

    //查找：学生名字查找
    @GetMapping("/search/name")
    public List<Users> searchByName(@RequestParam String name){
        List<Users> res=usersRepository.searchByName(name);
        return  res;
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
            Users ans = usersRepository.save(tmp);
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
