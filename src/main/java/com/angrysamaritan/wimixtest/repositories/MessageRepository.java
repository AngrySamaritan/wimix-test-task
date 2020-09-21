package com.angrysamaritan.wimixtest.repositories;

import com.angrysamaritan.wimixtest.model.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

    @Query(value = "SELECT * FROM message m WHERE (m.sender_id = :userId OR m.recipient_id = :userId) AND m.date" +
            " BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Message> getMessagesOfUserByPeriod(long userId, Date startDate, Date endDate);
}
