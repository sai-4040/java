package com.ensar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ensar.entity.ForecastDashBoard;
import com.ensar.entity.Organization;
import com.ensar.entity.Project;
import com.ensar.entity.Release;
import com.ensar.repository.ForecastUrlRepository;
import com.ensar.repository.OrganizationRepository;
import com.ensar.repository.ProjectRepository;
import com.ensar.repository.ReleaseRepository;
import com.ensar.request.dto.CreateUpdateOrgDto;
import com.ensar.request.dto.CreateUpdateProjectDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
////@CacheConfig(cacheNames = "releasesCache")
public class ReleaseService {

	@Autowired
    private ReleaseRepository releaseRepository;

  
	////@Cacheable(cacheNames = "releases")
    public List<Release> getAllReleases() {
        List<Release> projects = releaseRepository.findAll();
       
        return projects;
    }

    
   
}
