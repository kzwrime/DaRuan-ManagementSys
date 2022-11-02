package com.backend_pg.Repository;

import com.backend_pg.entity.Books;
import com.backend_pg.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users,String> {


    @Query(value = "select * from users WHERE TRUE ORDER BY users.user_email",nativeQuery = true)
    List<Users> all();


    @Query(value = "SELECT * FROM Users AS U WHERE U.user_name = ?1",nativeQuery = true)
    List<Users> searchByName(String name);
}
