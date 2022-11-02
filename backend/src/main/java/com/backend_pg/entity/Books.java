package com.backend_pg.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Generated;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
public class Books {        //类名首字母小写==表名，就可以自动绑定

    @Id                     //主键
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "book_id", nullable = false)
    private Integer book_id;

    private String book_name;
    private String book_author;
    private String isbn;
    private Integer borrow_count;
    private Integer state;
    private String locate;  //位置
    private String intro;
    private String book_holder;


    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss", timezone = "GMT+8")
    private Timestamp create_date;

}
