package com.backend_pg.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;


// what happened here?
@Entity
@Data
public class Borrowing {
    @Id
    private Integer action_id;

    private Integer book_id;          //有用，在insert/remove条目的时候需要
    private String book_name;
    private String book_author;
    private String isbn;

    private String user_email;
    private String user_name;

    private Integer action_type;

    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss", timezone = "GMT+8")
    private Timestamp action_date;
}
