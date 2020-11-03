package com.angrysamaritan.wimixtest.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

@Repository
public class StatsRepositoryImpl implements StatsRepository {

    private final EntityManager entityManager;

    @Autowired
    public StatsRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Long> getCommunicativeUserIds(Date startDate, Date endDate) {

        try {
            Long maxMessagesCount = getMaxMessagesCount(startDate, endDate);

            return getUserIds(startDate, endDate, maxMessagesCount);
        } catch (NoResultException ignored) {
            return new LinkedList<>();
        }

    }

    private List<Long> getUserIds(Date startDate, Date endDate, Long maxMessagesCount) {
        TypedQuery<Long> userIdsQuery = entityManager.createQuery(
                "SELECT u.id FROM User u JOIN Message m " +
                        "ON (u.id = m.recipient.id OR u.id = m.sender.id) " +
                        "AND (m.date BETWEEN :startDate AND :endDate)" +
                        "GROUP BY u.id " +
                        "HAVING COUNT(m.id) = :maxMessagesCount", Long.class);
        userIdsQuery.setParameter("maxMessagesCount", maxMessagesCount);
        userIdsQuery.setParameter("startDate", startDate);
        userIdsQuery.setParameter("endDate", endDate);
        return userIdsQuery.getResultList();
    }

    private Long getMaxMessagesCount(Date startDate, Date endDate) {
        TypedQuery<Long> maxMessagesQuery = entityManager.createQuery(
                "SELECT SUM(m.messagesCount) AS messageCount " +
                        "FROM MessagesAmountView m " +
                        "WHERE m.messagesDate BETWEEN :startDate AND :endDate " +
                        "GROUP BY (m.userId) " +
                        "ORDER BY messageCount DESC", Long.class).setMaxResults(1);
        maxMessagesQuery.setParameter("startDate", startDate);
        maxMessagesQuery.setParameter("endDate", endDate);
        return maxMessagesQuery.getSingleResult();
    }
}
