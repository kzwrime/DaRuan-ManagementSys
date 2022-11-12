package com.backend_pg.Repository;

import com.backend_pg.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocationRepository extends JpaRepository<Location,String>{
    @Query(value = "select * from location limit 1",nativeQuery = true)
    Location findfirst();
}
