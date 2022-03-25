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

import com.ensar.entity.TimesheetTask;
import com.ensar.repository.TimesheetTaskRepository;
import com.ensar.repository.UserRepository;


@Service
@Transactional
public class TimesheetTaskService {


	@Autowired
	private ModelMapper modelMapper;


	@Autowired
	private TimesheetTaskRepository timesheetTaskRepository;





    public TimesheetTask getTimesheetTaskById(Long id) {
        Optional<TimesheetTask> timesheetTaskOptional = timesheetTaskRepository.findById(id);

        if (!timesheetTaskOptional.isPresent())
            throw new RuntimeException("timesheetTask Optional with " + id + " not found.");
        TimesheetTask timesheetTask = timesheetTaskOptional.get();

        return timesheetTask;
    }



    public List<TimesheetTask> getAllTimesheetTasks() {

    	List<TimesheetTask> timesheetTasks = timesheetTaskRepository.findAll();


        return timesheetTasks;
    }

    public Map<String, Object> getAllTimesheetTasks(Pageable pageable) {

    	Page<TimesheetTask> timesheetTasks = timesheetTaskRepository.findAll(pageable);


    	Map<String, Object> response = new HashMap<>();

		response.put("timesheetTasks", timesheetTasks.getContent());
		response.put("currentPage", timesheetTasks.getNumber());
		response.put("totalItems", timesheetTasks.getTotalElements());
		response.put("totalPages", (timesheetTasks.getTotalElements() / pageable.getPageSize()+1));

		return response;

    }

    public TimesheetTask createOrUpdateTimesheetTask(Optional<Long> timesheetTaskId, TimesheetTask createUpdateTimesheetTaskDto) {
    	TimesheetTask timesheetTask;
        if (timesheetTaskId.isPresent()) {
        	timesheetTask = timesheetTaskRepository.getById(timesheetTaskId.get());
            if (timesheetTask == null)
                throw new RuntimeException("TimesheetTask with id " + timesheetTaskId.get() + " not found");

            createUpdateTimesheetTaskDto.setId(timesheetTaskId.get());

        }



        return timesheetTaskRepository.save(createUpdateTimesheetTaskDto);


    }


	public Long deleteTimesheetTaskById(Long id) {
		timesheetTaskRepository.deleteById(id);
		return id;
	}




}
