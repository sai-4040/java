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
import javax.persistence.Transient;

import com.ensar.entity.User.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name = "testcases")
@Data
@EqualsAndHashCode(callSuper = true)
public class Testcase extends BaseEntity {

    public enum Status  { pass, fail }

    
    @Column(name = "test_id")
    private String testId;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "expected_results")
    private String expectedResults;
    
    @Column(name = "actual_results")
    private String actualResults;
    
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    

    
}
