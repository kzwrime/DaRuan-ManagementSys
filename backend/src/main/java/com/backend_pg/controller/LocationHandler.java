package com.backend_pg.controller;

import com.backend_pg.Repository.LocationRepository;
import com.backend_pg.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class LocationHandler {

    @Autowired
    private LocationRepository locationRepository;

    public Location pop(){
        Location tmp=locationRepository.findfirst();
        String loc = tmp.getLoc();
        locationRepository.deleteById(loc);
        return tmp;
    }

    public void push(String loc){
        Location tmp=new Location();
        tmp.setLoc(loc);
        locationRepository.save(tmp);
    }
}
