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

    @Query(value = "SELECT u.id " +
            "FROM user u JOIN message m " +
            "ON (u.id = m.recipient_id OR u.id = m.sender_id) AND m.date BETWEEN :startDate and :endDate " +
            "GROUP BY u.id HAVING COUNT(m.id) = " +
            "(SELECT MAX(C) FROM (SELECT COUNT(message.id) AS C " +
            "FROM user JOIN message ON (user.id = message.recipient_id OR user.id = message.sender_id) " +
            "AND message.date BETWEEN :startDate and :endDate " +
            "GROUP BY user.id) as umC)", nativeQuery = true)
    List<Long> getTheMostCommunicativeUsers(Date startDate, Date endDate);
}
