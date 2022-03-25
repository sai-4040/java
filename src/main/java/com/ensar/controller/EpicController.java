package com.ensar.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ensar.entity.Epic;

import com.ensar.request.dto.CreateUpdateEpicDto;
import com.ensar.service.EpicService;


import javax.validation.Valid;

import java.util.Map;
import java.util.Optional;

@Api(tags = "Epic Mgmt")
@RestController
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/epic")
public class EpicController {

    private EpicService epicService;

    @Autowired
    public EpicController(EpicService epicService) {
        this.epicService = epicService;
    }

        
    @GetMapping
    
    public ResponseEntity<Map<String, Object>> getEpics(@RequestParam(required = false, defaultValue = "0") Integer page,
    		@RequestParam(required = false, defaultValue = "10") Integer size) {
    	
        return ResponseEntity.ok(epicService.getAllEpics(PageRequest.of(page, size)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Epic> createEpic(@Valid @RequestBody CreateUpdateEpicDto createUpdateEpicDto) {
        return ResponseEntity.ok(epicService.createOrUpdateEpic(Optional.empty(), createUpdateEpicDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Epic> updateEpic(@PathVariable Long id,
                                           @Valid @RequestBody CreateUpdateEpicDto createUpdateEpicDto) {
        return ResponseEntity.ok(epicService.createOrUpdateEpic(Optional.of(id), createUpdateEpicDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Epic> getEpic(@PathVariable Long id) {
        return ResponseEntity.ok(epicService.getEpicById(id));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Long> deleteEpic(@PathVariable Long id) {
        return ResponseEntity.ok(epicService.deleteEpicById(id));
    }

    
}
