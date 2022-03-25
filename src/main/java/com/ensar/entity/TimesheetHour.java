package com.ensar.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;

import com.ensar.entity.User.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name = "timesheet_hours")
@Data
@EqualsAndHashCode(callSuper = true)
public class TimesheetHour extends BaseEntity {

     
    
	@Column(name = "start_at")
	private Time startAt;

	@Column(name = "end_at")
	private Time endAt;
	    
    //@Column(name = "date")
    //private Date date;
    
    @OneToOne
    @JoinColumn(name = "timesheet_projects_id", referencedColumnName = "id")
    private TimesheetProject timesheetProject;
    
    
    @OneToOne
    @JoinColumn(name = "timesheet_sub_projects_id", referencedColumnName = "id")
    private TimesheetSubProject timesheetSubProject;
    
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
    @Column(name = "comment")
	private String comment;
    
    

    
}
