package com.angrysamaritan.wimixtest.repositories;
import com.angrysamaritan.wimixtest.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u join u.profile p where p.firstName = :firstName")
    Page<User> getUsersByFirstName(String firstName, Pageable pageable);


    User getUserByUsername(String username);


    @Query("SELECT u FROM User u WHERE u.registrationDate BETWEEN :startDate AND :endDate")
    List<User> getRegisteredUsersByPeriod(Date startDate, Date endDate);
}
