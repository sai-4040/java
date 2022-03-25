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
import com.ensar.entity.Testcase.Status;
import com.ensar.entity.Testcase;


@ApiModel(value = "CreateUpdateTestcaseDto", description = "Parameters required to create/update testcase")
@Accessors(chain = true)
@Setter
@Getter
public class CreateUpdateTestcaseDto {

    @ApiModelProperty(notes = "Testcase testId")
  
    private String testId;
    
    @ApiModelProperty(notes = "Testcase name")
    
    private String name;

    @ApiModelProperty(notes = "Testcase Description")
  
    private String description;
    
    @ApiModelProperty(notes = "Testcase expectedResults")
  
    private String expectedResults;
    
    @ApiModelProperty(notes = "Testcase actualResults")
  
    private String actualResults;
  

    @ApiModelProperty(notes = "Testcase priority")
    private Testcase.Status status;

    @ApiModelProperty(notes = "Testcase created by")
    private String createdBy;
    
    
    

}
