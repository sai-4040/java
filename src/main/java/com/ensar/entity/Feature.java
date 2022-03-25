package com.ensar.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity(name = "features")
@Data
@EqualsAndHashCode(callSuper = true)
public class Feature extends BaseEntity {

    @Column(name = "name")
    private String name;

  

 

}
