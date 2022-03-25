package com.ensar.request.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel(value = "CreateUpdateOrgDto", description = "Parameters required to create/update organization")
@Accessors(chain = true)
@Setter
@Getter
public class CreateUpdateOrgDto {

    @ApiModelProperty(notes = "Organization Name", required = true)
    @NotBlank(message = "Organization Name is required")
    @Size(max = 50)
    private String name;

    @ApiModelProperty(notes = "Organization Description", required = true)
    @NotBlank(message = "Organization Description is required")
    @Size(max = 500)
    private String description;

    @ApiModelProperty(notes = "Organization Domain", required = true)
    @NotBlank(message = "Organization Domain is required")
    @Size(max = 50)
    private String domain;

    @ApiModelProperty(notes = "Forecast dashboard ID")
    private Long dashBoardId;

}
