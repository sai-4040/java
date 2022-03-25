package com.ensar.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ensar.entity.TimesheetHour;
import com.ensar.request.dto.CreateUpdateTimesheetHourDto;
import com.ensar.service.TimesheetHourService;

import javax.validation.Valid;

import java.util.Map;
import java.util.Optional;

@Api(tags = "TimesheetHour Mgmt")
@RestController
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/timesheethour")
public class TimesheetHourController {

    private TimesheetHourService timesheetHourService;

    @Autowired
    public TimesheetHourController(TimesheetHourService timesheetHourService) {
        this.timesheetHourService = timesheetHourService;
    }

        
    @GetMapping
    
    public ResponseEntity<Map<String, Object>> getTimesheetHours(@RequestParam(required = false, defaultValue = "0") Integer page,
    		@RequestParam(required = false, defaultValue = "10") Integer size) {
    	
        return ResponseEntity.ok(timesheetHourService.getAllTimesheetHours(PageRequest.of(page, size)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<TimesheetHour> createOrganization(@Valid @RequestBody CreateUpdateTimesheetHourDto createUpdateOrgDto) {
        return ResponseEntity.ok(timesheetHourService.createOrUpdateTimesheetHour(Optional.empty(), createUpdateOrgDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<TimesheetHour> updateOrganization(@PathVariable Long id,
                                           @Valid @RequestBody CreateUpdateTimesheetHourDto createUpdateTimesheetHourDto) {
        return ResponseEntity.ok(timesheetHourService.createOrUpdateTimesheetHour(Optional.of(id), createUpdateTimesheetHourDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimesheetHour> getTimesheetHour(@PathVariable Long id) {
        return ResponseEntity.ok(timesheetHourService.getTimesheetHourById(id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteTimesheetHour(@PathVariable Long id) {
        return ResponseEntity.ok(timesheetHourService.deleteTimesheetHourById(id));
    }

    
}
