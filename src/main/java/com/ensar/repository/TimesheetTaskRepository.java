package com.ensar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensar.entity.Comment;
import com.ensar.entity.Epic;
import com.ensar.entity.Feature;
import com.ensar.entity.ForecastDashBoard;
import com.ensar.entity.Organization;
import com.ensar.entity.Project;
import com.ensar.entity.Release;
import com.ensar.entity.Testcase;
import com.ensar.entity.Ticket;
import com.ensar.entity.TimesheetProject;
import com.ensar.entity.TimesheetSubProject;
import com.ensar.entity.TimesheetTask;

import java.util.List;

@Repository
public interface TimesheetTaskRepository extends JpaRepository<TimesheetTask, Long> {
	
	

    
}
