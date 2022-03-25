package com.ensar.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ensar.entity.Organization;
import com.ensar.request.dto.CreateUpdateOrgDto;
import com.ensar.service.OrganizationService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Api(tags = "Organization Mgmt")
@RestController
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/organization")
public class OrganizationController {

    private OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<List<Organization>> getOrganizations() {
        return ResponseEntity.ok(organizationService.getAllOrganizations());
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Organization> createOrganization(@Valid @RequestBody CreateUpdateOrgDto createUpdateOrgDto) {
        return ResponseEntity.ok(organizationService.createOrUpdateOrganization(Optional.empty(), createUpdateOrgDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Organization> updateOrganization(@PathVariable Long id,
                                           @Valid @RequestBody CreateUpdateOrgDto createUpdateOrgDto) {
        return ResponseEntity.ok(organizationService.createOrUpdateOrganization(Optional.of(id), createUpdateOrgDto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Organization> getOrganization(@PathVariable Long id) {
        return ResponseEntity.ok(organizationService.getOrganizationById(id));
    }

    @PutMapping("/enable")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Void> enableUsers(@RequestBody List<Long> idList) {
        organizationService.enableOrDisableOrgs(idList, false);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/disable")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Void> disableUsers(@RequestBody List<Long> idList) {
        organizationService.enableOrDisableOrgs(idList, true);
        return ResponseEntity.ok().build();
    }
}
