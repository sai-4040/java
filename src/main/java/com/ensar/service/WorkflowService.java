package com.ensar.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service

public class WorkflowService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	 	@Autowired
	    private KafkaTemplate<String, String> kafkaTemplate;

	    public void sendMessage(String order) {
	    	logger.info("Order processed to dispatch:"+ order);
	        this.kafkaTemplate.send("orders", order);
	    }
	    
	    @KafkaListener(topics = "orders", groupId = "inventory")
	    public void consume(String order) throws IOException {
	    	logger.info("Order received to process:"+ order);
	       
	        
	    }

}
