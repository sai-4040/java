package com.ensar.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ensar.entity.Organization;
import com.ensar.entity.Release;
import com.ensar.request.dto.CreateUpdateOrgDto;
import com.ensar.service.OrganizationService;
import com.ensar.service.ReleaseService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Api(tags = "Release Mgmt")
@RestController
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/release")
public class ReleaseController {

    private ReleaseService releaseService;

    @Autowired
    public ReleaseController(ReleaseService releaseService) {
        this.releaseService = releaseService;
    }

    @GetMapping

    public ResponseEntity<List<Release>> getReleases() {
        return ResponseEntity.ok(releaseService.getAllReleases());
    }

   
    
}
