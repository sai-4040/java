package com.ensar.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ensar.entity.Testcase;

import com.ensar.request.dto.CreateUpdateTestcaseDto;

import com.ensar.service.TestcaseService;

import javax.validation.Valid;

import java.util.Map;
import java.util.Optional;

@Api(tags = "Testcase Mgmt")
@RestController
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/testcase")
public class TestcaseController {

    private TestcaseService testcaseService;

    @Autowired
    public TestcaseController(TestcaseService testcaseService) {
        this.testcaseService = testcaseService;
    }

        
    @GetMapping
    
    public ResponseEntity<Map<String, Object>> getTestcases(@RequestParam(required = false, defaultValue = "0") Integer page,
    		@RequestParam(required = false, defaultValue = "10") Integer size) {
    	
        return ResponseEntity.ok(testcaseService.getAllTestcases(PageRequest.of(page, size)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Testcase> createOrganization(@Valid @RequestBody CreateUpdateTestcaseDto createUpdateOrgDto) {
        return ResponseEntity.ok(testcaseService.createOrUpdateTestcase(Optional.empty(), createUpdateOrgDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Testcase> updateOrganization(@PathVariable Long id,
                                           @Valid @RequestBody CreateUpdateTestcaseDto createUpdateTestcaseDto) {
        return ResponseEntity.ok(testcaseService.createOrUpdateTestcase(Optional.of(id), createUpdateTestcaseDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Testcase> getTestcase(@PathVariable Long id) {
        return ResponseEntity.ok(testcaseService.getTestcaseById(id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteTestcase(@PathVariable Long id) {
        return ResponseEntity.ok(testcaseService.deleteTestcaseById(id));
    }

    
}
