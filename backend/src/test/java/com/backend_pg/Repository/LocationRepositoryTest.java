package com.backend_pg.Repository;

import com.backend_pg.entity.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LocationRepositoryTest {

    @Autowired
    private LocationRepository locationRepository;

    //@Test
    // void pop(){
    //    Location tmp=locationRepository.pop();
    //    String wtf = tmp.getEmpty();
    //    locationRepository.deleteById(wtf);
    //    System.out.println(tmp);
    //    System.out.println(wtf);
    //    //return tmp;
    //}
}