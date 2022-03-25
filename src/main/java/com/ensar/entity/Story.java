package com.ensar.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

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

import com.ensar.entity.Epic.Priority;
import com.ensar.entity.User.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name = "stories")
@Data
@EqualsAndHashCode(callSuper = true)
public class Story extends BaseEntity {

	 public enum Priority  { critical, high, medium, low }
	 
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
    
    @Column(name = "accptance_criteria")
    private String accptanceCriteria;
    
    @Column(name = "original_estimate")
    private Integer originalEstimate;
    
    @Column(name = "points")
    private Integer points;
    
    @Column(name = "remainng_estimate")
    private Integer remainngEstimate;
    
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
	@JoinColumn(name = "epic_id", referencedColumnName = "id")
	private Epic epic;

	
	 /*@OneToMany(mappedBy = "story", cascade =
	 CascadeType.ALL)
	 private List<Comment> comments;// = new HashSet<Comment>();
*/
}
