package com.angrysamaritan.wimixtest.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Map;

public interface StatsService {

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    Map<String, Object> getStatsMap(Date startDate, Date endDate);


}
