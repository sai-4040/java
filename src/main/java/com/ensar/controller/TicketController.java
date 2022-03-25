package com.ensar.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ensar.entity.Ticket;

import com.ensar.request.dto.CreateUpdateTicketDto;

import com.ensar.service.TicketService;

import javax.validation.Valid;

import java.util.Map;
import java.util.Optional;

@Api(tags = "Ticket Mgmt")
@RestController
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/ticket")
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

        
    @GetMapping
    
    public ResponseEntity<Map<String, Object>> getTickets(@RequestParam(required = false, defaultValue = "0") Integer page,
    		@RequestParam(required = false, defaultValue = "10") Integer size) {
    	
        return ResponseEntity.ok(ticketService.getAllTickets(PageRequest.of(page, size)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Ticket> createOrganization(@Valid @RequestBody CreateUpdateTicketDto createUpdateOrgDto) {
        return ResponseEntity.ok(ticketService.createOrUpdateTicket(Optional.empty(), createUpdateOrgDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Ticket> updateOrganization(@PathVariable Long id,
                                           @Valid @RequestBody CreateUpdateTicketDto createUpdateTicketDto) {
        return ResponseEntity.ok(ticketService.createOrUpdateTicket(Optional.of(id), createUpdateTicketDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicket(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteTicket(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.deleteTicketById(id));
    }

    
}
