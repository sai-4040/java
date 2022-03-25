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
import com.ensar.entity.Ticket;
import com.ensar.entity.Testcase;


@ApiModel(value = "CreateUpdateTicketDto", description = "Parameters required to create/update testcase")
@Accessors(chain = true)
@Setter
@Getter
public class CreateUpdateTicketDto {

    @ApiModelProperty(notes = "Testcase testId", required = true)
    @Size(max = 500)
    private String name;

    @ApiModelProperty(notes = "Testcase Description", required = true)
     @Size(max = 50)
    private String type;
    
    @ApiModelProperty(notes = "Testcase expectedResults", required = true)
    @Size(max = 200)
    private Ticket.Priority priority;
    
   
    @ApiModelProperty(notes = "Testcase priority", required = true)
    private Ticket.Status status;

    @ApiModelProperty(notes = "Testcase created by", required = true)
    private String createdBy;
    
    
    

}
