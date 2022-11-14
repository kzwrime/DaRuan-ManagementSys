package com.backend_pg.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Users {
    // entity: users
    // good for u
    @Id
    private String user_email;
    private String user_name;
    private String passwd;
    private String usrid;
    private Integer admin;
}
