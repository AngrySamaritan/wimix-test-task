package com.angrysamaritan.wimixtest.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Map;

public interface StatsService {

    Map<String, Object> getStatsMap(Date startDate, Date endDate);


}
