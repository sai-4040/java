package com.ensar.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity(name = "releases")
@Data
@EqualsAndHashCode(callSuper = true)
public class Release extends BaseEntity {

    @Column(name = "name")
    private String name;

  

 

}
