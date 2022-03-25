package com.ensar.controller;

import io.swagger.annotations.Api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ensar.entity.User;
import com.ensar.request.dto.CreateUpdateUserDto;
import com.ensar.security.CurrentUser;
import com.ensar.security.EnsarUserDetails;
import com.ensar.service.UserService;
import com.ensar.service.WorkflowService;

import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@Api(tags = "Workflow Kafka Mgmt")
@RestController
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/workflow")
public class WorkflowController {
	 
	 @Autowired
	 public WorkflowService workflowService;
	
	 @GetMapping("/")
	    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
	    public ResponseEntity<String> SendMessage(String message) {
		 	
		 	workflowService.sendMessage("orders");
		 
	        return ResponseEntity.ok("Success");
	    }

}
