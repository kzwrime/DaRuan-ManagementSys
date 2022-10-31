package com.backend_pg;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class BackendPgApplicationTests {


	//@Test
	//void contextLoads() {
	//	String authors="KarmenRider,你是谁,wdnmd";
	//	String[] authors_split=authors.split(",");
	//	Long cnt_a = Arrays.stream(authors_split).count();
	//	for (int i=0;i<cnt_a;i++)
	//		System.out.println(authors_split[i]);
	//	//System.out.println(Arrays.stream(a).count());
	//}

    //public static <T> Page<T> listConvertToPage1(List<T> list, Pageable pageable) {
    //    Long start_long=pageable.getOffset();
    //    int start =start_long.intValue();
    //    int end = (start + pageable.getPageSize()) > list.size() ? list.size() : (start + pageable.getPageSize());
    //    return new PageImpl<T>(list.subList(start, end), pageable, list.size());
    //}

    //@Test
    //void converting(){
    //
    //    List<String> strings = new ArrayList<>();
    //    for (int i=0;i<20;i++){
    //        strings.add("第"+i+"数据");
    //    }
    //    Pageable pageRequest = PageRequest.of(1,15);
    //    Page<String> strings1 = listConvertToPage1(strings, pageRequest);
    //    System.out.println(strings1);
    //}
}
