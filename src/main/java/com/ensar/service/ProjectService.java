package com.ensar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ensar.entity.ForecastDashBoard;
import com.ensar.entity.Organization;
import com.ensar.entity.Project;
import com.ensar.repository.ForecastUrlRepository;
import com.ensar.repository.OrganizationRepository;
import com.ensar.repository.ProjectRepository;
import com.ensar.request.dto.CreateUpdateOrgDto;
import com.ensar.request.dto.CreateUpdateProjectDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjectService {

    private ProjectRepository projectRepository;

  

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {

        this.projectRepository = projectRepository;
       
    }


    public Project getProjectById(String id) {
        Optional<Project> projectOptional = projectRepository.findById(id);

        if (!projectOptional.isPresent())
            throw new RuntimeException("projectOptional with " + id + " not found.");
        Project project = projectOptional.get();
        
        return project;
    }

    

    public List<Project> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
       
        return projects;
    }

    public Project createOrUpdateProject(Optional<String> projectId, CreateUpdateProjectDto createUpdateProjectDto) {
    	Project project;
        if (projectId.isPresent()) {
        	project = projectRepository.getById(projectId.get());
            if (project == null)
                throw new RuntimeException("Project with id " + projectId.get() + " not found");
        } else {
        	project = new Project();
            if (projectRepository.existsByName(createUpdateProjectDto.getName()))
                throw new RuntimeException("Organization with name " + createUpdateProjectDto.getName() + " already exists.");
        }

        project.setName(createUpdateProjectDto.getName());
        project.setDescription(createUpdateProjectDto.getDescription());
       

        project = projectRepository.save(project);

        return project;
    }

   


}
