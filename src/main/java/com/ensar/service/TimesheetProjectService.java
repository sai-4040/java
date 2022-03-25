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

import com.ensar.entity.TimesheetProject;
import com.ensar.repository.TimesheetProjectRepository;
import com.ensar.repository.UserRepository;


@Service
@Transactional
public class TimesheetProjectService {


	@Autowired
	private ModelMapper modelMapper;


	@Autowired
	private TimesheetProjectRepository timesheetProjectRepository;

	@Autowired
	private UserRepository userRepository;





    public TimesheetProject getTimesheetProjectById(Long id) {
        Optional<TimesheetProject> timesheetProjectOptional = timesheetProjectRepository.findById(id);

        if (!timesheetProjectOptional.isPresent())
            throw new RuntimeException("timesheetProject Optional with " + id + " not found.");
        TimesheetProject timesheetProject = timesheetProjectOptional.get();

        return timesheetProject;
    }



    public List<TimesheetProject> getAllTimesheetProjects() {

    	List<TimesheetProject> timesheetProjects = timesheetProjectRepository.findAll();


        return timesheetProjects;
    }

    public Map<String, Object> getAllTimesheetProjects(Pageable pageable) {

    	Page<TimesheetProject> timesheetProjects = timesheetProjectRepository.findAll(pageable);


    	Map<String, Object> response = new HashMap<>();

		response.put("timesheetProjects", timesheetProjects.getContent());
		response.put("currentPage", timesheetProjects.getNumber());
		response.put("totalItems", timesheetProjects.getTotalElements());
		response.put("totalPages", (timesheetProjects.getTotalElements() / pageable.getPageSize()+1));

		return response;

    }

    public TimesheetProject createOrUpdateTimesheetProject(Optional<Long> timesheetProjectId, TimesheetProject createUpdateTimesheetProjectDto) {
    	TimesheetProject timesheetProject;
        if (timesheetProjectId.isPresent()) {
        	timesheetProject = timesheetProjectRepository.getById(timesheetProjectId.get());
            if (timesheetProject == null)
                throw new RuntimeException("TimesheetProject with id " + timesheetProjectId.get() + " not found");

            createUpdateTimesheetProjectDto.setId(timesheetProjectId.get());

        }



        return timesheetProjectRepository.save(createUpdateTimesheetProjectDto);


    }


	public Long deleteTimesheetProjectById(Long id) {
		timesheetProjectRepository.deleteById(id);
		return id;
	}




}
