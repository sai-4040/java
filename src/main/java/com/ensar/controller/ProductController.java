package com.ensar.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ensar.entity.solr.Product;
import com.ensar.repository.solr.ProductRepository;
import com.ensar.request.dto.CreateUpdateOrgDto;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Api(tags = "Product Solr Mgmt")
@RestController
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/product")
public class ProductController {

	@Autowired
	ProductRepository productRepository;

    

    @GetMapping("/")
    public ResponseEntity<Iterable<Product>> getProducts() {
    	
    	Iterable<Product> resp = productRepository.findAll();
    	
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
    	 
    	return ResponseEntity.ok(productRepository.save(product));
    }

   
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<String> deleteEpic(@PathVariable String id) {
    	
    	productRepository.deleteById(id);
    	return ResponseEntity.ok(id);
    	
    	
    }
    
}
