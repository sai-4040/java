package com.ensar.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ensar.entity.TimesheetProject;



import com.ensar.service.TimesheetProjectService;

import javax.validation.Valid;

import java.util.Map;
import java.util.Optional;

@Api(tags = "TimesheetProject Mgmt")
@RestController
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/timesheetproject")
public class TimesheetProjectController {

    private TimesheetProjectService timesheetProjectService;

    @Autowired
    public TimesheetProjectController(TimesheetProjectService timesheetProjectService) {
        this.timesheetProjectService = timesheetProjectService;
    }

        
    @GetMapping
    public ResponseEntity<Map<String, Object>> getTimesheetProjects(@RequestParam(required = false, defaultValue = "0") Integer page,
    		@RequestParam(required = false, defaultValue = "10") Integer size) {
    	
        return ResponseEntity.ok(timesheetProjectService.getAllTimesheetProjects(PageRequest.of(page, size)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<TimesheetProject> createOrganization(@Valid @RequestBody TimesheetProject createUpdateOrgDto) {
        return ResponseEntity.ok(timesheetProjectService.createOrUpdateTimesheetProject(Optional.empty(), createUpdateOrgDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<TimesheetProject> updateOrganization(@PathVariable Long id,
                                           @Valid @RequestBody TimesheetProject createUpdateTimesheetProjectDto) {
        return ResponseEntity.ok(timesheetProjectService.createOrUpdateTimesheetProject(Optional.of(id), createUpdateTimesheetProjectDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimesheetProject> getTimesheetProject(@PathVariable Long id) {
        return ResponseEntity.ok(timesheetProjectService.getTimesheetProjectById(id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteTimesheetProject(@PathVariable Long id) {
        return ResponseEntity.ok(timesheetProjectService.deleteTimesheetProjectById(id));
    }

    
}
