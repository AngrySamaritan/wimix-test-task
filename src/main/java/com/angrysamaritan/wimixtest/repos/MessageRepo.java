package com.angrysamaritan.wimixtest.repos;

import com.angrysamaritan.wimixtest.model.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface MessageRepo extends CrudRepository<Message, Long> {

    @Query("SELECT m FROM Message m JOIN m.sender s ON s.id = :userId WHERE m.date BETWEEN :startDate AND :endDate")
    List<Message> getSentMessagesOfUser(long userId, Date startDate, Date endDate);

    @Query("SELECT m FROM Message m JOIN m.recipient r ON r.id = :userId WHERE m.date BETWEEN :startDate AND :endDate")
    List<Message> getReceivedMessagesOfUserByPeriod(long userId, Date startDate, Date endDate);
}
