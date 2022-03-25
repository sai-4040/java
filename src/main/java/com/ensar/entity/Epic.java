package com.ensar.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.support.collections.RedisList;

import com.ensar.entity.User.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name = "epics")
@Data
@EqualsAndHashCode(callSuper = true)
public class Epic extends BaseEntity {

	 public enum Priority  { critical, high, medium, low }

	    @Column(name = "name")
	    private String name;

	    @Column(name = "description")
	    private String description;
	    
	    @Column(name = "accptance_criteria")
	    private String accptanceCriteria;
	    
	   
	    
	    @Column(name = "points")
	    private Integer points;
	    
	    @Column(name = "original_estimate")
	    private Integer originalEstimate;
	    
	    
	    @Column(name = "remainng_estimate")
	    private Integer remainngEstimate;
	    
	    @Column(name = "start_date")
	    private Date startDate;
	    
	    @Column(name = "end_date")
	    private Date endDate;
	    
	    @Column(name = "due_date")
	    private Date dueDate;
	    
	    @Column(name = "priority")
	    @Enumerated(EnumType.STRING)
	    private Priority priority;
	   
	    @OneToOne
	    @JoinColumn(name = "user_id", referencedColumnName = "id")
	    private User user;

	   	    
	    @OneToOne
	    @JoinColumn(name = "feature_id", referencedColumnName = "id")
	    private Feature feature;
	    
	    @OneToOne
	    @JoinColumn(name = "release_id", referencedColumnName = "id")
	    private Release release;
	    
	    @OneToOne
	    @JoinColumn(name = "team_id", referencedColumnName = "id")
	    private Team team;
    	  
	   
	    //@OneToMany( mappedBy = "epic", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	   // @JsonManagedReference(value="epic-comments")
	    //private List<Comment> comments;
	

}
