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
import com.ensar.entity.Story;


@ApiModel(value = "CreateUpdateStoryDto", description = "Parameters required to create/update story")
@Accessors(chain = true)
@Setter
@Getter
public class CreateUpdateStoryDto {

    
	@ApiModelProperty(notes = "Story Name", required = true)
    @NotBlank(message = "Story Name is required")
    private String name;

    @ApiModelProperty(notes = "Story Description")
    private String description;
    
    @ApiModelProperty(notes = "Story Accptance Criteria")
    private String accptanceCriteria;
    

    @ApiModelProperty(notes = "Story priority")
    private Story.Priority priority;
    
    @ApiModelProperty(notes = "original_estimate")
    private Integer originalEstimate;
    
    @ApiModelProperty(notes = "points")
    private Integer points;
    
    @ApiModelProperty(notes = "remainng_estimate")
    private Integer remainngEstimate;
    
    @ApiModelProperty(notes = "remainng_estimate")
    private Long epic;
    

    @ApiModelProperty(notes = "Story created by", required = true)
    private String createdBy;
    
    @ApiModelProperty(notes = "Story feature ", required = true)
    private String feature;
    
    @ApiModelProperty(notes = "Story release ", required = true)
    private String release;
    
    @ApiModelProperty(notes = "Story comment", required = true)
    private String comment;
    

}
