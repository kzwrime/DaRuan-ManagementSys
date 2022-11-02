package com.backend_pg.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Papers {

    @Id
    private Integer paper_id;
    private String paper_name;
    private String paper_author;
    private String paper_intro;
    private String paper_url;
    private Integer paper_year;
    private Integer paper_click_times;
}
