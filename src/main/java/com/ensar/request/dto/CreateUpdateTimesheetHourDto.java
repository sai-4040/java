package com.ensar.request.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.ensar.entity.User;
import com.ensar.entity.Epic;
import com.ensar.entity.Epic.Priority;

@ApiModel(value = "CreateUpdateTimesheetHourDto", description = "Parameters required to create/update epic")
@Accessors(chain = true)
@Setter
@Getter
public class CreateUpdateTimesheetHourDto {

    @ApiModelProperty(notes = "User", required = true)

    private Long user;

    @ApiModelProperty(notes = "comment")
    private String comment;

    @ApiModelProperty
    private String startAt;

    @ApiModelProperty
    private String endAt;

    @ApiModelProperty
    private String date;

    @ApiModelProperty(notes = "Epic created by", required = true)
    private Long timesheetProject;

    @ApiModelProperty(notes = "Epic feature ", required = true)
    private Long timesheetSubProject;

}
