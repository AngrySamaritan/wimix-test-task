package com.angrysamaritan.wimixtest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

public class StatsRequest {

    @Getter
    @Setter
    @JsonProperty("start_date")
    private Date startDate;

    @Getter
    @Setter
    @JsonProperty("end_date")
    private Date endDate;
}
