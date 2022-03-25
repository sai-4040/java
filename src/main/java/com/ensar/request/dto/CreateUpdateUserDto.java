package com.ensar.request.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.ensar.entity.User;

@ApiModel(value = "CreateUpdateUserDto", description = "Parameters required to create/update user")
@Accessors(chain = true)
@Setter
@Getter
public class CreateUpdateUserDto {

    @ApiModelProperty(notes = "User Email", required = true)
    @NotBlank(message = "User Email is required")
    private String email;

    @ApiModelProperty(notes = "User First Name", required = true)
    @NotBlank(message = "User First Name is required")
    @Size(max = 50)
    private String firstName;

    @ApiModelProperty(notes = "User Last Name", required = true)
    @NotBlank(message = "User Last Name is required")
    @Size(max = 50)
    private String lastName;

    @ApiModelProperty(notes = "User Role")
    private User.Role role;

    @ApiModelProperty(notes = "User Organization")
    private Long organizationId;
}
