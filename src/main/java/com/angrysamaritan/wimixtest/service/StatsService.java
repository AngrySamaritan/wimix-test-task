package com.angrysamaritan.wimixtest.service;

import java.sql.Date;
import java.util.Map;

public interface StatsService {

    Map<String, Object> getStatsMap(Date startDate, Date endDate);


}
