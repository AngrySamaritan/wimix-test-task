package com.angrysamaritan.wimixtest.repositories;

import com.angrysamaritan.wimixtest.model.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Repository

public interface MessageRepository extends CrudRepository<Message, Long> {

    @Query("SELECT m FROM Message m JOIN User u ON m.sender.id = u.id OR m.recipient.id = u.id " +
            "WHERE m.date BETWEEN :startDate AND :endDate AND u.id = :userId")
    List<Message> getMessagesOfUserByPeriod(long userId, Date startDate, Date endDate);
}
