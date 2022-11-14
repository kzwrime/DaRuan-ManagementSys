package com.backend_pg.controller;

import com.backend_pg.Repository.PapersRepository;
import com.backend_pg.entity.Papers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/papers")
public class PapersHandler {

    @Autowired
    private PapersRepository papersRepository;

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

    @PostMapping("/searchVague")
    //@ResponseBody
    public List<Papers> searchVaguePapers1(@RequestParam String authors,@RequestParam String names){
        String[] names_split=names.split(",");
        long cnt_n_long = Arrays.stream(names_split).count();
        int cnt_n = (int) cnt_n_long;

        StringBuilder str1 = new StringBuilder("(");           //(ab)
        StringBuilder str2 = new StringBuilder("(");           //(a.*b)
        StringBuilder str3 = new StringBuilder("(");           //a|b

        for (int i=0;i<cnt_n;i++){
            str1.append(names_split[i]);                       //最后应为(abc
            str2.append(names_split[i]).append(".*");        //最后应为(a.*b.*c.*
            //str3+= names_split[i];
        }
        for (int i=0;i<cnt_n-1;i++){
            str3.append(names_split[i]).append("|");          //最后应为(a|b|
        }
        str1.append(")");                                      //(abc)
        str2.append(")");                                      //(a.*b.*c.*)
        str3.append(names_split[cnt_n - 1]).append(")");         //(a|b|c)
        String name_regexp = str1+ "|" + str2 + "|" +str3;            //这个是书名

        //作者名划分
        String[] authors_split=authors.split(",");
        long cnt_a_long = Arrays.stream(authors_split).count();
        int cnt_a = (int) cnt_a_long;

        //目标：(LeiJun | Ma Dongmei | Xia Luo)
        StringBuilder author_regexp = new StringBuilder("(");
        for (int i=0;i<cnt_a-1;i++){
            author_regexp.append(authors_split[i]).append("|");         //Expected: (LJ|MDm|
        }
        author_regexp.append(authors_split[cnt_a - 1]).append(")");        //Expected: (LJ|Mdm|XL)

        return papersRepository.searchVaguePapers(name_regexp, author_regexp.toString());
    }


    //查:分页查询
    @GetMapping("/all")
    public Page<Papers> findAllPapers(@RequestParam Integer pagenum, Integer pagesize){
        List<Papers> check = papersRepository.all();
        //Pageable pageable= PageRequest.of(pagenum-1,pagesize);
        if (!check.isEmpty()) {
            Pageable pageable = PageRequest.of(pagenum - 1, pagesize);
            return (listConvertToPage(check, pageable));
        }
        return null;
    }

    /****************
     * Retrieve：查 *
     * 查找论文API  *
     **************/

    //精确查找论文
    @PostMapping("/search/exact")
    public List<Papers> searchExactPapers1(@RequestParam String keywords){
        List<Papers> res=papersRepository.searchExactPapers(keywords);
        if (!res.isEmpty())
            return res;
        else
            return null;
    }



    /***************
     * Create：增  *
     **************/


    //增：新论文
    @PostMapping("/insert")
    public String insert(@RequestParam String paper_name, String paper_author, String paper_add,String paper_intro,Integer publish_year){
        Papers tmp = new Papers();

        //根据传过来的set

        tmp.setPaper_name(paper_name);
        tmp.setPaper_author(paper_author);
        tmp.setPaper_url(paper_add);
        tmp.setPaper_intro(paper_intro);
        tmp.setPaper_year(publish_year);

        //自动set
        tmp.setPaper_click_times(0);

//////////////////////////////////////////////////////////////////////////////////////////////////////
        //任何的交互都需要加框架

        //设置book_id
        Integer num=papersRepository.newestPaperID();
        tmp.setPaper_id(num+1);

        papersRepository.save(tmp);
/////////////////////////////////////////////////////////////////////////////////////////////
        //后期加框架
        return "success";
    }


    //统计点击次数
    @GetMapping("/clickclick")
    public void clickplus1(@RequestParam Integer paper_id){
        Optional<Papers> check = papersRepository.findById(paper_id);
        if (check.isPresent()){
            Papers tmp = check.get();
            Integer num = tmp.getPaper_click_times();
            //System.out.println(num);
            tmp.setPaper_click_times(num+1);
            papersRepository.save(tmp);
        }
    }

    //改：Paper信息
    @PutMapping("/update")
    public String updatePapers(@RequestParam Integer paper_id, String paper_name, String paper_author, String paper_add, String paper_intro, Integer publish_year, Integer paper_click_times){
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Optional<Papers> check= papersRepository.findById(paper_id);
        if (check.isPresent()){         //这里传的ID必定是对的，没有关系
            Papers tmp = check.get();           //拿到旧book的信息
            tmp.setPaper_name(paper_name);
            tmp.setPaper_author(paper_author);
            tmp.setPaper_url(paper_add);
            tmp.setPaper_intro(paper_intro);
            tmp.setPaper_year(publish_year);
            tmp.setPaper_click_times(paper_click_times);

            //id什么的不要改

            papersRepository.save(tmp);
            return "success";
        }
        return "error:";
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }


    //删：删除论文
    //妹有返回值是个大问题,但是里面说会返回个空的200到前端？
    @DeleteMapping("/delete")
    public Integer deletePapers(@RequestParam Integer paper_id){
/////////////////////////////////////////////////////////////////////////////////////////////
        papersRepository.deleteById(paper_id);
        return 1;
        //否则中间出错再return 0，这个放在exception里面吧
//////////////////////////////////////////////////////////////////////////////////////////
    }



}
