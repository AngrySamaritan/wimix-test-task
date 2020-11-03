package com.angrysamaritan.wimixtest.repositories;

import java.sql.Date;
import java.util.List;

public interface StatsRepository {

    List<Long> getCommunicativeUserIds(Date startDate, Date endDate);

}
