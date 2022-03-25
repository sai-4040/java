package com.ensar.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ensar.entity.TimesheetSubProject;
import com.ensar.repository.TimesheetSubProjectRepository;
import com.ensar.repository.UserRepository;


@Service
@Transactional
public class TimesheetSubProjectService {


	@Autowired
	private ModelMapper modelMapper;


	@Autowired
	private TimesheetSubProjectRepository timesheetSubProjectRepository;

	@Autowired
	private UserRepository userRepository;



    public TimesheetSubProject getTimesheetSubProjectById(Long id) {
        Optional<TimesheetSubProject> timesheetSubProjectOptional = timesheetSubProjectRepository.findById(id);

        if (!timesheetSubProjectOptional.isPresent())
            throw new RuntimeException("timesheetSubProject Optional with " + id + " not found.");
        TimesheetSubProject timesheetSubProject = timesheetSubProjectOptional.get();

        return timesheetSubProject;
    }



    public List<TimesheetSubProject> getAllTimesheetSubProjects() {

    	List<TimesheetSubProject> timesheetSubProjects = timesheetSubProjectRepository.findAll();


        return timesheetSubProjects;
    }

    public Map<String, Object> getAllTimesheetSubProjects(Pageable pageable) {

    	Page<TimesheetSubProject> timesheetSubProjects = timesheetSubProjectRepository.findAll(pageable);


    	Map<String, Object> response = new HashMap<>();

		response.put("timesheetSubProjects", timesheetSubProjects.getContent());
		response.put("currentPage", timesheetSubProjects.getNumber());
		response.put("totalItems", timesheetSubProjects.getTotalElements());
		response.put("totalPages", (timesheetSubProjects.getTotalElements() / pageable.getPageSize()+1));

		return response;

    }

    public TimesheetSubProject createOrUpdateTimesheetSubProject(Optional<Long> timesheetSubProjectId, TimesheetSubProject createUpdateTimesheetSubProjectDto) {
    	TimesheetSubProject timesheetSubProject;
        if (timesheetSubProjectId.isPresent()) {
        	timesheetSubProject = timesheetSubProjectRepository.getById(timesheetSubProjectId.get());
            if (timesheetSubProject == null)
                throw new RuntimeException("TimesheetSubProject with id " + timesheetSubProjectId.get() + " not found");

            createUpdateTimesheetSubProjectDto.setId(timesheetSubProjectId.get());

        }



        return timesheetSubProjectRepository.save(createUpdateTimesheetSubProjectDto);


    }


	public Long deleteTimesheetSubProjectById(Long id) {
		timesheetSubProjectRepository.deleteById(id);
		return id;
	}




}
