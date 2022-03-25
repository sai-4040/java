package com.ensar.request.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel(value = "CreateUpdateProjectDto", description = "Parameters required to create/update project")
@Accessors(chain = true)
@Setter
@Getter
public class CreateUpdateProjectDto {

    @ApiModelProperty(notes = "Project Name", required = true)
    @NotBlank(message = "Project Name is required")
    @Size(max = 50)
    private String name;

    @ApiModelProperty(notes = "Project Description", required = true)
    @NotBlank(message = "Project Description is required")
    @Size(max = 500)
    private String description;

    

}
