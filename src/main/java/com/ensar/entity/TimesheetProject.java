package com.ensar.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.ensar.entity.User.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name = "timesheet_projects")
@Data
@EqualsAndHashCode(callSuper = true)
public class TimesheetProject extends BaseEntity {

  
    @Column(name = "name")
    private String name;
    
        
    
    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;
    
    

    
}
