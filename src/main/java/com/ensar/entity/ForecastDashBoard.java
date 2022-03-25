package com.ensar.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity(name = "forecast_dash_board")
@Data
@EqualsAndHashCode(callSuper = true)
public class ForecastDashBoard extends BaseEntity {

    @Column(name = "organization_id")
    private Long organizationId;

    @Column(name = "dash_board_id")
    private Long dashBoardId;

    @Transient
    private String url;

}
