package com.ensar.controller;

import io.swagger.annotations.Api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Api(tags = "RabbitMQ Mgmt")
@RestController
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/rabbitmq")
public class RabbitMQController {
	 
	 private final Logger logger = LoggerFactory.getLogger(this.getClass());
	 
	 @Autowired
	 private RabbitTemplate rabbitTemplate;
	
	 @GetMapping("/")
	 @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
	 public ResponseEntity<String> SendMessage(String message) {
			 
		 rabbitTemplate.convertAndSend("user.exchange","user.routingkey", message);
		 
	        return ResponseEntity.ok("Success");
	 }
	 
	 @RabbitListener(queues = "user.queue")
	    public void receivedMessage(String user) {

		 logger.info("User Details Received is.. " + user);
	    }

}
