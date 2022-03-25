package com.ensar.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ensar.entity.TimesheetTask;



import com.ensar.service.TimesheetTaskService;

import javax.validation.Valid;

import java.util.Map;
import java.util.Optional;

@Api(tags = "TimesheetTask Mgmt")
@RestController
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/timesheettask")
public class TimesheetTaskController {

    private TimesheetTaskService timesheetTaskService;

    @Autowired
    public TimesheetTaskController(TimesheetTaskService timesheetTaskService) {
        this.timesheetTaskService = timesheetTaskService;
    }

        
    @GetMapping
    
    public ResponseEntity<Map<String, Object>> getTimesheetTasks(@RequestParam(required = false, defaultValue = "0") Integer page,
    		@RequestParam(required = false, defaultValue = "10") Integer size) {
    	
        return ResponseEntity.ok(timesheetTaskService.getAllTimesheetTasks(PageRequest.of(page, size)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<TimesheetTask> createOrganization(@Valid @RequestBody TimesheetTask createUpdateOrgDto) {
        return ResponseEntity.ok(timesheetTaskService.createOrUpdateTimesheetTask(Optional.empty(), createUpdateOrgDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<TimesheetTask> updateOrganization(@PathVariable Long id,
                                           @Valid @RequestBody TimesheetTask createUpdateTimesheetTaskDto) {
        return ResponseEntity.ok(timesheetTaskService.createOrUpdateTimesheetTask(Optional.of(id), createUpdateTimesheetTaskDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimesheetTask> getTimesheetTask(@PathVariable Long id) {
        return ResponseEntity.ok(timesheetTaskService.getTimesheetTaskById(id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteTimesheetTask(@PathVariable Long id) {
        return ResponseEntity.ok(timesheetTaskService.deleteTimesheetTaskById(id));
    }

    
}
