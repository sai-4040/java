package com.ensar.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ensar.entity.mongo.MongoUser;
import com.ensar.repository.mongo.MongoUserRepository;

import io.swagger.annotations.Api;

@Api(tags = "Mongo Mgmt")
@RestController
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/mongo")
public class MongoController {

	
	 @Autowired
	 public MongoUserRepository mongoUserRepository;

	 @GetMapping
	    public ResponseEntity<List<MongoUser>> getList() {

		 List<MongoUser> list = mongoUserRepository.findAll();


	        return ResponseEntity.ok(list);
	    }

	 @PostMapping
	 @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
	 public ResponseEntity<MongoUser> createUser(@Valid @RequestBody MongoUser user) {


		 return ResponseEntity.ok(mongoUserRepository.save(user));
	    }

}
