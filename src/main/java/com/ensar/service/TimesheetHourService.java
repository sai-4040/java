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
import org.springframework.util.StringUtils;

import com.ensar.entity.Epic;
import com.ensar.entity.Feature;
import com.ensar.entity.Release;
import com.ensar.entity.TimesheetHour;
import com.ensar.entity.TimesheetProject;
import com.ensar.entity.TimesheetSubProject;
import com.ensar.entity.User;
import com.ensar.repository.TimesheetHourRepository;
import com.ensar.repository.TimesheetProjectRepository;
import com.ensar.repository.TimesheetSubProjectRepository;
import com.ensar.repository.UserRepository;
import com.ensar.request.dto.CreateUpdateTimesheetHourDto;

@Service
@Transactional
public class TimesheetHourService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private TimesheetHourRepository timesheetHourRepository;

	@Autowired
	private TimesheetProjectRepository timesheetProjectRepository;

	@Autowired
	private TimesheetSubProjectRepository timesheetSubProjectRepository;

	@Autowired
	private UserRepository userRepository;

	public TimesheetHour getTimesheetHourById(Long id) {
		Optional<TimesheetHour> timesheetHourOptional = timesheetHourRepository.findById(id);

		if (!timesheetHourOptional.isPresent())
			throw new RuntimeException("timesheetHour Optional with " + id + " not found.");
		TimesheetHour timesheetHour = timesheetHourOptional.get();

		return timesheetHour;
	}

	public List<TimesheetHour> getAllTimesheetHours() {

		List<TimesheetHour> timesheetHours = timesheetHourRepository.findAll();

		return timesheetHours;
	}

	public Map<String, Object> getAllTimesheetHours(Pageable pageable) {

		Page<TimesheetHour> timesheetHours = timesheetHourRepository.findAll(pageable);

		Map<String, Object> response = new HashMap<>();

		response.put("timesheetHours", timesheetHours.getContent());
		response.put("currentPage", timesheetHours.getNumber());
		response.put("totalItems", timesheetHours.getTotalElements());
		response.put("totalPages", (timesheetHours.getTotalElements() / pageable.getPageSize() + 1));

		return response;

	}

	public TimesheetHour createOrUpdateTimesheetHour(Optional<Long> timesheetHourId,
			CreateUpdateTimesheetHourDto createUpdateTimesheetHourDto) {
		TimesheetHour timesheetHour;
		if (timesheetHourId.isPresent()) {
			timesheetHour = timesheetHourRepository.findById(timesheetHourId.get()).get();
			if (timesheetHour == null)
				throw new RuntimeException("TimesheetHour with id " + timesheetHourId.get() + " not found");

			timesheetHour = modelMapper.map(createUpdateTimesheetHourDto, TimesheetHour.class);
			timesheetHour.setId(timesheetHourId.get());

		} else {
			timesheetHour = modelMapper.map(createUpdateTimesheetHourDto, TimesheetHour.class);
		}

		if (createUpdateTimesheetHourDto.getUser() != null) {
			User user = userRepository.findById(createUpdateTimesheetHourDto.getUser()).get();
			if (user == null)
				throw new RuntimeException("User with id " + createUpdateTimesheetHourDto.getUser() + " not found");

			timesheetHour.setUser(user);
		}
		if (createUpdateTimesheetHourDto.getTimesheetProject() != null) {
			TimesheetProject timesheetProject = timesheetProjectRepository
					.findById(createUpdateTimesheetHourDto.getTimesheetProject()).get();
			if (timesheetProject == null)
				throw new RuntimeException(
						"User with id " + createUpdateTimesheetHourDto.getTimesheetProject() + " not found");

			timesheetHour.setTimesheetProject(timesheetProject);
		}
		if (createUpdateTimesheetHourDto.getTimesheetSubProject() != null) {
			TimesheetSubProject timesheetSubProject = timesheetSubProjectRepository
					.findById(createUpdateTimesheetHourDto.getTimesheetSubProject()).get();
			if (timesheetSubProject == null)
				throw new RuntimeException(
						"User with id " + createUpdateTimesheetHourDto.getTimesheetSubProject() + " not found");

			timesheetHour.setTimesheetSubProject(timesheetSubProject);
		}
		timesheetHour = timesheetHourRepository.save(timesheetHour);

		return timesheetHour;

	}

	public Long deleteTimesheetHourById(Long id) {
		timesheetHourRepository.deleteById(id);
		return id;
	}

}
