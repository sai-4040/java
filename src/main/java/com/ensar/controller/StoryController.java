package com.ensar.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ensar.entity.Organization;
import com.ensar.entity.Story;
import com.ensar.request.dto.CreateUpdateOrgDto;
import com.ensar.request.dto.CreateUpdateStoryDto;
import com.ensar.service.OrganizationService;
import com.ensar.service.StoryService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Api(tags = "Story Mgmt")
@RestController
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/story")
public class StoryController {

    private StoryService storyService;

    @Autowired
    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

        
    @GetMapping
    
    public ResponseEntity<Map<String, Object>> getStorys(@RequestParam(required = false, defaultValue = "0") Integer page,
    		@RequestParam(required = false, defaultValue = "10") Integer size) {
    	
        return ResponseEntity.ok(storyService.getAllStorys(PageRequest.of(page, size)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Story> createOrganization(@Valid @RequestBody CreateUpdateStoryDto createUpdateOrgDto) {
        return ResponseEntity.ok(storyService.createOrUpdateStory(Optional.empty(), createUpdateOrgDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Story> updateOrganization(@PathVariable Long id,
                                           @Valid @RequestBody CreateUpdateStoryDto createUpdateStoryDto) {
        return ResponseEntity.ok(storyService.createOrUpdateStory(Optional.of(id), createUpdateStoryDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Story> getStory(@PathVariable Long id) {
        return ResponseEntity.ok(storyService.getStoryById(id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteStory(@PathVariable Long id) {
        return ResponseEntity.ok(storyService.deleteStoryById(id));
    }

    
}
