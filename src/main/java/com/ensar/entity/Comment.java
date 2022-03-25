package com.ensar.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name = "comments")
@Data
@EqualsAndHashCode(callSuper = true)
public class Comment extends BaseEntity {

    @Column(name = "name")
    private String name;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "epic_id")
    //@JsonManagedReference(value="epic-comments")
    private Epic epic;
    
    @JsonIgnore
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id")
    private Story story;
    
    

    
}
