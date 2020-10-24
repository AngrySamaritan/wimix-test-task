package com.angrysamaritan.wimixtest.repositories;

import com.angrysamaritan.wimixtest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getUserByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.registrationDate BETWEEN :startDate AND :endDate")
    List<User> getRegisteredUsersByPeriod(Date startDate, Date endDate);

//    @Query("SELECT u FROM User u JOIN WHERE (u.sentMessages.size + u.receivedMessages.size) = " +
//            "(SELECT (u.receivedMessages.size + u.sentMessages.size) FROM u JOIN Message m " +
//            "WHERE m.date BETWEEN :startDate AND :endDate GROUP BY " +
//            "(u.receivedMessages.size + u.sentMessages.size)) ")
//    List<User> getTheMostCommunicativeUsers();
}
