package com.ensar.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ensar.entity.TimesheetSubProject;



import com.ensar.service.TimesheetSubProjectService;

import javax.validation.Valid;

import java.util.Map;
import java.util.Optional;

@Api(tags = "TimesheetSubProject Mgmt")
@RestController
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/timesheetsubproject")
public class TimesheetSubProjectController {

    private TimesheetSubProjectService timesheetSubProjectService;

    @Autowired
    public TimesheetSubProjectController(TimesheetSubProjectService timesheetSubProjectService) {
        this.timesheetSubProjectService = timesheetSubProjectService;
    }

        
    @GetMapping
    
    public ResponseEntity<Map<String, Object>> getTimesheetSubProjects(@RequestParam(required = false, defaultValue = "0") Integer page,
    		@RequestParam(required = false, defaultValue = "10") Integer size) {
    	
        return ResponseEntity.ok(timesheetSubProjectService.getAllTimesheetSubProjects(PageRequest.of(page, size)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<TimesheetSubProject> createOrganization(@Valid @RequestBody TimesheetSubProject createUpdateOrgDto) {
        return ResponseEntity.ok(timesheetSubProjectService.createOrUpdateTimesheetSubProject(Optional.empty(), createUpdateOrgDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<TimesheetSubProject> updateOrganization(@PathVariable Long id,
                                           @Valid @RequestBody TimesheetSubProject createUpdateTimesheetSubProjectDto) {
        return ResponseEntity.ok(timesheetSubProjectService.createOrUpdateTimesheetSubProject(Optional.of(id), createUpdateTimesheetSubProjectDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimesheetSubProject> getTimesheetSubProject(@PathVariable Long id) {
        return ResponseEntity.ok(timesheetSubProjectService.getTimesheetSubProjectById(id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteTimesheetSubProject(@PathVariable Long id) {
        return ResponseEntity.ok(timesheetSubProjectService.deleteTimesheetSubProjectById(id));
    }

    
}
