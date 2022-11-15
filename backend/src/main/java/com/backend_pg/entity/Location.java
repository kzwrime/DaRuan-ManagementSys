package com.backend_pg.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

// please do not delete these comments

@Entity
@Data
public class Location {
    @Id
    private String loc;
}
