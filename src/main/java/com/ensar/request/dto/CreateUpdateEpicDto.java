package com.ensar.request.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.ensar.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ensar.entity.Epic;
import com.ensar.entity.Epic.Priority;

@ApiModel(value = "CreateUpdateEpicDto", description = "Parameters required to create/update epic")
@Accessors(chain = true)
@Setter
@Getter
public class CreateUpdateEpicDto {

    
	@ApiModelProperty(notes = "Epic Name", required = true)
    @NotBlank(message = "Epic Name is required")
    private String name;

    @ApiModelProperty(notes = "Epic Description")
    private String description;
    
    @ApiModelProperty(notes = "Epic Accptance Criteria")
    private String accptanceCriteria;
    

    @ApiModelProperty(notes = "Epic priority")
    private Epic.Priority priority;
    
    @ApiModelProperty(notes = "Epic Original Estimate")
    private Integer originalEstimate;
    
    @ApiModelProperty(notes = "Epic Points")
    private Integer points;
    
    @ApiModelProperty(notes = " Epic Remainng Estimate")
    private Integer remainngEstimate;
    
    @ApiModelProperty(notes = "start_date")
    private Date startDate;
    
    @ApiModelProperty(notes = "end_date")
    private Date endDate;
    
    @ApiModelProperty(notes =  "due_date")
    private Date dueDate;
    

    @ApiModelProperty(notes = "Epic created by")
    private String createdBy;
    
    @ApiModelProperty(notes = "Epic feature ")
    private String feature;
    
    @ApiModelProperty(notes = "Epic release ")
    private String release;
    
    @ApiModelProperty(notes = "Epic comment")
    private String comment;
    

}
